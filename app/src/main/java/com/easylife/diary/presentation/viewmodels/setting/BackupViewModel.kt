package com.easylife.diary.presentation.ui.setting

import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.data.repository.EntryRepository
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.model.DiaryNote
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import com.easylife.diary.domain.backup.BackupCrypto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets
import javax.inject.Inject

data class BackupUiState(
    val encryptionEnabled: Boolean = false,
    val password: String = "",
    val status: String = "Ready",
    val lastPayload: ByteArray? = null,
    val selectedFormat: String = "json"
)

@HiltViewModel
class BackupViewModel @Inject constructor(
    private val entryRepository: EntryRepository,
    private val preferencesManager: PreferencesManager,
    private val gson: Gson
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(BackupUiState())
    val uiState: StateFlow<BackupUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                encryptionEnabled = preferencesManager.getBoolean(PreferenceKeys.BACKUP_ENCRYPTION_ENABLED, false)
            )
        }
    }

    fun onFormatChanged(format: String) {
        _uiState.value = _uiState.value.copy(selectedFormat = format)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onEncryptionChanged(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.BACKUP_ENCRYPTION_ENABLED, enabled)
            _uiState.value = _uiState.value.copy(encryptionEnabled = enabled)
        }
    }

    fun prepareExport() {
        viewModelScope.launch {
            runCatching {
                val state = _uiState.value
                val bytes = withContext(Dispatchers.IO) {
                    val entries = entryRepository.getAllEntries()
                    val text = when (state.selectedFormat) {
                        "csv" -> toCsv(entries)
                        "md" -> toMarkdown(entries)
                        else -> gson.toJson(entries)
                    }
                    var raw = text.toByteArray(StandardCharsets.UTF_8)
                    if (state.encryptionEnabled && state.password.isNotBlank()) {
                        raw = BackupCrypto.encrypt(raw, state.password)
                    }
                    _uiState.value = _uiState.value.copy(status = "Export prepared (${entries.size} entries)")
                    raw
                }
                _uiState.value = _uiState.value.copy(
                    lastPayload = bytes
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(status = "Export failed: ${it.message}")
            }
        }
    }

    fun consumePreparedPayload(): ByteArray? {
        val payload = _uiState.value.lastPayload
        _uiState.value = _uiState.value.copy(lastPayload = null)
        return payload
    }

    fun importFromRaw(raw: ByteArray) {
        viewModelScope.launch {
            runCatching {
                val importedCount = withContext(Dispatchers.IO) {
                    val state = _uiState.value
                    val input = if (state.encryptionEnabled && state.password.isNotBlank()) {
                        BackupCrypto.decrypt(raw, state.password)
                    } else {
                        raw
                    }
                    val text = input.toString(StandardCharsets.UTF_8)
                    val type = object : TypeToken<List<DiaryNote>>() {}.type
                    val imported: List<DiaryNote> = gson.fromJson(text, type) ?: emptyList()
                    entryRepository.deleteAllEntries()
                    entryRepository.addEntries(imported.map { it.copy(id = 0) })
                    imported.size
                }
                _uiState.value = _uiState.value.copy(status = "Import successful ($importedCount entries)")
            }.onFailure {
                _uiState.value = _uiState.value.copy(status = "Import failed: ${it.message}")
            }
        }
    }

    private fun toCsv(entries: List<DiaryNote>): String {
        return buildString {
            appendLine("id,title,description,moodId,timestamp,tags")
            entries.forEach { note ->
                appendLine(
                    listOf(
                        note.id.toString(),
                        quoteCsv(note.title.orEmpty()),
                        quoteCsv(note.description.orEmpty()),
                        note.moodId?.toString().orEmpty(),
                        note.date?.timestamp?.toString().orEmpty(),
                        quoteCsv(note.tags.orEmpty())
                    ).joinToString(",")
                )
            }
        }
    }

    private fun toMarkdown(entries: List<DiaryNote>): String {
        return buildString {
            appendLine("# DexDiary Backup")
            appendLine()
            entries.forEach { note ->
                appendLine("## ${note.title ?: "Untitled"}")
                appendLine("- Mood: ${note.moodId ?: "N/A"}")
                appendLine("- Date: ${note.date?.shortMonth ?: ""} ${note.date?.dayOfMonth ?: ""} ${note.date?.year ?: ""}")
                appendLine()
                appendLine(note.description.orEmpty())
                appendLine()
            }
        }
    }

    private fun quoteCsv(value: String): String = "\"${value.replace("\"", "\"\"")}\""
}

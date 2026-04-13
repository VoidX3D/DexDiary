package com.easylife.diary.feature.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.common.util.DateUtil
import com.easylife.diary.core.common.util.DateUtil.formattedDate
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.domain.usecases.AddEntryUseCase
import com.easylife.diary.core.domain.usecases.DeleteEntryUseCase
import com.easylife.diary.core.domain.usecases.EditEntryUseCase
import com.easylife.diary.core.model.DiaryNote
import com.easylife.diary.core.designsystem.enums.MoodTypes
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by erenalpaslan on 2.01.2023
 */
@HiltViewModel
class NoteViewModel @Inject constructor(
    private val navigator: DiaryNavigator,
    private val addEntryUseCase: AddEntryUseCase,
    private val editEntryUseCase: EditEntryUseCase,
    private val deleteEntryUseCase: DeleteEntryUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<NoteUiState> = MutableStateFlow(NoteUiState())
    val uiState = _uiState.asStateFlow().onEach {
        it.doneVisible = it.isChanged()
    }

    private var diaryNote: DiaryNote? = null

    init {
        viewModelScope.launch {
            diaryNote = savedStateHandle[DiaryArgs.NOTE_KEY]
            val isEditing = diaryNote != null
            val noteDate = if (isEditing) {
                Instant.ofEpochMilli(diaryNote?.date?.timestamp ?: 0L)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            } else {
                LocalDate.now()
            }
            val isEditable = noteDate.isEqual(LocalDate.now())

            _uiState.update {
                it.copy(
                    title = diaryNote?.title ?: "",
                    description = diaryNote?.description ?: "",
                    date = diaryNote?.date?.formattedDate() ?: DateUtil.getCurrentDate(),
                    mood = MoodTypes.from(diaryNote?.moodId ?: -1),
                    isEditing = isEditing,
                    tags = diaryNote?.tags ?: "",
                    mediaPaths = diaryNote?.mediaPaths ?: emptyList(),
                    isEditable = isEditable
                )
            }
        }
    }

    fun onDeleteClicked() {
        diaryNote?.let {
            viewModelScope.launch {
                showProgress()
                deleteEntryUseCase.execute(DeleteEntryUseCase.Params(
                    entry = it
                )).collect()
                navigator.navigateBackWithResult(
                    key = DiaryArgs.ENTRY_AFFECTED,
                    result = true
                )
                hideProgress()
            }
        }
    }

    fun onDoneClicked() {
        if (!_uiState.value.isEditable) return
        
        viewModelScope.launch {
            showProgress()
            addEntryUseCase.execute(AddEntryUseCase.Params(
                title = _uiState.value.title,
                description = _uiState.value.description,
                moodId = _uiState.value.mood?.id,
                tags = _uiState.value.tags,
                mediaPaths = _uiState.value.mediaPaths
            )).collect { result ->
                // TODO: Show PTS awarded animation/toast
            }
            navigator.navigateBackWithResult(
                key = DiaryArgs.ENTRY_AFFECTED,
                result = true
            )
            hideProgress()
        }
    }

    fun onEditClicked() {
        if (!_uiState.value.isEditable) return
        
        diaryNote?.let {entry ->
            viewModelScope.launch {
                showProgress()
                // Update diaryNote fields before editing
                entry.title = _uiState.value.title
                entry.description = _uiState.value.description
                entry.moodId = _uiState.value.mood?.id
                entry.tags = _uiState.value.tags
                entry.mediaPaths = _uiState.value.mediaPaths
                // wordCount and complexity should ideally be updated here too
                entry.wordCount = _uiState.value.description.split("\\s+".toRegex()).filter { it.isNotEmpty() }.size

                editEntryUseCase.execute(EditEntryUseCase.Params(
                    entry = entry
                )).collect()
                navigator.navigateBackWithResult(
                    key = DiaryArgs.ENTRY_AFFECTED,
                    result = true
                )
                hideProgress()
            }
        }
    }

    fun onMoodSelected(mood: MoodTypes) {
        if (!_uiState.value.isEditable) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    mood = mood,
                )
            }
        }
    }

    fun onTitleChanged(title: String) {
        if (!_uiState.value.isEditable) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    title = title
                )
            }
        }
    }

    fun onDescriptionChanged(description: String) {
        if (!_uiState.value.isEditable) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    description = description
                )
            }
        }
    }

    fun onTagsChanged(tags: String) {
        if (!_uiState.value.isEditable) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    tags = tags
                )
            }
        }
    }

    fun onMediaChanged(mediaPaths: List<String>) {
        if (!_uiState.value.isEditable) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    mediaPaths = mediaPaths
                )
            }
        }
    }

}
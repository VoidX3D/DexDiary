package com.easylife.diary.feature.ai

import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import com.easylife.diary.domain.ai.AiClient
import com.easylife.diary.domain.ai.AiProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiEngineUiState(
    val openAiKey: String = "",
    val geminiKey: String = "",
    val groqKey: String = "",
    val inputText: String = "",
    val output: String = "Add API key in Settings",
    val provider: AiProvider = AiProvider.OPENAI
)

@HiltViewModel
class AiEngineViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val aiClient: AiClient
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(AiEngineUiState())
    val uiState: StateFlow<AiEngineUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    openAiKey = preferencesManager.getString(PreferenceKeys.OPENAI_API_KEY, "") ?: "",
                    geminiKey = preferencesManager.getString(PreferenceKeys.GEMINI_API_KEY, "") ?: "",
                    groqKey = preferencesManager.getString(PreferenceKeys.GROQ_API_KEY, "") ?: ""
                )
            }
        }
    }

    fun onProviderChanged(provider: AiProvider) {
        _uiState.update { it.copy(provider = provider) }
    }

    fun onInputChanged(value: String) {
        _uiState.update { it.copy(inputText = value) }
    }

    fun onOpenAiKeyChanged(value: String) {
        _uiState.update { it.copy(openAiKey = value.trim()) }
    }

    fun onGeminiKeyChanged(value: String) {
        _uiState.update { it.copy(geminiKey = value.trim()) }
    }

    fun onGroqKeyChanged(value: String) {
        _uiState.update { it.copy(groqKey = value.trim()) }
    }

    fun saveKeys() {
        viewModelScope.launch {
            val state = _uiState.value
            preferencesManager.setString(PreferenceKeys.OPENAI_API_KEY, state.openAiKey)
            preferencesManager.setString(PreferenceKeys.GEMINI_API_KEY, state.geminiKey)
            preferencesManager.setString(PreferenceKeys.GROQ_API_KEY, state.groqKey)
            _uiState.update { it.copy(output = "API keys saved locally.") }
        }
    }

    fun summarizeEntry() = runPrompt("Summarize this diary entry:\n${_uiState.value.inputText}")
    fun detectMood() = runPrompt("Detect mood from this text and keep response short:\n${_uiState.value.inputText}")
    fun suggestTags() = runPrompt("Suggest 5 short tags for this text:\n${_uiState.value.inputText}")
    fun writingPrompt() = runPrompt("Give one writing prompt inspired by this diary text:\n${_uiState.value.inputText}")

    private fun runPrompt(prompt: String) {
        viewModelScope.launch {
            val state = _uiState.value
            val key = when (state.provider) {
                AiProvider.OPENAI -> state.openAiKey
                AiProvider.GEMINI -> state.geminiKey
                AiProvider.GROQ -> state.groqKey
            }
            if (key.isBlank()) {
                _uiState.update { it.copy(output = "Add API key in Settings") }
                return@launch
            }
            val result = aiClient.runPrompt(state.provider, key, prompt)
            _uiState.update { it.copy(output = result) }
        }
    }
}

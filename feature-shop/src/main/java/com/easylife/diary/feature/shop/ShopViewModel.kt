package com.easylife.diary.feature.shop

import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.domain.usecases.BuyItemUseCase
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val buyItemUseCase: BuyItemUseCase,
    private val preferencesManager: PreferencesManager
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ShopUiState())
    val uiState: StateFlow<ShopUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    currentStreak = preferencesManager.getInt(PreferenceKeys.CURRENT_STREAK, 0),
                    currentPts = preferencesManager.getInt(PreferenceKeys.PTS, 0),
                    streakFreezeCount = preferencesManager.getInt(PreferenceKeys.STREAK_FREEZE_COUNT, 0),
                    unlockedThemes = preferencesManager.getStringSet(PreferenceKeys.UNLOCKED_THEMES, emptySet()),
                    isDarkModeUnlocked = preferencesManager.getBoolean(PreferenceKeys.IS_DARK_MODE_UNLOCKED, false)
                )
            }
        }
    }

    fun buyItem(item: BuyItemUseCase.ShopItem, themeId: String? = null) {
        viewModelScope.launch {
            val result = buyItemUseCase.execute(item, themeId)
            result.exceptionOrNull()?.message?.let { _error.postValue(it) }
            refresh()
        }
    }
}

package com.easylife.diary.presentation.ui.setting

import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.designsystem.motion.MotionPreferences
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by erenalpaslan on 1.01.2023
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    val navigator: DiaryNavigator,
    private val preferencesManager: PreferencesManager
): BaseViewModel() {
    private val _reduceMotionEnabled = MutableStateFlow(false)
    val reduceMotionEnabled: StateFlow<Boolean> = _reduceMotionEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            val isReduced = preferencesManager.getBoolean(PreferenceKeys.REDUCE_MOTION_ENABLED, false)
            _reduceMotionEnabled.value = isReduced
            MotionPreferences.setReduceMotion(isReduced)
        }
    }

    fun onThemeButtonClicked() {
        navigator.navigate(DiaryRoutes.themeRoute)
    }

    fun onReduceMotionToggled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.REDUCE_MOTION_ENABLED, enabled)
            _reduceMotionEnabled.value = enabled
            MotionPreferences.setReduceMotion(enabled)
        }
    }

}
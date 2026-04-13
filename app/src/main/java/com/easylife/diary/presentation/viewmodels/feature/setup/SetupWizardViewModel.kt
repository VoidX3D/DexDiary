package com.easylife.diary.feature.setup

import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupWizardViewModel @Inject constructor(
    private val navigator: DiaryNavigator,
    private val preferencesManager: PreferencesManager
) : BaseViewModel() {

    fun nextFromWelcome() {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.SETUP_WELCOME_COMPLETED, true)
            navigator.navigate(DiaryRoutes.setupStorageRoute)
        }
    }

    fun nextFromStorage() {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.SETUP_STORAGE_COMPLETED, true)
            navigator.navigate(DiaryRoutes.setupNotificationsRoute)
        }
    }

    fun nextFromNotifications() {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.SETUP_NOTIFICATIONS_COMPLETED, true)
            navigator.navigate(DiaryRoutes.setupBatteryRoute)
        }
    }

    fun nextFromBattery() {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.SETUP_BATTERY_COMPLETED, true)
            navigator.navigate(DiaryRoutes.setupReadyRoute)
        }
    }

    fun finishSetup() {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.SETUP_READY_COMPLETED, true)
            preferencesManager.setBoolean(PreferenceKeys.MAPS_SETUP_COMPLETED, true)
            val isFirstEnter = preferencesManager.getBoolean(PreferenceKeys.IS_FIRST_ENTER, true)
            navigator.navigate(if (isFirstEnter) DiaryRoutes.themeRoute else DiaryRoutes.diaryRoute) {
                popUpTo(DiaryRoutes.setupWelcomeRoute) { inclusive = true }
                launchSingleTop = true
            }
        }
    }
}

package com.github.feature.setting

import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import com.github.feature.setting.notification.DiaryNotificationScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by erenalpaslan on 14.04.2026
 */
@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val notificationScheduler: DiaryNotificationScheduler
) : BaseViewModel() {

    private val _isDailyReminderEnabled = MutableStateFlow(false)
    val isDailyReminderEnabled: StateFlow<Boolean> = _isDailyReminderEnabled.asStateFlow()

    private val _isStreakRiskWarningEnabled = MutableStateFlow(false)
    val isStreakRiskWarningEnabled: StateFlow<Boolean> = _isStreakRiskWarningEnabled.asStateFlow()

    private val _isAIVibeNotificationEnabled = MutableStateFlow(false)
    val isAIVibeNotificationEnabled: StateFlow<Boolean> = _isAIVibeNotificationEnabled.asStateFlow()

    init {
        loadPreferences()
    }

    private fun loadPreferences() {
        viewModelScope.launch {
            _isDailyReminderEnabled.value = preferencesManager.getBoolean(PreferenceKeys.DAILY_REMINDER_ENABLED, false)
            _isStreakRiskWarningEnabled.value = preferencesManager.getBoolean(PreferenceKeys.STREAK_RISK_WARNING_ENABLED, false)
            _isAIVibeNotificationEnabled.value = preferencesManager.getBoolean(PreferenceKeys.AI_VIBE_NOTIFICATION_ENABLED, false)
        }
    }

    fun setDailyReminderEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.DAILY_REMINDER_ENABLED, enabled)
            _isDailyReminderEnabled.value = enabled
            notificationScheduler.setDailyReminder(enabled)
        }
    }

    fun setStreakRiskWarningEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.STREAK_RISK_WARNING_ENABLED, enabled)
            _isStreakRiskWarningEnabled.value = enabled
            notificationScheduler.setStreakRiskWarning(enabled)
        }
    }

    fun setAIVibeNotificationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setBoolean(PreferenceKeys.AI_VIBE_NOTIFICATION_ENABLED, enabled)
            _isAIVibeNotificationEnabled.value = enabled
            notificationScheduler.setOracleVibe(enabled)
        }
    }
}

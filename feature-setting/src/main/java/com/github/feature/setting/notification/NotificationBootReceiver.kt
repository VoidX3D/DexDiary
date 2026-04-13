package com.github.feature.setting.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED &&
            intent?.action != Intent.ACTION_LOCKED_BOOT_COMPLETED
        ) return

        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefs = PreferencesManager(context)
                val scheduler = DiaryNotificationScheduler(context)
                scheduler.setDailyReminder(prefs.getBoolean(PreferenceKeys.DAILY_REMINDER_ENABLED, false))
                scheduler.setStreakRiskWarning(prefs.getBoolean(PreferenceKeys.STREAK_RISK_WARNING_ENABLED, false))
                scheduler.setOracleVibe(prefs.getBoolean(PreferenceKeys.AI_VIBE_NOTIFICATION_ENABLED, false))
            } finally {
                pending.finish()
            }
        }
    }
}

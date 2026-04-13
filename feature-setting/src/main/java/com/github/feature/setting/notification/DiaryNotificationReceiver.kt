package com.github.feature.setting.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val type = intent?.getStringExtra(EXTRA_TYPE) ?: TYPE_DAILY
        ensureChannel(context)

        val (title, body, id) = when (type) {
            TYPE_STREAK_RISK -> Triple("Streak at Risk!", "Write now to protect your streak.", 2)
            TYPE_ORACLE -> Triple("Oracle Vibe", "Your vibe forecast is ready in Insights.", 3)
            else -> Triple("Daily Reminder", "Time to write today's diary entry.", 1)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(id, notification)
        reschedule(context, type)
    }

    private fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Dex Diary Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "dex_diary_reminders"
        const val EXTRA_TYPE = "extra_notification_type"
        const val TYPE_DAILY = "daily"
        const val TYPE_STREAK_RISK = "streak_risk"
        const val TYPE_ORACLE = "oracle"
    }

    private fun reschedule(context: Context, type: String) {
        val pending = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefs = PreferencesManager(context)
                val scheduler = DiaryNotificationScheduler(context)
                when (type) {
                    TYPE_STREAK_RISK -> scheduler.setStreakRiskWarning(
                        prefs.getBoolean(PreferenceKeys.STREAK_RISK_WARNING_ENABLED, false)
                    )
                    TYPE_ORACLE -> scheduler.setOracleVibe(
                        prefs.getBoolean(PreferenceKeys.AI_VIBE_NOTIFICATION_ENABLED, false)
                    )
                    else -> scheduler.setDailyReminder(
                        prefs.getBoolean(PreferenceKeys.DAILY_REMINDER_ENABLED, false)
                    )
                }
            } finally {
                pending.finish()
            }
        }
    }
}

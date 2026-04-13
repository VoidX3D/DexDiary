package com.github.feature.setting.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryNotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setDailyReminder(enabled: Boolean) {
        schedule(enabled, DAILY_REMINDER_REQUEST, 20, 0, DiaryNotificationReceiver.TYPE_DAILY)
    }

    fun setStreakRiskWarning(enabled: Boolean) {
        schedule(enabled, STREAK_WARNING_REQUEST, 23, 0, DiaryNotificationReceiver.TYPE_STREAK_RISK)
    }

    fun setOracleVibe(enabled: Boolean) {
        schedule(enabled, ORACLE_REQUEST, 8, 0, DiaryNotificationReceiver.TYPE_ORACLE)
    }

    private fun schedule(
        enabled: Boolean,
        requestCode: Int,
        hour: Int,
        minute: Int,
        type: String
    ) {
        val pendingIntent = createPendingIntent(requestCode, type)
        if (!enabled) {
            alarmManager.cancel(pendingIntent)
            return
        }

        val now = LocalDateTime.now()
        var trigger = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)
        if (trigger <= now) trigger = trigger.plusDays(1)

        val triggerMillis = trigger.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerMillis, pendingIntent)
    }

    private fun createPendingIntent(requestCode: Int, type: String): PendingIntent {
        val intent = Intent(context, DiaryNotificationReceiver::class.java)
            .putExtra(DiaryNotificationReceiver.EXTRA_TYPE, type)
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private companion object {
        const val DAILY_REMINDER_REQUEST = 1001
        const val STREAK_WARNING_REQUEST = 1002
        const val ORACLE_REQUEST = 1003
    }
}

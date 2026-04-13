package com.easylife.diary.core.crash

import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager

class CrashAutoFixer(
    private val preferencesManager: PreferencesManager
) {
    suspend fun autoFix(report: CrashReport): String {
        return when {
            report.throwableType.contains("NullPointerException") -> {
                preferencesManager.setBoolean(PreferenceKeys.MAPS_SETUP_COMPLETED, false)
                "Possible broken state reset. Setup flow will run again."
            }

            report.message.contains("permission", ignoreCase = true) ||
                report.stackTrace.contains("SecurityException") -> {
                preferencesManager.setBoolean(PreferenceKeys.SETUP_NOTIFICATIONS_COMPLETED, false)
                "Permission-related state reset. Please grant permissions in setup."
            }

            else -> "No known auto-fix for this crash type."
        }
    }
}

package com.easylife.diary.core.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

object PreferenceKeys {
    const val PREFERENCES_NAME = "DIARY_PREFERENCES"

    val IS_FIRST_ENTER = booleanPreferencesKey("IS_FIRST_ENTER")
    val SELECTED_THEME_ID = intPreferencesKey("SELECTED_THEME_ID")

    val PTS = intPreferencesKey("PTS")
    val CURRENT_STREAK = intPreferencesKey("CURRENT_STREAK")
    val LONGEST_CHAIN = intPreferencesKey("LONGEST_CHAIN")
    val LAST_STREAK_DATE = longPreferencesKey("LAST_STREAK_DATE")
    val STREAK_FREEZE_COUNT = intPreferencesKey("STREAK_FREEZE_COUNT")
    val DOUBLE_POINTS_EXPIRY = longPreferencesKey("DOUBLE_POINTS_EXPIRY")
    val IS_DARK_MODE_UNLOCKED = booleanPreferencesKey("IS_DARK_MODE_UNLOCKED")
    val UNLOCKED_THEMES = stringSetPreferencesKey("UNLOCKED_THEMES")

    val LAST_ENTRY_DATE = stringPreferencesKey("LAST_ENTRY_DATE")
    val TOTAL_WORDS_WRITTEN = intPreferencesKey("TOTAL_WORDS_WRITTEN")
    val ACHIEVEMENTS_UNLOCKED = stringSetPreferencesKey("ACHIEVEMENTS_UNLOCKED")

    val DAILY_REMINDER_ENABLED = booleanPreferencesKey("DAILY_REMINDER_ENABLED")
    val STREAK_RISK_WARNING_ENABLED = booleanPreferencesKey("STREAK_RISK_WARNING_ENABLED")
    val AI_VIBE_NOTIFICATION_ENABLED = booleanPreferencesKey("AI_VIBE_NOTIFICATION_ENABLED")
    val REDUCE_MOTION_ENABLED = booleanPreferencesKey("REDUCE_MOTION_ENABLED")
    val MAPS_SETUP_COMPLETED = booleanPreferencesKey("MAPS_SETUP_COMPLETED")
    val MAP_PROVIDER = stringPreferencesKey("MAP_PROVIDER")
    val USER_GOOGLE_MAPS_API_KEY = stringPreferencesKey("USER_GOOGLE_MAPS_API_KEY")
    val SETUP_WELCOME_COMPLETED = booleanPreferencesKey("SETUP_WELCOME_COMPLETED")
    val SETUP_STORAGE_COMPLETED = booleanPreferencesKey("SETUP_STORAGE_COMPLETED")
    val SETUP_NOTIFICATIONS_COMPLETED = booleanPreferencesKey("SETUP_NOTIFICATIONS_COMPLETED")
    val SETUP_BATTERY_COMPLETED = booleanPreferencesKey("SETUP_BATTERY_COMPLETED")
    val SETUP_READY_COMPLETED = booleanPreferencesKey("SETUP_READY_COMPLETED")

    val OPENAI_API_KEY = stringPreferencesKey("OPENAI_API_KEY")
    val GEMINI_API_KEY = stringPreferencesKey("GEMINI_API_KEY")
    val GROQ_API_KEY = stringPreferencesKey("GROQ_API_KEY")
    val BACKUP_REMINDER_ENABLED = booleanPreferencesKey("BACKUP_REMINDER_ENABLED")
    val BACKUP_ENCRYPTION_ENABLED = booleanPreferencesKey("BACKUP_ENCRYPTION_ENABLED")
}
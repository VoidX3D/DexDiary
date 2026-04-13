package com.easylife.diary.core.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

object PreferencesKeys {
    const val PREFERENCES_NAME = "DIARY_PREFERENCES"
    val IS_FIRST_ENTER = booleanPreferencesKey("IS_FIRST_ENTER")
    val SELECTED_THEME_ID = intPreferencesKey("SELECTED_THEME_ID")
    val LAST_ENTRY_DATE = stringPreferencesKey("LAST_ENTRY_DATE")
    val LONGEST_CHAIN = intPreferencesKey("LONGEST_CHAIN")
    val LAST_CHAIN = intPreferencesKey("LAST_CHAIN")

    // Dex Diary Specific Keys
    val PTS = intPreferencesKey("PTS")
    val STREAK_FREEZE_COUNT = intPreferencesKey("STREAK_FREEZE_COUNT")
    val DOUBLE_POINTS_EXPIRY = longPreferencesKey("DOUBLE_POINTS_EXPIRY")
    val IS_DARK_MODE_UNLOCKED = booleanPreferencesKey("IS_DARK_MODE_UNLOCKED")
    val UNLOCKED_THEMES = stringSetPreferencesKey("UNLOCKED_THEMES")
    val LAST_STREAK_DATE = longPreferencesKey("LAST_STREAK_DATE")
    val CURRENT_STREAK = intPreferencesKey("CURRENT_STREAK")
}
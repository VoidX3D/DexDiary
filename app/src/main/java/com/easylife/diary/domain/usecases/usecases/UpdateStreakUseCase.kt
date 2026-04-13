package com.easylife.diary.core.domain.usecases

import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

/**
 * Created by erenalpaslan on 14.04.2026
 */
class UpdateStreakUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {

    suspend fun execute() {
        val lastStreakTimestamp = preferencesManager.getLong(PreferenceKeys.LAST_STREAK_DATE, -1L)
        val today = LocalDate.now()
        
        if (lastStreakTimestamp == -1L) {
            // First time ever
            preferencesManager.setInt(PreferenceKeys.CURRENT_STREAK, 1)
            preferencesManager.setLong(PreferenceKeys.LAST_STREAK_DATE, System.currentTimeMillis())
            preferencesManager.setInt(PreferenceKeys.LONGEST_CHAIN, 1)
            return
        }

        val lastStreakDate = Instant.ofEpochMilli(lastStreakTimestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        val daysBetween = ChronoUnit.DAYS.between(lastStreakDate, today)

        when {
            daysBetween == 0L -> {
                // Already updated today, do nothing
            }
            daysBetween == 1L -> {
                // Consecutive day
                val newStreak = preferencesManager.getInt(PreferenceKeys.CURRENT_STREAK, 0) + 1
                preferencesManager.setInt(PreferenceKeys.CURRENT_STREAK, newStreak)
                preferencesManager.setLong(PreferenceKeys.LAST_STREAK_DATE, System.currentTimeMillis())
                
                val longestChain = preferencesManager.getInt(PreferenceKeys.LONGEST_CHAIN, 0)
                if (newStreak > longestChain) {
                    preferencesManager.setInt(PreferenceKeys.LONGEST_CHAIN, newStreak)
                }
            }
            else -> {
                // Missed day(s)
                val streakFreezes = preferencesManager.getInt(PreferenceKeys.STREAK_FREEZE_COUNT, 0)
                if (streakFreezes > 0) {
                    // Use a streak freeze
                    preferencesManager.setInt(PreferenceKeys.STREAK_FREEZE_COUNT, streakFreezes - 1)
                    // Streak continues but date is updated to today
                    val newStreak = preferencesManager.getInt(PreferenceKeys.CURRENT_STREAK, 0) + 1
                    preferencesManager.setInt(PreferenceKeys.CURRENT_STREAK, newStreak)
                    preferencesManager.setLong(PreferenceKeys.LAST_STREAK_DATE, System.currentTimeMillis())
                } else {
                    // Reset streak
                    preferencesManager.setInt(PreferenceKeys.CURRENT_STREAK, 1)
                    preferencesManager.setLong(PreferenceKeys.LAST_STREAK_DATE, System.currentTimeMillis())
                }
            }
        }
    }
}

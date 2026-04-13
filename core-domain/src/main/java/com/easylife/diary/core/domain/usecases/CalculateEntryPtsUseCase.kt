package com.easylife.diary.core.domain.usecases

import com.easylife.diary.core.common.util.dispatchers.DiaryDispatchers
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by erenalpaslan on 14.04.2026
 */
class CalculateEntryPtsUseCase @Inject constructor(
    private val dispatchers: DiaryDispatchers,
    private val preferencesManager: PreferencesManager
) {

    suspend fun execute(wordCount: Int, hasMedia: Boolean): Int {
        val basePts = 10
        val wordBonus = (wordCount / 250) * 5
        val mediaBonus = if (hasMedia) 5 else 0
        
        val currentStreak = preferencesManager.getInt(PreferenceKeys.CURRENT_STREAK, 0)
        val multiplier = when {
            currentStreak >= 30 -> 3.0
            currentStreak >= 14 -> 2.5
            currentStreak >= 7 -> 2.0
            currentStreak >= 3 -> 1.5
            else -> 1.0
        }

        val doublePointsExpiry = preferencesManager.getLong(PreferenceKeys.DOUBLE_POINTS_EXPIRY, -1L)
        val doubleMultiplier = if (doublePointsExpiry > System.currentTimeMillis()) 2.0 else 1.0

        val totalPts = ((basePts + wordBonus + mediaBonus) * multiplier * doubleMultiplier).toInt()
        
        val currentTotalPts = preferencesManager.getInt(PreferenceKeys.PTS, 0)
        preferencesManager.setInt(PreferenceKeys.PTS, currentTotalPts + totalPts)
        
        return totalPts
    }
}

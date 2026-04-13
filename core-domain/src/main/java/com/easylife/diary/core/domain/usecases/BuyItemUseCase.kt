package com.easylife.diary.core.domain.usecases

import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import javax.inject.Inject

/**
 * Created by erenalpaslan on 14.04.2026
 */
class BuyItemUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager
) {

    enum class ShopItem(val cost: Int) {
        STREAK_FREEZE(500),
        DOUBLE_POINTS(1000),
        DARK_MODE(5000),
        PREMIUM_THEME(2000)
    }

    suspend fun execute(item: ShopItem, themeId: String? = null): Result<Unit> {
        val currentPts = preferencesManager.getInt(PreferenceKeys.PTS, 0)
        
        if (currentPts < item.cost) {
            return Result.failure(Exception("Not enough PTS"))
        }

        when (item) {
            ShopItem.STREAK_FREEZE -> {
                val currentFreezes = preferencesManager.getInt(PreferenceKeys.STREAK_FREEZE_COUNT, 0)
                if (currentFreezes >= 2) return Result.failure(Exception("Max streak freezes reached"))
                preferencesManager.setInt(PreferenceKeys.STREAK_FREEZE_COUNT, currentFreezes + 1)
            }
            ShopItem.DOUBLE_POINTS -> {
                val twentyFourHours = 24 * 60 * 60 * 1000L
                preferencesManager.setLong(PreferenceKeys.DOUBLE_POINTS_EXPIRY, System.currentTimeMillis() + twentyFourHours)
            }
            ShopItem.DARK_MODE -> {
                preferencesManager.setBoolean(PreferenceKeys.IS_DARK_MODE_UNLOCKED, true)
            }
            ShopItem.PREMIUM_THEME -> {
                themeId?.let { id ->
                    val unlocked = preferencesManager.getStringSet(PreferenceKeys.UNLOCKED_THEMES).toMutableSet()
                    unlocked.add(id)
                    preferencesManager.setStringSet(PreferenceKeys.UNLOCKED_THEMES, unlocked)
                } ?: return Result.failure(Exception("Theme ID required"))
            }
        }

        preferencesManager.setInt(PreferenceKeys.PTS, currentPts - item.cost)
        return Result.success(Unit)
    }
}

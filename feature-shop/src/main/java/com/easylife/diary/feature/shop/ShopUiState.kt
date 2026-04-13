package com.easylife.diary.feature.shop

data class ShopUiState(
    val currentStreak: Int = 0,
    val currentPts: Int = 0,
    val streakFreezeCount: Int = 0,
    val unlockedThemes: Set<String> = emptySet(),
    val isDarkModeUnlocked: Boolean = false
)

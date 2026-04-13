package com.easylife.diary.core.domain.usecases

import com.easylife.diary.core.common.util.dispatchers.DiaryDispatchers
import com.easylife.diary.core.data.repository.EntryRepository
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EvaluateAchievementsUseCase @Inject constructor(
    private val dispatchers: DiaryDispatchers,
    private val entryRepository: EntryRepository,
    private val preferencesManager: PreferencesManager
) {
    suspend fun execute(): List<String> = withContext(dispatchers.io) {
        val unlocked = preferencesManager
            .getStringSet(PreferenceKeys.ACHIEVEMENTS_UNLOCKED, emptySet())
            .toMutableSet()

        val totalWords = preferencesManager.getInt(PreferenceKeys.TOTAL_WORDS_WRITTEN, 0)
        val streak = preferencesManager.getInt(PreferenceKeys.LONGEST_CHAIN, 0)
        val unlockedThemes = preferencesManager.getStringSet(PreferenceKeys.UNLOCKED_THEMES, emptySet()).size
        val latest = entryRepository.getLatestEntry()
        val isNightOwl = latest?.date?.hours?.contains("AM") == true

        if (totalWords >= 1000) unlocked.add("The Novice")
        if (streak >= 30) unlocked.add("The Monk")
        if (unlockedThemes >= 3) unlocked.add("The Alchemist")
        if (isNightOwl) unlocked.add("Night Owl")

        preferencesManager.setStringSet(PreferenceKeys.ACHIEVEMENTS_UNLOCKED, unlocked)
        unlocked.toList().sorted()
    }
}

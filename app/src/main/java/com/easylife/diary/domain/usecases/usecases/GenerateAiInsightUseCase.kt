package com.easylife.diary.core.domain.usecases

import com.easylife.diary.core.common.util.dispatchers.DiaryDispatchers
import com.easylife.diary.core.data.repository.EntryRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenerateAiInsightUseCase @Inject constructor(
    private val dispatchers: DiaryDispatchers,
    private val entryRepository: EntryRepository
) {
    suspend fun execute(): Pair<String, String> = withContext(dispatchers.io) {
        val latest = entryRepository.getLatestEntry()
        if (latest == null) {
            return@withContext "Write your first entry to unlock your weekly reflection." to "Calm and curious"
        }

        val text = latest.description.orEmpty().lowercase()
        val vibe = when {
            text.contains("happy") || text.contains("great") || text.contains("good") -> "Optimistic and upbeat"
            text.contains("tired") || text.contains("stress") || text.contains("anxious") -> "A bit heavy, be gentle today"
            else -> "Steady and reflective"
        }

        val summary = buildString {
            append("You are showing up consistently. ")
            append("Recent entries indicate a ")
            append(vibe.lowercase())
            append(" pattern. Keep a short check-in tonight to maintain momentum.")
        }

        summary to vibe
    }
}

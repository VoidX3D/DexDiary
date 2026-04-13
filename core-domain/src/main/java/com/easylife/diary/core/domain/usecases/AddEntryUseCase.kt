package com.easylife.diary.core.domain.usecases

import com.easylife.diary.core.common.util.DateUtil
import com.easylife.diary.core.common.util.DiaryResult
import com.easylife.diary.core.common.util.dispatchers.DiaryDispatchers
import com.easylife.diary.core.data.repository.EntryRepository
import com.easylife.diary.core.model.DiaryNote
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by erenalpaslan on 9.01.2023
 */
class AddEntryUseCase @Inject constructor(
    private val dispatchers: DiaryDispatchers,
    private val entryRepository: EntryRepository,
    private val updateStreakUseCase: UpdateStreakUseCase,
    private val calculateEntryPtsUseCase: CalculateEntryPtsUseCase,
    private val preferencesManager: PreferencesManager
): BaseUseCase<Int, AddEntryUseCase.Params>() {

    data class Params(
        val title: String?,
        val description: String?,
        val moodId: Int?,
        val tags: String? = null,
        val mediaPaths: List<String> = emptyList()
    )

    override suspend fun execute(params: Params): Flow<DiaryResult<Int>> = flow {
        try {
            // Golden rule: one entry per current day.
            if (entryRepository.hasEntryForGivenLocalDate(LocalDate.now())) {
                emit(DiaryResult.Error("Today's entry already exists. Edit it instead of creating a new one."))
                return@flow
            }

            val diaryDate = DateUtil.getCurrentDiaryDate()
            
            // 2. Calculate Metadata
            val wordCount = params.description?.split("\\s+".toRegex())?.filter { it.isNotEmpty() }?.size ?: 0
            val complexity = 0.0 // Placeholder for real complexity algorithm

            // 3. Update Streak
            updateStreakUseCase.execute()

            // 4. Calculate & Award PTS
            val awardedPts = calculateEntryPtsUseCase.execute(wordCount, params.mediaPaths.isNotEmpty())
            val totalWords = preferencesManager.getInt(PreferenceKeys.TOTAL_WORDS_WRITTEN, 0)
            preferencesManager.setInt(PreferenceKeys.TOTAL_WORDS_WRITTEN, totalWords + wordCount)

            // 5. Save Entry
            val entry = DiaryNote(
                id = 0,
                moodId = params.moodId,
                title = params.title,
                description = params.description,
                date = diaryDate,
                wordCount = wordCount,
                tags = params.tags,
                complexity = complexity,
                mediaPaths = params.mediaPaths
            )
            entryRepository.addEntry(entry)
            
            emit(DiaryResult.Success(awardedPts))
        }catch (e: Exception) {
            emit(DiaryResult.Error(e.message))
        }
    }.flowOn(dispatchers.io)

}
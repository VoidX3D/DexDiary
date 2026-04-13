package com.easylife.diary.feature.diary

import com.easylife.diary.core.model.DiaryNote
import com.easylife.diary.core.model.EntryGroup

/**
 * Created by erenalpaslan on 1.01.2023
 */
sealed interface DiaryUiState {

    object Loading : DiaryUiState

    object EmptyDiary: DiaryUiState

    data class DataLoaded(
        val data: List<EntryGroup> = emptyList(),
        val currentDate: String? = null,
        val rawData: List<EntryGroup> = emptyList(),
        val streak: Int = 0,
        val pts: Int = 0
    ): DiaryUiState
}

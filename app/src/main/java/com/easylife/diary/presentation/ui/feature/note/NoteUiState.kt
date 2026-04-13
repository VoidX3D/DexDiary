package com.easylife.diary.feature.note

import com.easylife.diary.core.designsystem.enums.MoodTypes

/**
 * Created by erenalpaslan on 8.01.2023
 */
data class NoteUiState(
    var doneVisible: Boolean = false,
    var title: String = "",
    var description: String = "",
    var date: String = "",
    var mood: MoodTypes? = null,
    var isEditing: Boolean = false,
    var tags: String = "",
    var mediaPaths: List<String> = emptyList(),
    var isEditable: Boolean = true
) {
    fun isChanged(): Boolean {
        return title.isNotEmpty() || description.isNotEmpty() || mood != null || tags.isNotEmpty() || mediaPaths.isNotEmpty()
    }
}

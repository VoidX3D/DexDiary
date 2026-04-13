package com.easylife.diary.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Created by erenalpaslan on 3.01.2023
 */
@Composable
fun DiaryTitleTextField(
    title: String?,
    enabled: Boolean = true,
    onTextChanged: (String) -> Unit
) {
    TextField(
        value = title ?: "",
        onValueChange = { newTitle ->
            if (newTitle.length < 64)
                onTextChanged(newTitle)
        },
        enabled = enabled,
        placeholder = {
            Text(
                "Title",
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        textStyle = MaterialTheme.typography.titleLarge
    )
}
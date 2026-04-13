package com.easylife.diary.ui.screen.main.theme

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import com.easylife.diary.core.designsystem.theme.AppDiaryTheme
import com.easylife.diary.core.designsystem.theme.DefaultColorScheme
import com.easylife.diary.core.designsystem.theme.DefaultTypography
import com.easylife.diary.feature.theme.util.DiaryTheme

@Composable
fun DiaryTheme(
    diaryTheme: DiaryTheme? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val systemDark = isSystemInDarkTheme()
    val pixelLikeDynamicScheme = remember(context, systemDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (systemDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        } else {
            DefaultColorScheme
        }
    }
    AppDiaryTheme(
        colorScheme = diaryTheme?.colorScheme ?: pixelLikeDynamicScheme,
        typography = diaryTheme?.typography ?: DefaultTypography,
        content = content
    )
}
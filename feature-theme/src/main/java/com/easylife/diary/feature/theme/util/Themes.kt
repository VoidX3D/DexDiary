package com.easylife.diary.feature.theme.util

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.easylife.diary.core.designsystem.theme.DefaultColorScheme
import com.easylife.diary.core.designsystem.theme.DefaultTypography
import com.easylife.diary.core.designsystem.theme.blue_theme_primary
import com.easylife.diary.core.designsystem.theme.blue_theme_secondary
import com.easylife.diary.core.designsystem.theme.blue_theme_tertiary
import com.easylife.diary.core.designsystem.theme.md_theme_background
import com.easylife.diary.core.designsystem.theme.red_theme_primary
import com.easylife.diary.core.designsystem.theme.red_theme_secondary
import com.easylife.diary.core.designsystem.theme.red_theme_tertiary
import com.easylife.diary.core.designsystem.theme.yellow_theme_primary
import com.easylife.diary.core.designsystem.theme.yellow_theme_secondary
import com.easylife.diary.core.designsystem.theme.yellow_theme_tertiary

/**
 * Created by erenalpaslan on 25.12.2022
 */
enum class Themes(val diaryTheme: DiaryTheme) {
    HONEY(
        DiaryTheme(
            id = 1,
            name = "Honey Theme",
            colorScheme = DefaultColorScheme,
            typography = DefaultTypography,
            parentBackgroundColor = md_theme_background
        )
    ),
    DARK(
        DiaryTheme(
            id = 2,
            name = "Dark Theme",
            colorScheme = darkColorScheme(
                primary = blue_theme_primary,
                secondary = blue_theme_secondary,
                tertiary = blue_theme_tertiary
            ),
            typography = DefaultTypography,
            parentBackgroundColor = Color(0xFF121212),
            isPremium = true
        )
    ),
    PINK(
        DiaryTheme(
            id = 3,
            name = "Sunset Theme",
            colorScheme = lightColorScheme(
                primary = red_theme_primary,
                secondary = red_theme_secondary,
                tertiary = red_theme_tertiary
            ),
            typography = DefaultTypography,
            parentBackgroundColor = Color(0xFFFFF8F6),
            isPremium = true
        )
    ),
    GOLD(
        DiaryTheme(
            id = 4,
            name = "Gold Theme",
            colorScheme = lightColorScheme(
                primary = yellow_theme_primary,
                secondary = yellow_theme_secondary,
                tertiary = yellow_theme_tertiary
            ),
            typography = DefaultTypography,
            parentBackgroundColor = Color(0xFFFFF9EE),
            isPremium = true
        )
    );

    companion object {
        infix fun from(id: Int): DiaryTheme? = Themes.values().firstOrNull { it.diaryTheme.id == id }?.diaryTheme
        fun getThemes(): List<DiaryTheme> = Themes.values().map { it.diaryTheme }
    }
}
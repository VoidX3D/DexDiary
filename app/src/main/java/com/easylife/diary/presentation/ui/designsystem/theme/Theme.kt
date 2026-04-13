package com.easylife.diary.core.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

val DefaultColorScheme = lightColorScheme(
    primary = md_theme_primary,
    onPrimary = md_theme_onPrimary,
    primaryContainer = md_theme_primaryContainer,
    onPrimaryContainer = md_theme_onPrimaryContainer,
    secondary = md_theme_secondary,
    onSecondary = md_theme_onSecondary,
    secondaryContainer = md_theme_secondaryContainer,
    onSecondaryContainer = md_theme_onSecondaryContainer,
    tertiary = md_theme_tertiary,
    onTertiary = md_theme_onTertiary,
    tertiaryContainer = md_theme_tertiaryContainer,
    onTertiaryContainer = md_theme_onTertiaryContainer,
    error = md_theme_error,
    onError = md_theme_onError,
    background = md_theme_background,
    onBackground = md_theme_onBackground,
    surface = md_theme_surface,
    onSurface = md_theme_onSurface,
    surfaceVariant = md_theme_surfaceVariant,
    onSurfaceVariant = md_theme_onSurfaceVariant,
    outline = md_theme_outline
)

val DefaultDarkColorScheme = darkColorScheme(
    primary = md_theme_primaryContainer,
    onPrimary = md_theme_onPrimaryContainer,
    secondary = md_theme_secondaryContainer,
    onSecondary = md_theme_onSecondaryContainer,
    tertiary = md_theme_tertiaryContainer,
    onTertiary = md_theme_onTertiaryContainer,
    background = md_theme_onBackground,
    onBackground = md_theme_background,
    surface = md_theme_onSurface,
    onSurface = md_theme_surface,
    surfaceVariant = md_theme_onSurfaceVariant,
    onSurfaceVariant = md_theme_surfaceVariant,
    outline = md_theme_outline
)

val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun AppDiaryTheme(
    colorScheme: ColorScheme? = null,
    typography: Typography = DefaultTypography,
    useDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val context = LocalContext.current
    val resolvedColorScheme = colorScheme ?: when {
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        isDark -> DefaultDarkColorScheme
        else -> DefaultColorScheme
    }

    MaterialTheme(
        colorScheme = resolvedColorScheme,
        typography = typography,
        shapes = ExpressiveShapes,
        content = content
    )
}
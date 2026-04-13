package com.easylife.diary.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes.splashRoute
import com.easylife.diary.feature.calendar.navigation.calendarScreen
import com.easylife.diary.feature.diary.navigation.diaryScreen
import com.easylife.diary.feature.ai.navigation.aiEngineScreen
import com.easylife.diary.feature.insight.navigation.insightsScreen
import com.easylife.diary.feature.mapsetup.navigation.mapSetupScreen
import com.easylife.diary.feature.note.navigation.noteScreen
import com.easylife.diary.feature.setup.navigation.setupWizardScreens
import com.easylife.diary.feature.splash.navigation.splashScreen
import com.easylife.diary.feature.theme.navigation.themeScreen
import com.easylife.diary.feature.shop.navigation.shopScreen
import com.easylife.diary.presentation.ui.setting.navigation.settingsScreen
@Composable
fun DiaryNavHost(
    navigator: DiaryNavigator,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = splashRoute,
        modifier = Modifier.padding(
            bottom = paddingValues.calculateBottomPadding()
        )
    ) {
        splashScreen(navigator)
        setupWizardScreens(navigator)
        mapSetupScreen(navigator)
        themeScreen(navigator)
        shopScreen(navigator)
        noteScreen(navigator)
        diaryScreen(navigator)
        calendarScreen(navigator)
        insightsScreen(navigator)
        aiEngineScreen(navigator)
        settingsScreen(navigator)
    }
}
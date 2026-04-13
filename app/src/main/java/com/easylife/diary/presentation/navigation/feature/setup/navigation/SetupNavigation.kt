package com.easylife.diary.feature.setup.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.feature.setup.SetupBatteryScreen
import com.easylife.diary.feature.setup.SetupNotificationsScreen
import com.easylife.diary.feature.setup.SetupReadyScreen
import com.easylife.diary.feature.setup.SetupStorageScreen
import com.easylife.diary.feature.setup.SetupWelcomeScreen

fun NavGraphBuilder.setupWizardScreens(navigator: DiaryNavigator) {
    composable(DiaryRoutes.setupWelcomeRoute) {
        SetupWelcomeScreen().Create(navigator, hiltViewModel())
    }
    composable(DiaryRoutes.setupStorageRoute) {
        SetupStorageScreen().Create(navigator, hiltViewModel())
    }
    composable(DiaryRoutes.setupNotificationsRoute) {
        SetupNotificationsScreen().Create(navigator, hiltViewModel())
    }
    composable(DiaryRoutes.setupBatteryRoute) {
        SetupBatteryScreen().Create(navigator, hiltViewModel())
    }
    composable(DiaryRoutes.setupReadyRoute) {
        SetupReadyScreen().Create(navigator, hiltViewModel())
    }
}

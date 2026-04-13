package com.easylife.diary.presentation.ui.setting.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.core.navigation.screen.DiaryRoutes.settingsRoute
import com.easylife.diary.delete.data.navigation.deleteDataScreen
import com.easylife.diary.password.navigation.passwordScreen
import com.easylife.diary.presentation.ui.setting.backup.navigation.backupScreen
import com.easylife.diary.presentation.ui.setting.NotificationSettingsScreen
import com.easylife.diary.presentation.ui.setting.SettingsScreen

/**
 * Created by erenalpaslan on 1.01.2023
 */
fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsRoute, navOptions)
}

fun NavController.navigateToNotificationSettings() {
    this.navigate(DiaryRoutes.notificationSettingsRoute)
}


fun NavGraphBuilder.settingsScreen(
    navigator: DiaryNavigator
) {
    composable(route = settingsRoute) {
        SettingsScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
    passwordScreen(navigator)
    deleteDataScreen(navigator)
    backupScreen(navigator)
    composable(route = DiaryRoutes.notificationSettingsRoute) {
        NotificationSettingsScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}
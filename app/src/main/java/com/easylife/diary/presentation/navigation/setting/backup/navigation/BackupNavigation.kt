package com.easylife.diary.presentation.ui.setting.backup.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.presentation.ui.setting.BackupScreen

fun NavGraphBuilder.backupScreen(navigator: DiaryNavigator) {
    composable(DiaryRoutes.backupRoute) {
        BackupScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}

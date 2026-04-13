package com.easylife.diary.feature.mapsetup.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.feature.mapsetup.MapSetupScreen

fun NavGraphBuilder.mapSetupScreen(navigator: DiaryNavigator) {
    composable(route = DiaryRoutes.mapSetupRoute) {
        MapSetupScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}

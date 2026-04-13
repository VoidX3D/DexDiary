package com.easylife.diary.feature.ai.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.feature.ai.AiEngineScreen

fun NavGraphBuilder.aiEngineScreen(navigator: DiaryNavigator) {
    composable(DiaryRoutes.aiEngineRoute) {
        AiEngineScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}

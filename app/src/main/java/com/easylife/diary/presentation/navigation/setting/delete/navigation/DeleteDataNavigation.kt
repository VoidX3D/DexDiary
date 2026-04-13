package com.easylife.diary.delete.data.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.delete.data.DeleteDataScreen

/**
 * Created by erenalpaslan on 6.01.2023
 */
fun NavGraphBuilder.deleteDataScreen(
    navigator: DiaryNavigator
) {
    composable(DiaryRoutes.deleteDataRoute) {
        DeleteDataScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}
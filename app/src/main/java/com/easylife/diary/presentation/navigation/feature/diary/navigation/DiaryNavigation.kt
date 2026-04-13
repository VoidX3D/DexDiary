package com.easylife.diary.feature.diary.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes.diaryRoute
import com.easylife.diary.feature.diary.DiaryScreen

/**
 * Created by erenalpaslan on 1.01.2023
 */

fun NavController.navigateToDiary(navOptions: NavOptions? = null) {
    this.navigate(diaryRoute, navOptions)
}

fun NavGraphBuilder.diaryScreen(navigator: DiaryNavigator) {
    composable(route = diaryRoute) {
        DiaryScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}
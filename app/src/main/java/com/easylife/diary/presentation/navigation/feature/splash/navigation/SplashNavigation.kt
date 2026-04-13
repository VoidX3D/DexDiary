package com.easylife.diary.feature.splash.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes.splashRoute
import com.easylife.diary.feature.splash.SplashScreen

/**
 * Created by erenalpaslan on 23.12.2022
 */
fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    this.navigate(splashRoute, navOptions)
}

fun NavGraphBuilder.splashScreen(navigator: DiaryNavigator) {
    composable(route = splashRoute) {
        SplashScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}
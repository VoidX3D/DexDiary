package com.easylife.diary.feature.calendar.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes.calendarRoute
import com.easylife.diary.feature.calendar.CalendarScreen

/**
 * Created by erenalpaslan on 1.01.2023
 */
fun NavController.navigateToCalendar(navOptions: NavOptions? = null) {
    this.navigate(calendarRoute, navOptions)
}

fun NavGraphBuilder.calendarScreen(navigator: DiaryNavigator) {
    composable(route = calendarRoute) {
        CalendarScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}
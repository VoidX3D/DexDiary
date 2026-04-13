package com.easylife.diary.feature.shop.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.feature.shop.ShopScreen
import com.google.accompanist.navigation.animation.composable

const val shopRoute = "shop_route"

fun NavController.navigateToShop() {
    this.navigate(shopRoute)
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.shopScreen(navigator: DiaryNavigator) {
    composable(route = shopRoute) {
        ShopScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}

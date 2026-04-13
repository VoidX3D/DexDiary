package com.easylife.diary.feature.shop.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.feature.shop.ShopScreen

const val shopRoute = "shop_route"

fun NavController.navigateToShop() {
    this.navigate(shopRoute)
}

fun NavGraphBuilder.shopScreen(navigator: DiaryNavigator) {
    composable(route = shopRoute) {
        ShopScreen().Create(
            navigator = navigator,
            viewModel = hiltViewModel()
        )
    }
}

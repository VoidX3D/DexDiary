package com.easylife.diary.feature.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit // Placeholder for streak icon
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.core.domain.usecases.BuyItemUseCase
import com.easylife.diary.core.designsystem.theme.md_theme_primary
import com.easylife.diary.core.designsystem.theme.md_theme_secondary
import com.easylife.diary.core.domain.usecases.BuyItemUseCase.ShopItem

/**
 * Created by erenalpaslan on 14.04.2026
 */
class ShopScreen : BaseScreen<ShopViewModel>() {
    @Composable
    override fun Screen() {
        val uiState by viewModel.uiState.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("The Shop") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        ) { paddingValues ->
            ShopContent(
                uiState = uiState,
                paddingValues = paddingValues,
                onBuyItem = { item, themeId ->
                    viewModel.buyItem(item, themeId)
                }
            )
        }
    }
}

@Composable
fun ShopContent(
    uiState: ShopUiState,
    paddingValues: PaddingValues,
    onBuyItem: (ShopItem, String?) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        // Display PTS and Streak at the top
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.AcUnit, // Placeholder for streak icon
                    contentDescription = "Streak Icon",
                    tint = md_theme_primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Streak: ${uiState.currentStreak}", style = MaterialTheme.typography.titleMedium)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Diamond, // Placeholder for PTS icon
                    contentDescription = "PTS Icon",
                    tint = md_theme_secondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "PTS: ${uiState.currentPts}", style = MaterialTheme.typography.titleMedium)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Consumables", style = MaterialTheme.typography.labelLarge)
            }
            item {
                ShopItemCard(
                    item = ShopItem.STREAK_FREEZE,
                    description = "Prevents streak loss for one missed day.",
                    onBuyClick = { onBuyItem(ShopItem.STREAK_FREEZE, null) }
                )
            }
            item {
                ShopItemCard(
                    item = ShopItem.DOUBLE_POINTS,
                    description = "Doubles all PTS earned for 24 hours.",
                    onBuyClick = { onBuyItem(ShopItem.DOUBLE_POINTS, null) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Permanent Unlocks", style = MaterialTheme.typography.labelLarge)
            }
            item {
                ShopItemCard(
                    item = ShopItem.DARK_MODE,
                    description = "Unlock the coveted Dark Mode.",
                    onBuyClick = { onBuyItem(ShopItem.DARK_MODE, null) }
                )
            }
            item {
                ShopItemCard(
                    item = ShopItem.PREMIUM_THEME,
                    description = "Unlock a new visual theme.",
                    themeId = "ocean", // Example theme ID
                    onBuyClick = { onBuyItem(ShopItem.PREMIUM_THEME, "ocean") }
                )
            }
            item {
                ShopItemCard(
                    item = ShopItem.PREMIUM_THEME,
                    description = "Unlock another visual theme.",
                    themeId = "forest", // Example theme ID
                    onBuyClick = { onBuyItem(ShopItem.PREMIUM_THEME, "forest") }
                )
            }
        }
    }
}

@Composable
fun ShopItemCard(
    item: ShopItem,
    description: String,
    themeId: String? = null,
    onBuyClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.name.replace("_", " "), style = MaterialTheme.typography.titleMedium)
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "${item.cost} PTS", style = MaterialTheme.typography.titleSmall)
                Button(
                    onClick = onBuyClick,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Buy")
                }
            }
        }
    }
}

@Composable
fun rememberPreferencesManager(): PreferencesManager {
    val context = LocalContext.current
    return remember { PreferencesManager(context) }
}

package com.easylife.diary.feature.shop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AcUnit // Placeholder for streak icon
import androidx.compose.material.icons.rounded.Diamond
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.core.designsystem.theme.md_theme_primary
import com.easylife.diary.core.designsystem.theme.md_theme_secondary
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import com.easylife.diary.feature.shop.BuyItemUseCase.ShopItem
import com.easylife.diary.R // Assuming R.drawable.ic_fire and R.drawable.ic_gem exist
import kotlinx.coroutines.launch

/**
 * Created by erenalpaslan on 14.04.2026
 */
class ShopScreen : BaseScreen<ShopViewModel>() {
    @Composable
    override fun Screen() {
        val uiState by viewModel.uiState.collectAsState()
        val preferencesManager = remember { PreferencesManager(LocalContext.current) }
        val coroutineScope = rememberCoroutineScope()

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
                    coroutineScope.launch {
                        viewModel.buyItem(item, themeId)
                    }
                },
                preferencesManager = preferencesManager // Pass preferencesManager
            )
        }
    }
}

@Composable
fun ShopContent(
    uiState: ShopUiState,
    paddingValues: PaddingValues,
    onBuyItem: (ShopItem, String?) -> Unit,
    preferencesManager: PreferencesManager // Receive preferencesManager
) {
    val currentStreak by remember {
        mutableStateOf(preferencesManager.getInt(PreferenceKeys.CURRENT_STREAK, 0))
    }
    val currentPts by remember {
        mutableStateOf(preferencesManager.getInt(PreferenceKeys.PTS, 0))
    }

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
                Text(text = "Streak: $currentStreak", style = MaterialTheme.typography.titleMedium)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Diamond, // Placeholder for PTS icon
                    contentDescription = "PTS Icon",
                    tint = md_theme_secondary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "PTS: $currentPts", style = MaterialTheme.typography.titleMedium)
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

package com.easylife.diary.presentation.ui.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.core.designsystem.theme.md_theme_onSurfaceVariant

/**
 * Created by erenalpaslan on 14.04.2026
 */
class NotificationSettingsScreen : BaseScreen<NotificationSettingsViewModel>() {
    @Composable
    override fun Screen() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Notification Settings") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.navigateBack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Manage your notification preferences", style = MaterialTheme.typography.bodyMedium, color = md_theme_onSurfaceVariant)
                Spacer(modifier = Modifier.height(24.dp))

                // Example: Daily logging reminder toggle
                ReminderSettingItem(
                    title = "Daily Logging Reminder",
                    description = "Receive a gentle reminder to write at 8 PM.",
                    isChecked = viewModel.isDailyReminderEnabled.collectAsState().value,
                    onCheckedChange = { isChecked -> viewModel.setDailyReminderEnabled(isChecked) }
                )
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                // Example: Streak risk warning toggle
                ReminderSettingItem(
                    title = "Streak Risk Warning",
                    description = "Get an urgent warning at 11 PM if your streak is at risk.",
                    isChecked = viewModel.isStreakRiskWarningEnabled.collectAsState().value,
                    onCheckedChange = { isChecked -> viewModel.setStreakRiskWarningEnabled(isChecked) }
                )
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                // Example: AI Oracle Vibe Notification toggle
                ReminderSettingItem(
                    title = "AI Oracle Vibe Notification",
                    description = "Receive a morning notification with your AI-predicted vibe.",
                    isChecked = viewModel.isAIVibeNotificationEnabled.collectAsState().value,
                    onCheckedChange = { isChecked -> viewModel.setAIVibeNotificationEnabled(isChecked) }
                )
            }
        }
    }
}

@Composable
fun ReminderSettingItem(
    title: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

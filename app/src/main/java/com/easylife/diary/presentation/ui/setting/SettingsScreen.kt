package com.easylife.diary.presentation.ui.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.easylife.diary.R
import com.easylife.diary.core.designsystem.motion.rememberReduceMotionEnabled
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.core.designsystem.components.NavigationButton
import com.easylife.diary.core.designsystem.components.FeedbackComposable
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.presentation.ui.setting.components.NameDialog

/**
 * Created by erenalpaslan on 1.01.2023
 */
class SettingsScreen : BaseScreen<SettingsViewModel>() {
    @Composable
    override fun Screen() {
        val scrollableState = rememberScrollState()
        val reduceMotion by viewModel.reduceMotionEnabled.collectAsState()
        val motionOff = rememberReduceMotionEnabled()
        var showFeedback by remember {
            mutableStateOf(false)
        }
        var showNameDialog by remember {
            mutableStateOf(false)
        }


        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Settings")
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollableState)
                    .padding(it)
            ) {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically(initialOffsetY = { if (motionOff) 0 else it / 3 })
                ) {
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    text = "PERSONAL",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.labelMedium
                )
                NavigationButton(
                    title = "Your name",
                    icon = R.drawable.ic_user
                ) {
                    showNameDialog = true
                }
                Divider()
                NavigationButton(
                    title = "Password (PIN)",
                    icon = R.drawable.ic_lock
                ) {
                    navigator.navigate(DiaryRoutes.passwordRoute)
                }
                Divider()
                NavigationButton(
                    title = "Themes",
                    icon = R.drawable.ic_theme
                ) {
                    viewModel.onThemeButtonClicked()
                }
                Divider()
                NavigationToggleItem(
                    title = "Reduce Motion",
                    checked = reduceMotion,
                    onCheckedChange = viewModel::onReduceMotionToggled
                )
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = "MY DATA",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.labelMedium
                )
                NavigationButton(
                    title = "Backup & Restore",
                    icon = R.drawable.ic_cloud
                ) {

                }
                Divider()
                NavigationButton(
                    title = "Delete app data",
                    icon = R.drawable.ic_trash
                ) {
                    navigator.navigate(DiaryRoutes.deleteDataRoute)
                }
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = "REMINDERS",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.labelMedium
                )
                Divider()
                NavigationButton(
                    title = "Daily logging reminder",
                    icon = R.drawable.ic_notification,
                ) { navigator.navigate(DiaryRoutes.notificationSettingsRoute) }
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = "OTHER",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.labelMedium
                )
                NavigationButton(
                    title = "Share with friends",
                    icon = R.drawable.ic_share
                ) {
                    //ShareAppHelper.openShareApp(activity)
                }
                Divider()
                NavigationButton(
                    title = "Help and Feedback",
                    icon = R.drawable.ic_help
                ) {
                    showFeedback = true
                }
                Divider()
                NavigationButton(
                    title = "Remove ads",
                    icon = R.drawable.ic_remove_ad
                ) {

                }
                Divider()
                NavigationButton(
                    title = "Rate app",
                    icon = R.drawable.ic_star
                ) {

                }
                }
            }
        }

        if (showFeedback) {
            FeedbackComposable(
                onDismiss = {
                    showFeedback = false
                },
                onSendClicked = {
                    //TODO Send Button clicked
                }
            )
        }

        if (showNameDialog) {
            NameDialog(onDismiss = { showNameDialog = false }, onSaveClicked = {

            })
        }
    }
}

@Composable
private fun NavigationToggleItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(6.dp))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
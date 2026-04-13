package com.easylife.diary.feature.setup

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.easylife.diary.core.designsystem.base.BaseScreen

private const val WELCOME_MESSAGE = "Welcome to DexDiary"

class SetupWelcomeScreen : BaseScreen<SetupWizardViewModel>() {
    @Composable
    override fun Screen() {
        SetupStep(
            title = WELCOME_MESSAGE,
            body = "Let's quickly configure your diary for a safe and smooth experience.",
            actionLabel = "Start setup",
            onAction = viewModel::nextFromWelcome
        )
    }
}

class SetupStorageScreen : BaseScreen<SetupWizardViewModel>() {
    @Composable
    override fun Screen() {
        SetupStep(
            title = "Storage access",
            body = "DexDiary can export backups to files you choose. Grant access when asked.",
            actionLabel = "Continue",
            onAction = viewModel::nextFromStorage
        )
    }
}

class SetupNotificationsScreen : BaseScreen<SetupWizardViewModel>() {
    @Composable
    override fun Screen() {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) {
            viewModel.nextFromNotifications()
        }
        SetupStep(
            title = "Notification permission",
            body = "Allow reminders so streak and backup notifications can work.",
            actionLabel = "Grant and continue",
            onAction = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    viewModel.nextFromNotifications()
                }
            }
        )
    }
}

class SetupBatteryScreen : BaseScreen<SetupWizardViewModel>() {
    @Composable
    override fun Screen() {
        val context = LocalContext.current
        SetupStep(
            title = "Battery optimization (optional)",
            body = "Disable battery optimization for DexDiary to keep reminders reliable.",
            actionLabel = "Open settings and continue",
            onAction = {
                runCatching {
                    context.startActivity(Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS))
                }
                viewModel.nextFromBattery()
            }
        )
    }
}

class SetupReadyScreen : BaseScreen<SetupWizardViewModel>() {
    @Composable
    override fun Screen() {
        SetupStep(
            title = "Your diary is ready",
            body = "You're all set. You can update permissions, AI keys, and backup options later in Settings.",
            actionLabel = "Open DexDiary",
            onAction = viewModel::finishSetup
        )
    }
}

@Composable
private fun SetupStep(
    title: String,
    body: String,
    actionLabel: String,
    onAction: () -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
            Button(onClick = onAction, modifier = Modifier.fillMaxWidth()) {
                Text(actionLabel)
            }
        }
    }
}

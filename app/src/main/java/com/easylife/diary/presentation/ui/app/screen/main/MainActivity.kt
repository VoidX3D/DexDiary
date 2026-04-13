package com.easylife.diary.ui.screen.main

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Build
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.easylife.diary.core.crash.CrashAutoFixer
import com.easylife.diary.core.crash.CrashLogStore
import com.easylife.diary.core.crash.CrashReport
import com.easylife.diary.core.designsystem.motion.MotionPreferences
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import com.easylife.diary.ui.screen.main.theme.DiaryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    internal lateinit var navigator: DiaryNavigator

    @Inject
    internal lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermissionIfNeeded()
        lifecycleScope.launch {
            runCatching {
                MotionPreferences.setReduceMotion(
                    preferencesManager.getBoolean(PreferenceKeys.REDUCE_MOTION_ENABLED, false)
                )
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val crashStore = CrashLogStore(this)
        val crashAutoFixer = CrashAutoFixer(preferencesManager)
        setContent {
            var crashReport by remember {
                mutableStateOf<CrashReport?>(crashStore.read())
            }
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            val navHost = rememberNavController()
            val selectedTheme by viewModel.theme.observeAsState()

            LaunchedEffect(Unit) {
                runCatching {
                    navigator.handleNavigationCommands(navHost)
                }
            }

            DiaryTheme(selectedTheme) {
                MainDiary(
                    navController = navHost,
                    navigator = navigator
                )
                crashReport?.let { report ->
                    val fullReport = report.toPrettyString()
                    val preview = if (fullReport.length > 1200) {
                        fullReport.take(1200) + "\n\n...truncated in dialog for performance..."
                    } else {
                        fullReport
                    }
                    AlertDialog(
                        onDismissRequest = {},
                        title = { Text("Previous crash detected") },
                        text = {
                            Column {
                                Text(preview)
                                Row {
                                    TextButton(onClick = {
                                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_TEXT, fullReport)
                                        }
                                        startActivity(Intent.createChooser(shareIntent, "Share crash report"))
                                    }) { Text("Share manually") }
                                    TextButton(onClick = {
                                        lifecycleScope.launch {
                                            crashAutoFixer.autoFix(report)
                                            crashStore.clear()
                                            crashReport = null
                                        }
                                    }) { Text("Auto-fix") }
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                val clipboard = getSystemService(ClipboardManager::class.java)
                                clipboard?.setPrimaryClip(
                                    ClipData.newPlainText("DexDiary Crash", fullReport)
                                )
                            }) {
                                Text("Copy report")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                crashStore.clear()
                                crashReport = null
                            }) {
                                Text("Dismiss")
                            }
                        }
                    )
                }
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED
        ) return
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            NOTIFICATION_PERMISSION_REQUEST
        )
    }

    private companion object {
        const val NOTIFICATION_PERMISSION_REQUEST = 4101
    }
}
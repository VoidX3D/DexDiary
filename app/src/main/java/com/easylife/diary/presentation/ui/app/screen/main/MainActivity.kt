package com.easylife.diary.ui.screen.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Build
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
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
            MotionPreferences.setReduceMotion(
                preferencesManager.getBoolean(PreferenceKeys.REDUCE_MOTION_ENABLED, false)
            )
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            val navHost = rememberNavController()
            val selectedTheme by viewModel.theme.observeAsState()

            LaunchedEffect(Unit) {
                navigator.handleNavigationCommands(navHost)
            }

            DiaryTheme(selectedTheme) {
                MainDiary(
                    navController = navHost,
                    navigator = navigator
                )
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
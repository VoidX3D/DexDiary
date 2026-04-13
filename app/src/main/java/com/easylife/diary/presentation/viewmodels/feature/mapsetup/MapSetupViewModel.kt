package com.easylife.diary.feature.mapsetup

import androidx.lifecycle.viewModelScope
import com.easylife.diary.core.designsystem.base.BaseViewModel
import com.easylife.diary.core.navigation.DiaryNavigator
import com.easylife.diary.core.navigation.screen.DiaryRoutes
import com.easylife.diary.core.preferences.PreferenceKeys
import com.easylife.diary.core.preferences.PreferencesManager
import com.easylife.diary.domain.models.MapProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MapSetupUiState(
    val selectedProvider: MapProvider = MapProvider.OPEN_STREET_MAP,
    val userGoogleMapsKey: String = ""
)

@HiltViewModel
class MapSetupViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val navigator: DiaryNavigator
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MapSetupUiState())
    val uiState: StateFlow<MapSetupUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val provider = MapProvider.from(
                preferencesManager.getString(PreferenceKeys.MAP_PROVIDER)
            )
            val key = preferencesManager.getString(
                PreferenceKeys.USER_GOOGLE_MAPS_API_KEY,
                ""
            ) ?: ""
            _uiState.update {
                it.copy(selectedProvider = provider, userGoogleMapsKey = key)
            }
        }
    }

    fun onProviderSelected(provider: MapProvider) {
        _uiState.update { it.copy(selectedProvider = provider) }
    }

    fun onGoogleKeyChanged(key: String) {
        _uiState.update { it.copy(userGoogleMapsKey = key.trim()) }
    }

    fun onContinueClicked() {
        viewModelScope.launch {
            val state = _uiState.value
            val resolvedProvider = if (
                state.selectedProvider == MapProvider.GOOGLE &&
                state.userGoogleMapsKey.isBlank()
            ) {
                MapProvider.OPEN_STREET_MAP
            } else {
                state.selectedProvider
            }

            preferencesManager.setString(PreferenceKeys.MAP_PROVIDER, resolvedProvider.value)
            preferencesManager.setString(
                PreferenceKeys.USER_GOOGLE_MAPS_API_KEY,
                state.userGoogleMapsKey
            )
            preferencesManager.setBoolean(PreferenceKeys.MAPS_SETUP_COMPLETED, true)

            val isFirstEnter = preferencesManager.getBoolean(PreferenceKeys.IS_FIRST_ENTER)
            if (isFirstEnter) {
                navigator.navigate(DiaryRoutes.themeRoute) {
                    popUpTo(DiaryRoutes.mapSetupRoute) { inclusive = true }
                    launchSingleTop = true
                }
            } else {
                navigator.navigate(DiaryRoutes.diaryRoute) {
                    popUpTo(DiaryRoutes.mapSetupRoute) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }
}

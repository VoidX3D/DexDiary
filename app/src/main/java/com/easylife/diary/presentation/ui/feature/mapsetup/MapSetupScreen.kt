package com.easylife.diary.feature.mapsetup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.domain.models.MapProvider

class MapSetupScreen : BaseScreen<MapSetupViewModel>() {
    @Composable
    override fun Screen() {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Maps Setup",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "OpenStreetMap is free and works without any key.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Add Maps API key for better maps.",
                    color = MaterialTheme.colorScheme.primary
                )

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        ProviderRow(
                            title = "Use OpenStreetMap (Free)",
                            selected = uiState.selectedProvider == MapProvider.OPEN_STREET_MAP
                        ) {
                            viewModel.onProviderSelected(MapProvider.OPEN_STREET_MAP)
                        }
                        ProviderRow(
                            title = "Use Google Maps (Bring your own key)",
                            selected = uiState.selectedProvider == MapProvider.GOOGLE
                        ) {
                            viewModel.onProviderSelected(MapProvider.GOOGLE)
                        }
                    }
                }

                OutlinedTextField(
                    value = uiState.userGoogleMapsKey,
                    onValueChange = viewModel::onGoogleKeyChanged,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Google Maps API key (optional)") },
                    singleLine = true
                )

                Text(
                    text = "If no key is provided, DexDiary safely falls back to OpenStreetMap.",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = viewModel::onContinueClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue")
                }
            }
        }
    }
}

@Composable
private fun ProviderRow(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = title, modifier = Modifier.padding(start = 8.dp))
    }
}

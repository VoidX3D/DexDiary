package com.easylife.diary.presentation.ui.setting

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easylife.diary.core.designsystem.base.BaseScreen
import java.io.ByteArrayOutputStream

class BackupScreen : BaseScreen<BackupViewModel>() {
    @Composable
    override fun Screen() {
        val context = LocalContext.current
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        val exportLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument("application/octet-stream")
        ) { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult
            val payload = viewModel.consumePreparedPayload() ?: return@rememberLauncherForActivityResult
            context.contentResolver.openOutputStream(uri)?.use { stream ->
                stream.write(payload)
            }
        }

        val importLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            if (uri == null) return@rememberLauncherForActivityResult
            context.contentResolver.openInputStream(uri)?.use { input ->
                val output = ByteArrayOutputStream()
                input.copyTo(output)
                viewModel.importFromRaw(output.toByteArray())
            }
        }

        Scaffold(topBar = { TopAppBar(title = { Text("Backup & Restore") }) }) { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("Export format")
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { viewModel.onFormatChanged("json") }) { Text("JSON") }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = { viewModel.onFormatChanged("csv") }) { Text("CSV") }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = { viewModel.onFormatChanged("md") }) { Text("Markdown") }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    Text("Encrypt backup")
                    Spacer(modifier = Modifier.padding(4.dp))
                    Switch(
                        checked = state.encryptionEnabled,
                        onCheckedChange = viewModel::onEncryptionChanged
                    )
                }
                if (state.encryptionEnabled) {
                    OutlinedTextField(
                        value = state.password,
                        onValueChange = viewModel::onPasswordChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Backup password") }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.prepareExport()
                        exportLauncher.launch("dexdiary-backup.${state.selectedFormat}")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Export Backup") }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { importLauncher.launch(arrayOf("*/*")) },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Import Backup") }
                Spacer(modifier = Modifier.height(12.dp))
                Text(state.status)
            }
        }
    }
}

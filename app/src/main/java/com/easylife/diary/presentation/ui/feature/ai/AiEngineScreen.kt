package com.easylife.diary.feature.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easylife.diary.core.designsystem.base.BaseScreen
import com.easylife.diary.domain.ai.AiProvider

class AiEngineScreen : BaseScreen<AiEngineViewModel>() {
    @Composable
    override fun Screen() {
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        Scaffold(
            topBar = { TopAppBar(title = { Text("AI Engine") }) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("User-provided keys only. Nothing is sent automatically.")
                OutlinedTextField(
                    value = state.openAiKey,
                    onValueChange = viewModel::onOpenAiKeyChanged,
                    label = { Text("OpenAI API key") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.geminiKey,
                    onValueChange = viewModel::onGeminiKeyChanged,
                    label = { Text("Gemini API key") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.groqKey,
                    onValueChange = viewModel::onGroqKeyChanged,
                    label = { Text("Groq API key") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = viewModel::saveKeys, modifier = Modifier.fillMaxWidth()) {
                    Text("Save keys")
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = { viewModel.onProviderChanged(AiProvider.OPENAI) }) { Text("OpenAI") }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = { viewModel.onProviderChanged(AiProvider.GEMINI) }) { Text("Gemini") }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = { viewModel.onProviderChanged(AiProvider.GROQ) }) { Text("Groq") }
                }

                OutlinedTextField(
                    value = state.inputText,
                    onValueChange = viewModel::onInputChanged,
                    label = { Text("Entry text") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = viewModel::summarizeEntry) { Text("Summarize") }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = viewModel::detectMood) { Text("Mood") }
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = viewModel::suggestTags) { Text("Suggest tags") }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(onClick = viewModel::writingPrompt) { Text("Prompt") }
                }
                Text("Output")
                OutlinedTextField(
                    value = state.output,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 6,
                    readOnly = true
                )
            }
        }
    }
}

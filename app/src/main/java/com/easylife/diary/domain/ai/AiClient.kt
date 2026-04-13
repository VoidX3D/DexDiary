package com.easylife.diary.domain.ai

interface AiClient {
    suspend fun runPrompt(provider: AiProvider, apiKey: String, prompt: String): String
}

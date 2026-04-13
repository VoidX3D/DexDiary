package com.easylife.diary.data.ai

import com.easylife.diary.domain.ai.AiClient
import com.easylife.diary.domain.ai.AiProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HttpAiClient @Inject constructor() : AiClient {
    private val jsonType = "application/json".toMediaType()
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .callTimeout(40, TimeUnit.SECONDS)
        .build()

    override suspend fun runPrompt(provider: AiProvider, apiKey: String, prompt: String): String {
        if (apiKey.isBlank()) return "Add API key in Settings"
        return withContext(Dispatchers.IO) {
            runCatching {
                when (provider) {
                    AiProvider.OPENAI -> callOpenAi(apiKey, prompt)
                    AiProvider.GEMINI -> callGemini(apiKey, prompt)
                    AiProvider.GROQ -> callGroq(apiKey, prompt)
                }
            }.getOrElse {
                "AI request failed: ${it.message}"
            }
        }
    }

    private fun callOpenAi(apiKey: String, prompt: String): String {
        val payload = """
            {"model":"gpt-4o-mini","messages":[{"role":"user","content":${quote(prompt)}}]}
        """.trimIndent()
        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(payload.toRequestBody(jsonType))
            .build()
        return execute(request)
    }

    private fun callGemini(apiKey: String, prompt: String): String {
        val payload = """
            {"contents":[{"parts":[{"text":${quote(prompt)}}]}]}
        """.trimIndent()
        val request = Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey")
            .post(payload.toRequestBody(jsonType))
            .build()
        return execute(request)
    }

    private fun callGroq(apiKey: String, prompt: String): String {
        val payload = """
            {"model":"llama-3.1-8b-instant","messages":[{"role":"user","content":${quote(prompt)}}]}
        """.trimIndent()
        val request = Request.Builder()
            .url("https://api.groq.com/openai/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(payload.toRequestBody(jsonType))
            .build()
        return execute(request)
    }

    private fun execute(request: Request): String {
        client.newCall(request).execute().use { response ->
            val body = response.body?.string().orEmpty()
            return if (!response.isSuccessful) {
                "AI request failed (${response.code}): ${body.take(280)}"
            } else {
                body.take(1000)
            }
        }
    }

    private fun quote(value: String): String {
        return "\"" + value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n") + "\""
    }
}

package com.easylife.diary.core.crash

data class CrashReport(
    val timestamp: Long,
    val threadName: String,
    val throwableType: String,
    val message: String,
    val stackTrace: String
) {
    fun toPrettyString(): String {
        return buildString {
            appendLine("Timestamp: $timestamp")
            appendLine("Thread: $threadName")
            appendLine("Type: $throwableType")
            appendLine("Message: $message")
            appendLine()
            appendLine(stackTrace)
        }
    }
}

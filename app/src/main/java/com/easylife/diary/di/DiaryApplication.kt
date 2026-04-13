package com.easylife.diary

import android.app.Application
import com.easylife.diary.core.crash.CrashLogStore
import com.easylife.diary.core.crash.CrashReport
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by erenalpaslan on 19.12.2022
 */
@HiltAndroidApp
class DiaryApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            CrashLogStore(this).write(
                CrashReport(
                    timestamp = System.currentTimeMillis(),
                    threadName = thread.name,
                    throwableType = throwable::class.java.name,
                    message = throwable.message ?: "No message",
                    stackTrace = throwable.stackTraceToString()
                )
            )
            previous?.uncaughtException(thread, throwable)
        }
    }
}
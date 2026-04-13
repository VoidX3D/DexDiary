package com.easylife.diary.core.crash

import android.content.Context
import com.google.gson.Gson
import java.io.File

class CrashLogStore(
    private val context: Context,
    private val gson: Gson = Gson()
) {
    private val fileName = "last_crash_report.json"

    private fun file(): File = File(context.filesDir, fileName)

    fun write(report: CrashReport) {
        runCatching {
            file().writeText(gson.toJson(report))
        }
    }

    fun read(): CrashReport? {
        return runCatching {
            val target = file()
            if (!target.exists()) return null
            gson.fromJson(target.readText(), CrashReport::class.java)
        }.getOrNull()
    }

    fun clear() {
        runCatching {
            file().delete()
        }
    }
}

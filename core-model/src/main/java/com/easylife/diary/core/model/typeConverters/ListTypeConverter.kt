package com.easylife.diary.core.model.typeConverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Created by erenalpaslan on 14.04.2026
 */
@ProvidedTypeConverter
class ListTypeConverter @Inject constructor(
    private val gson: Gson
) {
    @TypeConverter
    fun toString(list: List<String>?): String? {
        if (list == null) {
            return null
        }
        val type: Type = object : TypeToken<List<String>?>(){}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun fromString(list: String?): List<String>? {
        if (list == null) {
            return null
        }
        val type: Type = object : TypeToken<List<String>?>(){}.type
        return gson.fromJson(list, type)
    }
}

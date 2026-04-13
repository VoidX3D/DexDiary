package com.easylife.diary.core.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.easylife.diary.core.model.typeConverters.DiaryDateTypeConverter
import com.easylife.diary.core.model.typeConverters.ListTypeConverter
import kotlinx.parcelize.Parcelize

/**
 * Created by erenalpaslan on 7.01.2023
 */
@Entity(tableName = "Entries")
@Parcelize
data class DiaryNote(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var moodId: Int? = null,
    var title: String? = null,
    var description: String? = null,
    @field:TypeConverters(DiaryDateTypeConverter::class)
    @Embedded val date: DiaryDate?,
    var wordCount: Int = 0,
    var tags: String? = null,
    var complexity: Double = 0.0,
    @field:TypeConverters(ListTypeConverter::class)
    var mediaPaths: List<String> = emptyList(),
    var isOracleAnalyzed: Boolean = false
): Parcelable

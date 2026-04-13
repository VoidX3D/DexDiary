package com.easylife.diary.core.data.di

import android.content.Context
import androidx.room.Room
import com.easylife.diary.core.data.room.DiaryDatabase
import com.easylife.diary.core.data.room.dao.EntryDao
import com.easylife.diary.core.model.typeConverters.DiaryDateTypeConverter
import com.easylife.diary.core.model.typeConverters.ListTypeConverter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by erenalpaslan on 9.01.2023
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideChannelDao(diaryDb: DiaryDatabase): EntryDao {
        return diaryDb.entryDao()
    }

    @Provides
    fun providesDiaryDateTypeConverter(gson: Gson): DiaryDateTypeConverter {
        return DiaryDateTypeConverter(gson)
    }

    @Provides
    fun providesListTypeConverter(gson: Gson): ListTypeConverter {
        return ListTypeConverter(gson)
    }

    @Provides
    @Singleton
    fun providesDiaryDatabase(
        @ApplicationContext appContext: Context,
        diaryDateTypeConverter: DiaryDateTypeConverter,
        listTypeConverter: ListTypeConverter
    ): DiaryDatabase {
        return runCatching {
            Room.databaseBuilder(
                appContext,
                DiaryDatabase::class.java,
                DiaryDatabase.DB_NAME
            )
                .addTypeConverter(diaryDateTypeConverter)
                .addTypeConverter(listTypeConverter)
                .setQueryExecutor(Executors.newSingleThreadExecutor())
                .setTransactionExecutor(Executors.newSingleThreadExecutor())
                .fallbackToDestructiveMigration()
                .build()
        }.getOrElse {
            Room.databaseBuilder(
                appContext,
                DiaryDatabase::class.java,
                DiaryDatabase.DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
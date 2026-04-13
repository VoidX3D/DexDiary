package com.easylife.diary.core.data.di

import com.easylife.diary.data.ai.HttpAiClient
import com.easylife.diary.domain.ai.AiClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AiModule {
    @Binds
    @Singleton
    abstract fun bindAiClient(impl: HttpAiClient): AiClient
}

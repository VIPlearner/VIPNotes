package com.viplearner.single_note.data.di

import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.feature.single_note.domain.repository.SingleNoteRepository
import com.viplearner.single_note.data.repository.SingleNoteRepositoryImpl
import com.viplearner.single_note.data.service.SingleNoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingleNoteRepositoryModule {
    @Provides
    @Singleton
    fun provideSingleNoteRepository(
        singleNoteService: SingleNoteService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): SingleNoteRepository =
        SingleNoteRepositoryImpl(singleNoteService, ioDispatcher)
}
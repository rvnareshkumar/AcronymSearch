package com.albertson.acronymsearch.framework.di

import com.albertson.acronymsearch.data.network.AcronymSearchApi
import com.albertson.acronymsearch.data.repository.AcronymSearchRepositoryImpl
import com.albertson.acronymsearch.domain.usecase.FetchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AcronymModule {

  @Provides
  @Singleton
  fun provideAcronymSearchRepository(searchApi: AcronymSearchApi): AcronymSearchRepositoryImpl {
    return AcronymSearchRepositoryImpl(searchApi, Dispatchers.IO)
  }

  @Provides
  @Singleton
  fun provideUsecase(repository: AcronymSearchRepositoryImpl) = FetchUseCase(repository, Dispatchers.IO)
}
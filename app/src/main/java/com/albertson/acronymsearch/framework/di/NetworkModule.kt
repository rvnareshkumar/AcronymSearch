package com.albertson.acronymsearch.framework.di

import com.albertson.acronymsearch.BuildConfig
import com.albertson.acronymsearch.data.network.AcronymSearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  private const val ACCEPT_LANGUAGE = "Accept-Language"

  @Singleton
  @Provides
  fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(
      if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
      else
        HttpLoggingInterceptor.Level.NONE
    )
  }

  @Singleton
  @Provides
  fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    val builder: OkHttpClient.Builder = OkHttpClient.Builder()
    return builder
      .addInterceptor(
        Interceptor { chain ->
          val builder = chain.request().newBuilder()
          builder.header(ACCEPT_LANGUAGE, Locale.getDefault().language)
          return@Interceptor chain.proceed(builder.build())
        }
      )
      .addInterceptor(loggingInterceptor)
      .build()
  }


  @Provides
  @Singleton
  fun provideApiInterface(retrofit: Retrofit): AcronymSearchApi {
    return retrofit.create(AcronymSearchApi::class.java)
  }

  @Provides
  @Singleton
  fun retrofit(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("http://www.nactem.ac.uk/software/acromine/")
      .addConverterFactory(gsonConverterFactory)
      .build()
  }

  @Provides
  @Singleton
  fun provideGsonConvertorFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
  }
}
package com.un_bd.github.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.un_bd.github.BuildConfig
import com.un_bd.github.MyApp
import com.un_bd.github.api.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModel {

  @Singleton
  @Provides
  fun provideLoggerInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  @Singleton
  @Provides
  fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    @ApplicationContext context: Context
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .apply {
        if (BuildConfig.DEBUG) {
          addInterceptor(httpLoggingInterceptor)
          addInterceptor(ChuckerInterceptor.Builder(context).build())
        }
      }
      .build()
  }

  @Singleton
  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(GitHubService.BASE_URL)
      .client(okHttpClient)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  @Singleton
  @Provides
  fun provideGitHubService(retrofit: Retrofit): GitHubService {
    return retrofit.create(GitHubService::class.java)
  }
}
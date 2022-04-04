package com.un_bd.github.net

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.un_bd.github.model.ReposModel
import com.un_bd.github.model.UserModelX
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


object ApiClient {
  private const val BASE_URL = "https://api.github.com/"

  private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      // .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  val gitHubApiService: GitHubApiService by lazy {
    retrofit.create(GitHubApiService::class.java)
  }

  private val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder()
      .addInterceptor(loggerInterceptor)
      .build()

  private val loggerInterceptor: HttpLoggingInterceptor
    get() {
      val interceptor = HttpLoggingInterceptor { message ->
        Log.d("=== OkHttpInterceptor ==>", message)
      }
      interceptor.level = HttpLoggingInterceptor.Level.BODY
      HttpLoggingInterceptor()
      return interceptor
    }

}

interface GitHubApiService {
  @GET(value = "users")
  suspend fun getUsers(@Query(value = "since") since: Int = 0): List<UserModelX>

  @GET(value = "users/{user}/repos")
  suspend fun getRepos(@Path(value = "user") User: String): List<ReposModel>
}

interface OtherApiService {

}
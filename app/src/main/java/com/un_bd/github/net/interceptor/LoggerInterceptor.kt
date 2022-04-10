package com.un_bd.github.net.interceptor

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

object LoggerInterceptor {
  val loggerInterceptor: HttpLoggingInterceptor
    get() {
      val interceptor = HttpLoggingInterceptor { message ->
        Log.d("=== OkHttpInterceptor ==>", message)
      }
      interceptor.level = HttpLoggingInterceptor.Level.BODY
      HttpLoggingInterceptor()
      return interceptor
    }
}
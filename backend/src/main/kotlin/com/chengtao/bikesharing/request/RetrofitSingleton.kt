package com.chengtao.bikesharing.request

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSingleton private constructor() {
  var retrofit: Retrofit

  private object Holder {
    const val BASE_URL = "http://api.map.baidu.com/"
    val INSTANCE = RetrofitSingleton()
  }

  companion object {
    val instance: RetrofitSingleton by lazy { Holder.INSTANCE }
  }

  init {
    val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
      println(message)
    })
    logging.level = HttpLoggingInterceptor.Level.BODY
    val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .build()
    retrofit = Retrofit.Builder()
        .baseUrl(Holder.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()
            )
        )
        .build()
  }
}
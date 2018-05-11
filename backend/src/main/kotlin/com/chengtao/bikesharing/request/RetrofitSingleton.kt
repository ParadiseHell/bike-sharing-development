package com.chengtao.bikesharing.request

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
    retrofit = Retrofit.Builder()
        .baseUrl(Holder.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }
}
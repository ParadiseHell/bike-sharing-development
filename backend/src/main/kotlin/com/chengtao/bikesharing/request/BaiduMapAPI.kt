package com.chengtao.bikesharing.request

import com.chengtao.bikesharing.model.GeoCode
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BaiduMapAPI {
  companion object {
    const val GEOCODER_API = "/geocoder/v2/"
    const val OUTPUT = "json"
    const val AK = "VlLIUSFowxeff3qcgZi55h4q0asEkKQR"
    const val SK = "aCWYa6ndtHzc9VD5Bu6Xeqsl8S4kUk7m"
  }

  @GET(GEOCODER_API)
  fun getGeoCode(
    @Query("address") address: String,
    @Query("output") output: String = OUTPUT,
    @Query("ak") ak: String = AK,
    @Query("sn") sn: String
  ): Call<GeoCode>
}
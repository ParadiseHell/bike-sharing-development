package com.chengtao.bikesharing.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * 共享单车模型
 */
data class Bike(
  var id: Int,
  var createdAt: Date,
  var updateAt: Date,
  var name: String,
  var introduction: String,
  var foundedAt: Date? = null
)

/**
 * 发展趋势模型
 */
data class Development(
  var id: Int,
  var createdAt: Date,
  var updatedAt: Date,
  var bikeId: Int,
  var city: String,
  var deliveryAt: Date,
  var deliveryCount: Int? = 0,
  var location: Location
)

/**
 * 位置
 */
data class Location(
  @SerializedName("lat")
  var latitude: Float,
  @SerializedName("lng")
  var longitude: Float
)

/**
 * 百度地图地理编码返回实体
 */
data class GeoCode(
  var status: Int,
  var location: Location,
  var precise: Int,
  var confidence: Int,
  var level: String
)

/**
 * 错误模型
 */
data class Error(val error: String)

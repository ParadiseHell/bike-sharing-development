package com.chengtao.bikesharing.model

import java.util.Date

/**
 * 共享单车模型
 */
data class Bike(
  var id: Short,
  var name: String,
  var foundedAt: Date? = null,
  var createdAt: Date,
  var updateAt: Date
)

/**
 * 发展趋势模型
 */
data class Development(
  var id: Int,
  var bikeId: Short,
  var city: String,
  var deliveryAt: Date,
  var createdAt: Date,
  var updateAt: Date
)

package com.chengtao.bikesharing.model

import java.util.Date

/**
 * 共享单车模型
 */
data class Bike(
  var id: Int,
  var createdAt: Date,
  var updateAt: Date,
  var name: String,
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
  var deliveryCount: Int? = 0
)

/**
 * 错误模型
 */
data class Error(val error: String)

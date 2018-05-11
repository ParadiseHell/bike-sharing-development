package com.chengtao.bikesharing.controller

import com.chengtao.bikesharing.Utils
import com.chengtao.bikesharing.database.sql.BikeSQL
import com.chengtao.bikesharing.database.sql.DevelopmentSQL
import com.chengtao.bikesharing.extension.missingParameter
import com.chengtao.bikesharing.extension.parameterInvalid
import com.chengtao.bikesharing.model.Error
import com.chengtao.bikesharing.request.BaiduMapAPI
import com.chengtao.bikesharing.request.RetrofitSingleton
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DevelopmentController {
  private class Parameter {
    companion object {
      const val bikeId = "bike_id"
      const val city = "city"
      const val deliveryAt = "delivery_at"
      const val deliveryCount = "delivery_count"
    }
  }

  @PostMapping("/developments")
  fun insetDevelopment(
    @RequestParam(name = Parameter.bikeId) bikeId: Int?,
    @RequestParam(name = Parameter.city) city: String?,
    @RequestParam(name = Parameter.deliveryAt) deliveryAt: String?,
    @RequestParam(name = Parameter.deliveryCount) deliveryCount: Int?
  ): Any? {
    return when {
      bikeId == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error(Parameter.bikeId.missingParameter()))
      city == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error(Parameter.city.missingParameter()))
      deliveryAt == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error(Parameter.deliveryAt.missingParameter()))
      deliveryCount == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error(Parameter.deliveryCount.missingParameter()))
      deliveryCount <= 0 ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error(Parameter.deliveryCount.parameterInvalid()))
      BikeSQL.queryBikeById(bikeId) == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("no such ${Parameter.bikeId}"))
      DevelopmentSQL.isExit(bikeId, city) ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("${Parameter.bikeId} and ${Parameter.city} has been used"))
      else -> {
        val date = Utils.rfc3339ToDate(deliveryAt)
        if (date == null) {
          ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(Error(Parameter.deliveryAt.parameterInvalid()))
        } else {
          //通过百度 API 获取城市的经纬度
          val sn = Utils.generateSn(BaiduMapAPI.GEOCODER_API, city) ?: return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Error(HttpStatus.INTERNAL_SERVER_ERROR.name))
          //
          val baiduMapAPI = RetrofitSingleton
              .instance
              .retrofit
              .create(BaiduMapAPI::class.java)
          val geoCodeCall = baiduMapAPI.getGeoCode(address = city, sn = sn)
          val geoCode = geoCodeCall.execute().body() ?: return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Error(HttpStatus.INTERNAL_SERVER_ERROR.name))
          //
          return DevelopmentSQL.insertDevelopment(
              bikeId = bikeId,
              city = city,
              deliveryAt = date,
              deliveryCount = deliveryCount,
              locationLatitude = geoCode.location.latitude,
              locationLongitude = geoCode.location.longitude
          )
        }
      }
    }
  }

  @GetMapping("/developments")
  fun getBikeDevelopments(@RequestParam(value = Parameter.bikeId) bikeId: Int): Any {
    return if (BikeSQL.queryBikeById(bikeId) == null) {
      ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(Error("${Parameter.bikeId}($bikeId) not exist"))
    } else {
      DevelopmentSQL.queryDevelopmentsByBikeId(bikeId)
    }
  }
}
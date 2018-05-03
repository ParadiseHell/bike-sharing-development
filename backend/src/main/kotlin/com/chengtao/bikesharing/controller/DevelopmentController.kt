package com.chengtao.bikesharing.controller

import com.chengtao.bikesharing.Utils
import com.chengtao.bikesharing.database.sql.BikeSQL
import com.chengtao.bikesharing.database.sql.DevelopmentSQL
import com.chengtao.bikesharing.model.Error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DevelopmentController {

  @PostMapping("/developments")
  fun insetDevelopment(
    @RequestParam(name = "bike_id") bikeId: Int?,
    @RequestParam(name = "city") city: String?,
    @RequestParam(name = "delivery_at") deliveryAt: String?,
    @RequestParam(name = "delivery_count") deliveryCount: Int?
  ): Any? {
    return when {
      bikeId == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("missing parameter : bike_id"))
      city == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("missing parameter : city"))
      deliveryAt == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("missing parameter : delivery_at"))
      deliveryCount != null && deliveryCount <= 0 ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("parameter invalid : delivery_count"))
      BikeSQL.queryBikeById(bikeId) == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("no such bike_id"))
      DevelopmentSQL.isExit(bikeId, city) ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("bike_id and city has been used"))
      else -> {
        val date = Utils.rfc3999ToDate(deliveryAt)
        if (date == null) {
          ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(Error("parameter invalid : delivery_at"))
        } else {
          DevelopmentSQL.insertDevelopment(bikeId, city, date, deliveryCount)
        }
      }
    }
  }

  @GetMapping("/developments")
  fun getBikeDevelopments(@RequestParam(value = "bike_id") bikeId: Int): Any {
    return if (BikeSQL.queryBikeById(bikeId) == null) {
      ResponseEntity.status(HttpStatus.NOT_FOUND).body(Error("bike_id($bikeId) not exist"))
    } else {
      DevelopmentSQL.queryDevelopmentsByBikeId(bikeId)
    }
  }
}
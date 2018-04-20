package com.chengtao.bikesharing.controller

import com.chengtao.bikesharing.Utils
import com.chengtao.bikesharing.database.sql.BikeSQL
import com.chengtao.bikesharing.database.sql.DevelopmentSQL
import com.chengtao.bikesharing.model.APIError
import com.chengtao.bikesharing.model.Development
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class DevelopmentController {

  @PostMapping("developments")
  fun insetDevelopment(
    @RequestParam(name = "bike_id") bikeId: Int?,
    @RequestParam(name = "city") city: String?,
    @RequestParam(name = "delivery_at") deliveryAt: String?
  ): Any? {
    return when {
      bikeId == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(APIError("missing parameter : bike_id"))
      city == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(APIError("missing parameter : city"))
      deliveryAt == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(APIError("missing parameter : delivery_at"))
      BikeSQL.queryBikeById(bikeId) == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(APIError("no such bike_id"))
      DevelopmentSQL.isExit(bikeId, city) ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(APIError("bike_id and city has been used"))
      else -> {
        val date = Utils.rfc3999ToDate(deliveryAt)
        if (date == null) {
          ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(APIError("parameter invalid : delivery_at"))
        } else {
          DevelopmentSQL.insertDevelopment(bikeId, city, date)
        }
      }
    }
  }

  @GetMapping("/developments/bike_id/{bike_id}")
  fun getBikeDevelopments(@PathVariable(name = "bike_id") bikeId: Int): List<Development> {
    return DevelopmentSQL.queryDevelopmentsByBikeId(bikeId)
  }
}
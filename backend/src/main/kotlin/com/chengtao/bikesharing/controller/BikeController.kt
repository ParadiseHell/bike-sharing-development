package com.chengtao.bikesharing.controller

import com.chengtao.bikesharing.Utils
import com.chengtao.bikesharing.database.sql.BikeSQL
import com.chengtao.bikesharing.model.Bike
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BikeController {
  /**
   * 获取所有共享单车
   */
  @GetMapping("/bikes")
  fun getBikes(): List<Bike> {
    return BikeSQL.queryAllBikes()
  }

  /**
   * 通过id获取单车
   */
  @GetMapping("/bikes/{id}")
  fun getBikeById(
    @PathVariable(name = "id") id: Int
  ): Any {
    return BikeSQL.queryBikeById(id) ?: ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Error("not found"))
  }

  /**
   * 插入单车
   */
  @PostMapping("bikes")
  fun insertBike(
    @RequestParam(name = "name") name: String?,
    @RequestParam(name = "founded_at") foundedAt: String?
  ): Any {
    return when {
      name == null ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("missing parameter : name"))
      BikeSQL.isBikeNameExist(name) ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("name has been used"))
      else -> BikeSQL.insertBike(name, Utils.rfc3999ToDate(foundedAt))
    }
  }
}
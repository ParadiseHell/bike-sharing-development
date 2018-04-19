package com.chengtao.bikesharing.controller

import com.chengtao.bikesharing.database.sql.BikeSQL
import com.chengtao.bikesharing.model.Bike
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BikeController {
  /**
   * 获取所有共享单车
   */
  @GetMapping("/bikes")
  fun getBikes(): List<Bike> {
    return BikeSQL.getBikes()
  }

  @GetMapping("/bikes/{id}")
  fun getBikeById(@PathVariable(name = "id") id: Int): Any? {
    return BikeSQL.getBikeById(id)
  }
}
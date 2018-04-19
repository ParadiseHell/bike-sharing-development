package com.chengtao.bikesharing.controller

import com.chengtao.bikesharing.model.Bike
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Date

@RestController
class BikeController {
  @GetMapping("/bikes")
  fun getBikes(): List<Bike> {
    val bikeList = ArrayList<Bike>()
    val bike = Bike(id = 1, name = "ofo", createdAt = Date(), updateAt = Date())
    bikeList.add(bike)
    return bikeList
  }
}
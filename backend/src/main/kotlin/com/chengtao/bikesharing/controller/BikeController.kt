package com.chengtao.bikesharing.controller

import com.chengtao.bikesharing.Utils
import com.chengtao.bikesharing.database.sql.BikeSQL
import com.chengtao.bikesharing.extension.missingParameter
import com.chengtao.bikesharing.extension.parameterInvalid
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
  private class Parameter {
    companion object {
      const val name = "name"
      const val introduction = "introduction"
      const val foundedAt = "founded_at"
    }
  }

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
    @RequestParam(name = Parameter.name) name: String?,
    @RequestParam(name = Parameter.introduction) introduction: String?,
    @RequestParam(name = Parameter.foundedAt) foundedAt: String?
  ): Any {
    return when {
      (name == null || name == "") ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error(Parameter.name.missingParameter()))
      BikeSQL.isBikeNameExist(name) ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error("${Parameter.name} has been used"))
      (introduction == null || introduction == "") ->
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Error(Parameter.introduction.missingParameter()))
      else -> {
        val date = Utils.rfc3339ToDate(foundedAt);
        if (foundedAt != null && date == null) {
          ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(Error(Parameter.foundedAt.parameterInvalid()))
        } else {
          BikeSQL.insertBike(name, introduction, date)
        }
      }
    }
  }
}
package com.chengtao.bikesharing.database.sql

import com.chengtao.bikesharing.database.table.DevelopmentTable
import com.chengtao.bikesharing.model.Development
import com.chengtao.bikesharing.model.Location
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.math.BigDecimal
import java.util.Date

object DevelopmentSQL : BaseSQL() {

  /**
   * 插入发展
   */
  fun insertDevelopment(
    bikeId: Int,
    city: String,
    deliveryAt: Date,
    deliveryCount: Int?,
    locationLatitude: Float,
    locationLongitude: Float
  ): Development {
    connectDataBase()
    var id: Int? = -1
    transaction {
      id = DevelopmentTable
          .insert {
            it[this.bikeId] = bikeId
            it[this.city] = city
            it[this.deliveryAt] = DateTime(deliveryAt)
            it[this.deliveryCount] = deliveryCount
            it[this.locationLatitude] = BigDecimal(locationLatitude.toDouble())
            it[this.locationLongitude] = BigDecimal(locationLongitude.toDouble())
          } get DevelopmentTable.id
    }
    return queryDevelopmentById(id!!)!!
  }

  /**
   * 判断是否存在此记录
   */
  fun isExit(bikeId: Int, city: String): Boolean {
    connectDataBase()
    var count = -1
    transaction {
      count = DevelopmentTable
          .select {
            (DevelopmentTable.bikeId eq bikeId) and (DevelopmentTable.city eq city)
          }
          .count()
    }
    return count > 0
  }

  /**
   * 通过id查询发展
   */
  private fun queryDevelopmentById(id: Int): Development? {
    var development: Development? = null
    connectDataBase()
    transaction {
      DevelopmentTable
          .select {
            DevelopmentTable.id eq id
          }
          .forEach {
            development =
                Development(
                    id = it[DevelopmentTable.id],
                    createdAt = it[DevelopmentTable.createdAt].toDate(),
                    updatedAt = it[DevelopmentTable.updatedAt].toDate(),
                    bikeId = it[DevelopmentTable.bikeId],
                    city = it[DevelopmentTable.city],
                    deliveryAt = it[DevelopmentTable.deliveryAt].toDate(),
                    deliveryCount = it[DevelopmentTable.deliveryCount],
                    location = Location(
                        latitude = it[DevelopmentTable.locationLatitude].toFloat(),
                        longitude = it[DevelopmentTable.locationLongitude].toFloat()
                    )
                )
          }
    }
    return development
  }

  /**
   * 通过bike_id查询发展
   */
  fun queryDevelopmentsByBikeId(bikeId: Int): List<Development> {
    val developmentList = ArrayList<Development>()
    connectDataBase()
    transaction {
      DevelopmentTable
          .select {
            DevelopmentTable.bikeId eq bikeId
          }
          .orderBy(
              column = DevelopmentTable.deliveryAt,
              isAsc = true
          )
          .forEach {
            developmentList.add(
                Development(
                    id = it[DevelopmentTable.id],
                    createdAt = it[DevelopmentTable.createdAt].toDate(),
                    updatedAt = it[DevelopmentTable.updatedAt].toDate(),
                    bikeId = it[DevelopmentTable.bikeId],
                    city = it[DevelopmentTable.city],
                    deliveryAt = it[DevelopmentTable.deliveryAt].toDate(),
                    deliveryCount = it[DevelopmentTable.deliveryCount],
                    location = Location(
                        latitude = it[DevelopmentTable.locationLatitude].toFloat(),
                        longitude = it[DevelopmentTable.locationLongitude].toFloat()
                    )
                )
            )
          }
    }
    return developmentList
  }

  /**
   * 获取所有的发展数据
   */
  fun queryAllDevelopments(): List<Development> {
    val developmentList = ArrayList<Development>()
    connectDataBase()
    transaction {
      DevelopmentTable
          .selectAll()
          .forEach {
            developmentList.add(
                Development(
                    id = it[DevelopmentTable.id],
                    createdAt = it[DevelopmentTable.createdAt].toDate(),
                    updatedAt = it[DevelopmentTable.updatedAt].toDate(),
                    bikeId = it[DevelopmentTable.bikeId],
                    city = it[DevelopmentTable.city],
                    deliveryAt = it[DevelopmentTable.deliveryAt].toDate(),
                    deliveryCount = it[DevelopmentTable.deliveryCount],
                    location = Location(
                        latitude = it[DevelopmentTable.locationLatitude].toFloat(),
                        longitude = it[DevelopmentTable.locationLongitude].toFloat()
                    )
                )
            )
          }
    }
    return developmentList
  }
}
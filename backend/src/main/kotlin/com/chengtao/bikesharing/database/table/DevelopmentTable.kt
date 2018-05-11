package com.chengtao.bikesharing.database.table

import org.jetbrains.exposed.sql.Table

object DevelopmentTable : Table("development") {
  val id = integer("id").autoIncrement().primaryKey()
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  val bikeId = (integer("bike_id") references BikeTable.id)
  val city = varchar("city", 10)
  val deliveryAt = datetime("delivery_at")
  val deliveryCount = integer("delivery_count").nullable()
  val locationLatitude = decimal("location_latitude", 9, 6)
  val locationLongitude = decimal("location_longitude", 9, 6)
}
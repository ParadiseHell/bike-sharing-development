package com.chengtao.bikesharing.database.table

import org.jetbrains.exposed.sql.Table

object DevelopmentTable : Table("development") {
  val id = integer("id").autoIncrement().primaryKey()
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  val bikeId = (integer("bike_id") references BikeTable.id)
  val city = varchar("city", 10)
  val deliveryAt = date("delivery_at")
}
package com.chengtao.bikesharing.database.table

import org.jetbrains.exposed.sql.Table

object BikeTable : Table(name = "bike") {
  val id = integer("id").autoIncrement().primaryKey()
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")

  val name = varchar("name", 20)
  val foundedAt = datetime("founded_at").nullable()
}

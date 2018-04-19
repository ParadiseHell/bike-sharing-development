package com.chengtao.bikesharing.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object BikeTable : Table(name = "bike") {
  val id = integer("id").autoIncrement().primaryKey()
  val name = varchar("name", 20)
  val foundedAt = datetime("founded_at").nullable()
  val createdAt = datetime("created_at")
  val updatedAt = datetime("updated_at")
}

fun main(args: Array<String>) {
  Database.connect(
      url = "jdbc:mysql://127.0.0.1:3306/bike_sharing?useUnicode=true&characterEncoding=UTF-8",
      driver = "com.mysql.cj.jdbc.Driver",
      user = "root",
      password = "123456"
  )
  transaction {
    BikeTable.insert {
      it[name] = "ofo"
    }
  }
}
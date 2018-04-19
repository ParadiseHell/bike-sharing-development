package com.chengtao.bikesharing.database.sql

import com.chengtao.bikesharing.database.table.BikeTable
import com.chengtao.bikesharing.model.Bike
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.Date

object BikeSQL : BaseSQL() {
  fun insertBike(bikeName: String, bikeFoundedAt: Date? = null) {
    connectDataBase()
    transaction {
      BikeTable.insert {
        it[name] = bikeName
        if (bikeFoundedAt != null) {
          it[foundedAt] = DateTime(bikeFoundedAt)
        }
      }
    }
  }

  fun getBikes(): ArrayList<Bike> {
    val bikeList = ArrayList<Bike>()
    connectDataBase()
    transaction {
      BikeTable.selectAll().forEach {
        bikeList.add(
            Bike(
                id = it[BikeTable.id],
                createdAt = it[BikeTable.createdAt].toDate(),
                updateAt = it[BikeTable.updatedAt].toDate(),
                name = it[BikeTable.name],
                foundedAt = it[BikeTable.foundedAt]?.toDate()
            )
        )
      }
    }
    return bikeList
  }

  /**
   * 通过id获取单车信息
   */
  fun getBikeById(id: Int): Bike? {
    connectDataBase()
    var bike: Bike? = null
    transaction {
      BikeTable.select {
        BikeTable.id eq id
      }.forEach {
        bike = Bike(
            id = it[BikeTable.id],
            createdAt = it[BikeTable.createdAt].toDate(),
            updateAt = it[BikeTable.updatedAt].toDate(),
            name = it[BikeTable.name],
            foundedAt = it[BikeTable.foundedAt]?.toDate()
        )
      }
    }
    return bike
  }
}
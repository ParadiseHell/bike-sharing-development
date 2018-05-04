package com.chengtao.bikesharing.database.sql

import org.jetbrains.exposed.sql.Database

open class BaseSQL {
  fun connectDataBase() {
    Database.connect(
        url = "jdbc:mysql://127.0.0.1:3306/bike_sharing?useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
        password = "123456"
    )
  }
}
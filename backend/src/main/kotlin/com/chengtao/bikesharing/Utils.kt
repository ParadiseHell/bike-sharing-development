package com.chengtao.bikesharing

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

object Utils {
  fun rfc3999ToDate(date: String?): Date? {
    return if (date == null) {
      null
    } else
      try {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        dateFormat.parse(date)
      } catch (e: ParseException) {
        println("e : ${e.message}")
        null
      }
  }
}
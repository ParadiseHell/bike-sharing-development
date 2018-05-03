package com.chengtao.bikesharing

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

object Utils {
  /**
   * 字符串转换成时间
   * 格式 yyyy-MM-dd
   */
  fun rfc3339ToDate(date: String?): Date? {
    return if (date == null || date == "") {
      null
    } else
      try {
        val dateFormat = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssZ")
        dateFormat.parse(date)
      } catch (e: ParseException) {
        println("e : ${e.message}")
        null
      }
  }
}
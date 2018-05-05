package com.chengtao.bikesharing

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

object Utils {
  /**
   * 字符串转换成时间
   * 格式 yyyy-MM-dd'T'HH:mm:ss'Z'(UTC)
   */
  fun rfc3339ToDate(date: String?): Date? {
    return if (date == null || date == "") {
      null
    } else
      try {
        val dateFormat = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss'Z'")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        println("timeZone : " + dateFormat.timeZone.displayName)
        dateFormat.parse(date)
      } catch (e: ParseException) {
        null
      }
  }
}
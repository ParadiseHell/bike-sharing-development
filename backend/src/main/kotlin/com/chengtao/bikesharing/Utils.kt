package com.chengtao.bikesharing

import com.chengtao.bikesharing.request.BaiduMapAPI
import java.lang.StringBuilder
import java.net.URLEncoder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

object Utils {
  /**
   * 字符串转换成时间
   * 格式 yyyy-MM-dd'T'HH:mm:ssZ
   */
  fun rfc3339ToDate(date: String?): Date? {
    return if (date == null || date == "") {
      null
    } else
      try {
        val dateFormat = SimpleDateFormat("yyy-MM-dd'T'HH:mm:ssZ")
        dateFormat.parse(date)
      } catch (e: ParseException) {
        null
      }
  }

  fun generateSn(path: String, address: String): String? {
    val builder = StringBuilder()
    //
    val parametersMap = LinkedHashMap<String, String>()
    parametersMap["address"] = address
    parametersMap["output"] = BaiduMapAPI.OUTPUT
    parametersMap["ak"] = BaiduMapAPI.AK
    //
    for (entry in parametersMap.entries) {
      println("key : " + entry.key + " - value : " + entry.value)
      builder.append(entry.key + "=")
      builder.append(URLEncoder.encode(entry.value, "UTF-8") + "&")
    }
    if (builder.isNotEmpty()) {
      builder.deleteCharAt(builder.length - 1)
    }
    val parametersString = builder.toString()
    println("parametersString : $parametersString ")
    if (parametersString.isEmpty()) {
      return null
    }
    var wholeString = "$path/?$parametersString${BaiduMapAPI.SK}"
    println("wholeString : $wholeString")
    wholeString = URLEncoder.encode(wholeString, "UTF-8")
    println("wholeString : $wholeString")
    return md5(wholeString)
  }

  private fun md5(string: String): String? {
    try {
      val md = java.security.MessageDigest.getInstance("MD5");
      val array = md.digest(string.toByteArray())
      val sb = StringBuffer();
      for (a in array) {
        sb.append(Integer.toHexString((a.toInt() and 0xFF) or 0x100).substring(1, 3))
      }
      return sb.toString();
    } catch (e: java.security.NoSuchAlgorithmException) {
      //do nothing
    }
    return null
  }
}
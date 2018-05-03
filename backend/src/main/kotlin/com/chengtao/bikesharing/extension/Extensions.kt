package com.chengtao.bikesharing.extension

fun String.parameterInvalid(): String {
  return "parameter invalid : " + this
}

fun String.missingParameter(): String {
  return "missing parameter : " + this
}

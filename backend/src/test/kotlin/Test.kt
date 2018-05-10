import com.chengtao.bikesharing.Utils

fun main(args: Array<String>) {
  test()
}

fun test() {
  val parametersMap = LinkedHashMap<String, String>()
  parametersMap["address"] = "北京"
  parametersMap["output"] = "json"
  parametersMap["ak"] = "VlLIUSFowxeff3qcgZi55h4q0asEkKQR"
  val sn = Utils.generateSn(parametersMap)
  println(sn)
}

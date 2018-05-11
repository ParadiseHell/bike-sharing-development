import com.chengtao.bikesharing.Utils
import com.chengtao.bikesharing.request.BaiduMapAPI

fun main(args: Array<String>) {
  test()
}

fun test() {
  val sn = Utils.generateSn(BaiduMapAPI.GEOCODER_API, "上海")
  println(sn)
}

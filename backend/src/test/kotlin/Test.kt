import java.text.SimpleDateFormat
import java.util.Calendar

fun main(args: Array<String>) {
  dateTest()
}

fun dateTest() {
  val dateString = "2016-08-01T00:00:00+0800";
  val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  val date = dateFormat.parse(dateString)
  val calendar = Calendar.getInstance()
  calendar.time = date
  println("date : $dateString time-zone : ${calendar.timeZone}")
}

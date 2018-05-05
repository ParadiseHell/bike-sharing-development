import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun main(args: Array<String>) {
  dateTest()
}

fun dateTest() {
  val date = Date()
  val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
  dateFormat.timeZone = TimeZone.getTimeZone("UTC")
  val dateString = dateFormat.format(date);
  println("date : $dateString")
}

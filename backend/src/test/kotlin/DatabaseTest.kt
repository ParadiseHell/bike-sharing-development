import com.chengtao.bikesharing.database.table.BikeTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
  Database.connect(
      url = "jdbc:mysql://127.0.0.1:3306/bike_sharing?useUnicode=true&characterEncoding=UTF-8",
      driver = "com.mysql.cj.jdbc.Driver",
      user = "root",
      password = "123456"
  )
  transaction {
    BikeTable.insert {
      it[name] = "ofo"
    }
  }
}

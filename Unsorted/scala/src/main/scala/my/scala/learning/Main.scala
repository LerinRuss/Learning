package my.scala.learning


case class Employee(name: String, office: String, role: String)

object Main {

  def main(args: Array[String]): Unit = {
    val fred = Employee("Fred", "Anchorage", "Salesman")

    val res = Map("1s" -> 1, "2s" -> 2)

    println(res.updated("2s", 3))
  }
}

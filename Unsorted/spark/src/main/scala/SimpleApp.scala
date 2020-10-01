import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object SimpleApp {
  val test_base_path = "hdfs://gandalf-nn.placeiq.net/tmp90/vharsekin/data/dap"


  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = SparkSession.builder
      .appName("Spark new name")
      .master("local[2]")
      .getOrCreate()

    Test.execute()

    spark.stop()
  }

  def read_count()(implicit spark: SparkSession): Unit = {
    val fact_visit_dt = "20200112"
    val device_snapshot_dt = "20200116"

    val fact_visit = spark.read.parquet(s"hdfs://gandalf-nn.placeiq.net/data/dap/landmark/v4_1/fact_visit/partner=ADR/inflation=${fact_visit_dt}_temporal")

    val all_fact_visit_count = fact_visit.count()

    println(s"all fact visit  count: $all_fact_visit_count")
  }

  def writeBigParquet()(implicit spark: SparkSession): Unit = {

  }

  def writeTestDeviceSnapshot()(implicit spark: SparkSession): Unit = {
    import spark.implicits._
    import java.sql.Timestamp
    import org.apache.spark.sql.SaveMode

    val dt = 20200103

    val test_device = List(
      (111111, Timestamp.valueOf("2012-12-12 00:00:00"))
    ).toDF("device_key", "last_seen")

    test_device.write.mode(SaveMode.Overwrite).parquet(s"$test_base_path/device/dim_device_snapshot/dt=$dt/")
  }

  def checkUniqueDeviceKeys()(implicit spark: SparkSession): Unit = {
    val fact_visit_dt = ""
    val device_snapshot_dt = ""

    val fact_visit = spark.read.parquet("")
    val device_snapshot = spark.read.parquet("")

    val all_fact_visit_count = fact_visit.count()
    val all_device_snapshot_count = device_snapshot.count()

    val unique_fact_visit_count = fact_visit.select("device_key").distinct().count()
    val unique_device_snapshot = device_snapshot.select("device_key").distinct().count()

    val fact_visit_discrepancy = all_fact_visit_count - unique_fact_visit_count
    val device_snapshot_discrepancy = all_device_snapshot_count - unique_device_snapshot

    if (fact_visit_discrepancy == 0 || device_snapshot_discrepancy == 0) {
      throw new RuntimeException(s"Discrepancy Fail. Fact visit discrepancy is $fact_visit_discrepancy and " +
        s"device snapshot discrepancy is $device_snapshot_discrepancy")
    }

    println(s"Discrepancy Right. Fact visit discrepancy is $fact_visit_discrepancy and " +
      s"device snapshot discrepancy is $device_snapshot_discrepancy")
  }

  def readTestDeviceSnapshot()(implicit spark: SparkSession): Unit = {
    val test_device = spark.read.parquet(s"$test_base_path/device/dim_device_snapshot/dt=20121212/")

    test_device.printSchema()
    test_device.show()
  }

  def orta2127()(implicit spark: SparkSession): Unit = {
    import spark.implicits._
    import org.apache.spark.sql.functions.lit


    val fact_visit_dt = "20200110"
    val device_snapshot_dt = "20200115"

    val fact_visit = spark.read.parquet(s"hdfs://gandalf-nn.placeiq.net/data/dap/landmark/v4_1/fact_visit/partner=ADR/inflation=${fact_visit_dt}_temporal/dt=$fact_visit_dt")
    val device_snapshot = spark.read.parquet(s"hdfs://gandalf-nn.placeiq.net/data/dap/device/dim_device_snapshot/dt=$device_snapshot_dt")

    printSchema("fact visit", fact_visit)
    printSchema("device snapshot", device_snapshot)

    val joint = fact_visit.join(device_snapshot, "device_key")

    printSchema("Joint", joint)

    println(joint.filter($"last_seen" < lit(fact_visit_dt)).first) // Must be empty
  }

  def printSchema(name: String, parquet: DataFrame): Unit = {
    println(s"$name:")
    parquet.printSchema()
  }
}

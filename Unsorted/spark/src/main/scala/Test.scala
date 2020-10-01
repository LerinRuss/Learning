import org.apache.spark.sql.{DataFrame, SparkSession}

object Test {

  def execute()(implicit spark: SparkSession): Unit = {
    import spark.implicits._
    import org.apache.spark.sql.functions._

//    val categories_whitelist = spark.read.option("header", true).csv("hdfs://gandalf-nn.placeiq.net/user/cwilcomb/data/basemap/enhanced_visits/categories/sic8_superset")
//    val dim_category = spark.read.parquet("hdfs://gandalf-nn.placeiq.net/data/dap/landmark/v4_1/dim_category/dt=20200725")
//
//    val joint = categories_whitelist.join(dim_category, $"SIC8" === $"category_id")
//      .withColumn("category_type", lit("SIC"))
//      .select($"category_id", $"description", $"category_type")
//    val count = joint.count()

    val parq = spark.read.parquet("hdfs://gandalf-nn.placeiq.net/data/dap/landmark/v4_1/dim_chain/dt=20200906")

    parq.printSchema
    parq.show( false)

    storeCsv(parq, "/home/vkharsekin/workstop/Projects/Placeiq/test/chain")
  }

  def storeCsv(parq: DataFrame, path: String): Unit = {
    parq.write
      .option("header", true)
      .option("delimiter", ",")
      .option("nullValue", "")
      .csv(path)
  }

  def printScript(parq: DataFrame): Unit = {
    parq.foreach(row => println(s"""INSERT INTO visitation_stream_v2.category ("category_id", "description", "category_type") """ +
      s"""VALUES ('${row.getAs[String]("category_id")}', '${row.getAs[String]("description")}', '${row.getAs[String]("category_type")}');"""))
  }
}

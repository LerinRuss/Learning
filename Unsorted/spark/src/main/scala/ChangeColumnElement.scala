import org.apache.spark.sql.{Row, SparkSession}

object ChangeColumnElement {

  def execute()(implicit spark: SparkSession): Unit = {
    import spark.implicits._
    import org.apache.spark.sql.functions._

    val test = Seq(
      ("1", Map[String, BigDecimal]("observation_count" -> 1.000000000000000000, "sigma" -> 21.000000000000000000)),
      ("2", Map[String, BigDecimal]("observation_count" -> 0, "sigma" -> 22.000000000000000000)),
      ("3", Map[String, BigDecimal]("observation_count" -> 3.000000000000000000, "sigma" -> 23.000000000000000000))
    ).toDF("name", "metrics")

    test.printSchema
    test.show(false)

    def mergeUdf = udf((map1: Map[String, BigDecimal]) => map1.updated("observation_count", BigDecimal(1)))

    val res = test
      .withColumnRenamed("metrics", "old_metrics")
      .withColumn(
      "metrics",
        when(
          $"old_metrics".getItem("observation_count") > 0,
          $"old_metrics")
        .otherwise(mergeUdf($"old_metrics"))

    ).select(test.columns.map(col).toList: _*)

    res.printSchema
    res.show(false)
  }
}

package c4.w4

import org.apache.spark.sql.{DataFrame, SparkSession}

object Dataframe {

  val spark: SparkSession =
    SparkSession
      .builder()
      .appName("Time Usage")
      .config("spark.master", "local")
      .getOrCreate()

  case class Abo(id: Int, v: (String, String))
  case class Loc(id: Int, v: String)

  val as = List(
    Abo(101, ("Ruetli", "AG")),
    Abo(102, ("Brelaz", "DemiTarif")),
    Abo(103, ("Gress", "DemiTarifVisa")),
    Abo(104, ("Schatten", "DemiTarif"))
  )

  val abosDF: DataFrame = spark.createDataFrame(as)

  val ls = List(
    Loc(101, "Bern"),
    Loc(101, "Thun"),
    Loc(102, "Lausanne"),
    Loc(102, "Geneve"),
    Loc(102, "Nyon"),
    Loc(103, "Zurich"),
    Loc(103, "St-Gallen"),
    Loc(103, "Chur")
  )
  val locationsDF: DataFrame = spark.createDataFrame(ls)

  val trackedCustomersDF: DataFrame = abosDF.join(locationsDF, abosDF("id") === locationsDF("id"))


  def main(args: Array[String]): Unit = {
    trackedCustomersDF.show()
  }

}
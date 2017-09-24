package c4.w1

import org.apache.spark.{RangePartitioner, SparkConf, SparkContext, rdd}
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

class Shuffle {

  case class CFFPurchase(customerId: Int, destination: String, price: Double)

  val conf: SparkConf = new SparkConf().setMaster("local").setAppName("app")
  val sc: SparkContext = SparkContext.getOrCreate(conf)


  val l: List[CFFPurchase] = CFFPurchase(1, "London", 30D) :: CFFPurchase(1, "Paris", 20D) :: CFFPurchase(1, "Amsterdam", 30D) :: Nil
  val purchasesRdd: rdd.RDD[CFFPurchase] = sc.parallelize(l)

  private val pairs: rdd.RDD[(Int, (Int, Double))] = purchasesRdd.map(p => (p.customerId, (1, p.price)))


  pairs.partitionBy(new RangePartitioner(3, pairs)).persist()

  // This will shuffle a large sized collection of pairs on the nodes
  val purchasesPerMonth = pairs
    .groupByKey() // shuffle with grouping
    .map(p => (p._1, (p._2.size, p._2.sum)))
    .count()

  val purchasesPerMonthBetter = pairs
    .reduceByKey( (p1, p2) => (p1._1 + p2._1, p1._2 + p2._2)) // group, reduce then shuffle
    .count()
}

package c1.w6

class ScalarProduct {

  def scalarProductWithMap1(xs: List[Double], ys: List[Double]): Double = {
    ((xs zip ys) map (xy => xy._1 * xy._2)).sum
  }

  def scalarProductWithMap2(xs: List[Double], ys: List[Double]): Double = {
    def tupleToDouble(pair: (Double, Double)): Double = pair match {
      case (x, y) => x * y
    }

    (xs zip ys map tupleToDouble).sum
  }

  def scalarProductWithMap3(xs: List[Double], ys: List[Double]): Double = {
    (xs zip ys map { case (x, y) => x * y }).sum
  }

  def scalarProductWithFor(xs: List[Double], ys: List[Double]): Double = {
    (for ((x, y) <- xs zip ys) yield x * y).sum
  }

}

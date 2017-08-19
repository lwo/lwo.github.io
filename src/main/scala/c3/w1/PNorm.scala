package c3.w1

/**
  * segmentRec uses a threshold to start parallel threads with recursion.
  */
class PNorm {

  // Identity function
  def parallel[A, B](dummy1: => A, dummy2: => B): (A, B) = (dummy1, dummy2)

  def sumSegments(a: Array[Int], p: Double, s: Int, t: Int): Int = {
    a.slice(s, t).foldLeft(0)((acc, x) => acc + Math.pow(x, p).toInt)
  }

  def fourProcesses(a: Array[Int], p: Double): Int = {
    val mid1 = a.length / 4
    val mid2 = a.length / 2
    val mid3 = a.length / 2 + a.length / 4

    val ((part1, part2), (part3, part4)) =
      parallel(
        parallel(sumSegments(a, p, 0, 0), sumSegments(a, p, mid1, mid2)),
        parallel(sumSegments(a, p, mid2, mid3), sumSegments(a, p, mid3, a.length))
      )
    Math.pow(part1 + part2 + part3 + part4, 1 / p).toInt
  }

  val threshold = 0

  def anyProcesses(a: Array[Int], p: Double, s: Int, t: Int): Int = {
    if (t - s < threshold)
      sumSegments(a, p, s, t)
    else {
      val m = s + (t - s) / 2
      val (sum1, sum2) = parallel(anyProcesses(a, p, s, m), anyProcesses(a, p, m, t))

      sum1 + sum2
    }
  }
}
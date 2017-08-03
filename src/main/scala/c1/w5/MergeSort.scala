package c1.w5

object MergeSort extends App {

  def msort(xs: List[Int]): List[Int] = {

    val pivot = xs.length / 2
    if (pivot == 0) xs
    else {
      val (left, right) = xs splitAt pivot
      merge(msort(left), msort(right))
    }
  }

  def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
    case (Nil, ys1) => ys1
    case (xs1, Nil) => xs1
    case (x :: xs1, y :: ys1) =>
      if (x < y) x :: merge(xs1, ys)
      else y :: merge(xs, ys1)
  }

  val before: List[Int] = List(5, 2, 9, 2, -4, 3)
  println(before)
  private val after = msort(before)
  println(after)
  assert ( after == List(-4, 2, 2, 3, 5, 9))
}
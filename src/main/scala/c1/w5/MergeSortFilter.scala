package c1.w5

object MergeSortFilter extends App {

  def msort(xs: List[Int]): List[Int] = {

    val pivot = xs.length / 2
    if (pivot == 0) xs
    else
      msort(xs filter (_ < xs(pivot))) ::: (xs filter (_ == xs(pivot))) ::: msort(xs filter (_ > xs(pivot)))
  }

  val before: List[Int] = List(5, 2, 9, 2, -4, 3)
  println(before)
  private val after = msort(before)
  println(after)
  assert ( after == List(-4, 2, 2, 3, 5, 9))
}
package c1.w5

object MergeSortFilterPolymorphic extends App {

  def msort[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {

    val pivot = xs.length / 2
    if (pivot == 0) xs
    else
      msort(xs filter (ord.lt(_, xs(pivot))))(ord) ::: (xs filter (_ == xs(pivot))) ::: msort(xs filter (ord.gt(_, xs(pivot))))(ord)
  }

  val before: List[Int] = List(5, 2, 9, 2, -4, 3)
  println(before)
  private val after = msort(before)
  println(after)
  assert(after == List(-4, 2, 2, 3, 5, 9))
}
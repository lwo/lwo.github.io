package c1.w5

object SampleListFunction extends App {
  def removeAt[T](n: Int, xs: List[T]): List[T] = xs match {
    case Nil => Nil
    case y :: ys => if (n == 0) removeAt(-1, ys) else y :: removeAt(n - 1, ys)
  }

  private val chars = removeAt(1, List('a', 'b', 'c', 'd'))
  println(chars)
  assert( chars == List('a', 'c', 'd') )
}

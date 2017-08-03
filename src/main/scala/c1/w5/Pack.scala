package c1.w5

object Pack extends App {
  def pack[T](xs: List[T]): List[List[T]] = xs match {
    case Nil => Nil
    case y :: ys =>
      val (left, right) = xs span (_ == y)
      left :: pack(right)
  }

  def encode[T](xs: List[T]): List[(T, Int)] = pack(xs) map ( x=> (x.head, x.size ) )

  val list = List("a", "a", "a", "b", "c", "c", "a")
  val packed = pack(list)
  println(list)
  println(packed)
  assert(packed == List(List("a", "a", "a"), List("b"), List("c", "c"), List("a")))

  val list2 = List("a", "a", "a", "b", "c", "c", "a")
  val encoded = encode(list2)
  println(list2)
  println(encoded)
  assert(encoded == List(("a", 3), ("b", 1), ("c", 2), ("a", 1)))
}

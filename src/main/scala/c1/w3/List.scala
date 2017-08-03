package c1.w3


trait List[T] {
  def isEmpty: Boolean

  def head: T

  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false

  override def toString: String = "(" + head + ")" + tail
}

class Nil[T] extends List[T] {
  def isEmpty: Boolean = true

  def head: Nothing = throw new NoSuchElementException("Nil.head")

  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

object List {
  def apply[T](): List[T] = new Nil()
  def apply[T](x1: T): List[T] = new Cons(x1, apply())
  def apply[T](x1: T, x2: T): List[T] = new Cons(x1, apply(x2))
  def apply[T](x1: T, x2: T, x3: T): List[T] = new Cons(x1, apply(x2, x3))

}

object Main {
  def main(args: Array[String]): Unit = {
    val l = List(1, 2, 3)
    println("0th of (3,2,1): " + nth(0, l))
    println("1st of (3,2,1): " + nth(1, l))
    println("2nd of (3,2,1): " + nth(2, l))
    println("3rd of (3,2,1): " + nth(3, l)) // Fatal
  }

  def nth[A](n: Int, xs: List[A]): A = {
    if (xs.isEmpty)
      throw new IndexOutOfBoundsException
    else if (n == 0) xs.head
    else nth(n - 1, xs.tail)
  }

}
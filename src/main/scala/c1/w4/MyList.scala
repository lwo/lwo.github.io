package c1.w4

trait MyList[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: MyList[T]
  def prepend[U >: T](elem: U): MyList[U] = new Cons(elem, this)
}
class Cons[T](val head: T, val tail: MyList[T]) extends MyList[T] {
  def isEmpty = false
  
  override def toString: String = {
    def foreach(l: MyList[T], str: String): String = {
      if(l.isEmpty) str + ")"
      else if(l.tail.isEmpty) foreach(l.tail, str +l.head)
      else foreach(l.tail, str + l.head+ ", ")
    }

    foreach(this, "MyList(")
  }
}
class Nil[T] extends MyList[T] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")

  override def toString = "Nil"
}
object Nil2 extends MyList[Nothing] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
  override def toString = "Nil"
}

object MyList {
  def apply[T](x1: T, x2: T, x3: T, x4: T): MyList[T] = new Cons(x1, apply(x2, x3, x4))
  def apply[T](x1: T, x2: T, x3: T): MyList[T] = new Cons(x1, apply(x2, x3))
  def apply[T](x1: T, x2: T): MyList[T] = new Cons(x1, apply(x2))
  def apply[T](x1: T) = new Cons(x1, new Nil)
  def apply[T](): MyList[T] = new Nil
}

object ListMain {
  def main(args: Array[String]): Unit = {
    println(MyList())
    println(MyList(1))
    println(MyList(1, 2))
    println(MyList(1, 2, 3))
    println(MyList(1, 2, 3, 4))
  }
}
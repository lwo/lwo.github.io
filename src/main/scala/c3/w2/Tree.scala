package c3.w2

sealed class Tree[+A] {
  def toList: List[A] = this match {
    case Leaf(v) => List(v)
    case Node(l, r) => l.toList ++ r.toList
  }

  def map[B](f: A => B): Tree[B] = this match {
    case Leaf(v) => Leaf(f(v))
    case Node(l, r) => Node(l.map(f), r.map(f))
  }
}

case class Leaf[A](element: A) extends Tree[A] {
  override def toString: String = s"($element)"
}

case class Node[A](l: Tree[A], r: Tree[A]) extends Tree[A] {
  override def toString: String = s"{" + l + " | " + r + "}"
}

object Tree {
  def apply[A](v: A): Leaf[A] = Leaf(v)

  def apply[A](l: Tree[A], r: Tree[A]): Tree[A] = Node(l, r)
}
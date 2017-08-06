package c2.w1

/**
  * A monad design pattern
  *
  * @tparam T The type this monad returns
  *
  * Note:
  * identity: Option(x).flatMap(f) == f(x) where f(x) = Option(x)
  */
abstract class Option[+T] {
  def flatMap[U](f: T => Option[U]): Option[U] = this match {
    case Some(x) => f(x)
    case None => None
  }

  def Try[A](a: => A): Option[A] = {
    try Some(a)
    catch {
      case e:
        Exception => None
    }
  }
}

object Option {
  def apply[T](x: T): Option[T] = Some(x)
}

case class Some[T](get: T) extends Option[T]

case object None extends Option[Nothing]


 
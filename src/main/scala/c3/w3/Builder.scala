package c3.w3

/**
  * @tparam A Type of the elements we add
  * @tparam Repr Type of the collection
  */
trait Builder[A, Repr] {
  def +=(elem: A): Builder[A, Repr]

  def result: Repr

  def newBuilder: Builder[A, Repr]

  def filter(p: A => Boolean): Repr = {
    val b = newBuilder
    for (x <- this) if (p(x)) b += x
    b.result
  }

}

/**
  * Repr
  * Type of the collection
  */
trait Repr {
  def map: Repr
}


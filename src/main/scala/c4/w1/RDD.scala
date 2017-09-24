package c4.w1

/**
  * RDD
  * Resilient Distributed Datasets
  * Just to show it is like other Monads
  * @tparam T Type T
  */
abstract class RDD[T] {
  def map[U](f: T => U): RDD[U] = ???
  def flatMap[U](f: T => TraversableOnce[U]): RDD[U] = ???
  def filter(f: T => Boolean): RDD[T] = ???
  def reduce(f: (T, T) => T): T = ???
  def fold(z: T)(op: (T, T) => T): T = ???
  def aggregate[U](z: U)(seqop: (U, T) => U, combop: (U, U) => U): RDD[U] = ???
  def distinct(): RDD[T] = ???
  // and more
}
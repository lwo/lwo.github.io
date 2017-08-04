package c1.w6

import scala.collection.immutable

object nestedSequence extends App {

  type v = immutable.IndexedSeq[(Int, Int)]

  def isPrime(n: Int): Boolean = 2 until n forall (_ % n != 0)

  def flatMap1(n: Int): v = {
    (1 to n).flatMap(i => (1 to i) map (j => (i, j))) filter { case (i, j) => isPrime(i + j) }
  }

  def flatMap2(n: Int): v = {
    (1 to n).flatMap(i => (1 to i) map (j => (i, j))) filter { case (i, j) => isPrime(i + j) }
  }

  def flatMap3(n: Int): v = {
    for {
      i <- 1 to n // implicit flatmap
      j <- 1 to i // map
      if isPrime(i + j)
    } yield (i, j)
  }

  println(flatMap1(7))
  println(flatMap2(7))
  println(flatMap3(7))

}

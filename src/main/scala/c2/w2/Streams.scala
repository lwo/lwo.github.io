package c2.w2

/**
  * Streams demo class
  *
  * Note the list and stream range is isomorphic.
  */
class Streams {
  def isOdd: (Int) => Boolean = (x: Int) => x % 2 == 1

  def listRange(lo: Int, hi: Int): List[Int] = {
    if (lo >= hi) Nil
    else lo :: listRange(lo + 1, hi)
  }

  def streamRange(lo: Int, hi: Int): Stream[Int] = {
    if (lo >= hi) Stream.empty
    else Stream.cons(lo, streamRange(lo + 1, hi))
  }

  def from(n: Int): Stream[Int] = n #:: from(n + 1)

  /**
    * The collection of natural numbers
    *
    * @return The stream of all natural numbers
    */
  object naturalNumbers {
    from(0)
  }


  object primeNumbers {
    /**
      * sieve
      * The Sieve of Eratosthenes:
      *
      * @param stream The stream
      * @return
      */
    def sieve(stream: Stream[Int]): Stream[Int] = {
      stream.head #:: sieve(stream.tail filter (_ % stream.head != 0))
    }

    sieve(from(2))
  }

  /**
    * sqrtStream
    *
    * @param x The question
    * @return The Square root that is good enough
    */
  def sqrtStream(x: Double): Double = {
    def isGoodEnough(guess: Double) = Math.abs(guess * guess - x) / x < 0.0001

    def improve(guess: Double) = (guess + x / guess) / 2

    lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)
    (guesses filter isGoodEnough).head
  }

}
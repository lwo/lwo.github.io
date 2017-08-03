package c1.w1

import math.abs

object FixedPoint extends App {
  val LIMIT = 0.0001

  def isCloseEnough(x: Double, y: Double): Boolean =
    abs((x - y) / x) < LIMIT

  def isCloseEnoughBetter(x: Double, y: Double): Boolean =
    abs((x - y) / x) / x < LIMIT

  def fixedPoint(f: Double => Double)(firstGuess: Double): Double = {
    def iterate(guess: Double): Double = {
      println("guess: " + guess)
      val next = f(guess)
      if (isCloseEnoughBetter(guess, next)) next
      else iterate(next)
    }

    iterate(firstGuess)
  }

  def sqrt(x: Double): Double = fixedPoint(y => (y + x / y) / 2)(1)

  def averageDamp(f: Double => Double)(x: Double): Double = (x + f(x)) / 2

  def sqrt2(x: Double): Double = fixedPoint(averageDamp(y => x / y))(1)

  fixedPoint(x => 1 + x / 2)(1)
  sqrt(9)
  sqrt2(9)
}
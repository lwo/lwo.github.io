package c1.w1

/*
Scala Evaluation Strategy

Call-by-value (lazy evaluation) is exponentially more efficient than call by name.
 */
object EvaluationStrategy extends App {

  def square(x: Double): Double = x*x
  def sumOfSquares(x: Double, y: Double): Double = square(x) + square(y)
  def lazySumOfSquares(x: Double, y: => Double): Double = square(x) + square(y)
  lazySumOfSquares(3, 3)
  square(3)
}
package c3.w1

import scala.util.Random

/**
  * Monte Carlo experiments are a broad class of computational algorithms that rely on repeated random sampling
  * to obtain numerical results.
  */
class MonteCarlo {

  // Identity function
  def parallel[A, B](taskA: => A, taskB: => B): (A, B) = (taskA, taskB)

  /**
    * circleArea
    *
    * Area of a circle with random data
    *
    * @param iterations Number of inputs
    * @param radius     Radius of the circle
    * @return Approximate area to pi*(r*r)
    */
  def circleArea(iterations: Int, radius: Int = 1): Double = {
    val randomX = new Random
    val randomY = new Random
    var hits = 0
    for (i <- 0 until iterations) {
      val x = randomX.nextDouble()
      val y = randomY.nextDouble()
      if (x * x + y * y < radius) hits += 1
    }
    4.0 * hits / iterations
  }

  def circleAreaInParallel(iterations: Int, radius: Int = 1): Double = {
    val i = iterations / 4
    val ((pi1, pi2), (pi3, pi4)) = parallel(
      parallel(circleArea(i), circleArea(i)),
      parallel(circleArea(i), circleArea(i))
    )
    (pi1 + pi2 + pi3 + pi4) / 4
  }

}

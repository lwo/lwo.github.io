package c3.w1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MonteCarloSuite extends FunSuite {

  trait experments {
    def monteCarlo = new MonteCarlo
  }

  test("Approximate surface area. Allow One percent to be off") {
    new experments {
      val radius = 1
      val area: Double = monteCarlo.circleArea(1000000, radius)
      val p: Double = Math.PI * radius
      assert(area > p * 0.99 && area < p * 1.01)
    }
  }

  test("Approximate surface area. Allow One percent to be off. Use parallel") {
    new experments {
      val radius = 1
      val area: Double = monteCarlo.circleAreaInParallel(1000000, radius)
      val p: Double = Math.PI * radius
      assert(area > p * 0.99 && area < p * 1.01)
    }
  }

}
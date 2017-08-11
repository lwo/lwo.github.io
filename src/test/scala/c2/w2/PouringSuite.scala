package c2.w2

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PouringSuite extends FunSuite {

  test("pour with 4 and 9 capacity glasses to get 6 units") {
    val problem: Pouring = new Pouring(Vector(4, 9))
    val solution: Stream[Any] = problem.solution(6)
    println(solution)
  }
}

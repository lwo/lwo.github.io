package c2

import c2.w2.Pouring
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PouringSuite extends FunSuite {

  test("a") {
    val problem = new Pouring(Vector(4, 9))
  }
}

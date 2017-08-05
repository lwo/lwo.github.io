package c2

import c2.w1._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PartialFunctionsSuite extends FunSuite {

  test("ping") {
    val partial = new PartialFunctions
    assert(partial.f1("ping") == "pong")
  }

  test("ping") {
    val partial = new PartialFunctions
    assert(partial.f1("ping") == "pong")
  }

}

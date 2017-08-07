package c1

import c1.w4.{Empty, NonEmpty}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class IntSetSuite extends FunSuite {
  test("Induction step 1: NonEmpty(x, l, r) z=x") {
    val x = 1
    val z = 1
    assert(new NonEmpty(x, Empty, Empty) contains z)
  }
  test("Induction step 2: NonEmpty(x, l, r)") {
    val x = 1
    val z = 2
    assert(!(new NonEmpty(x, Empty, Empty) contains z))
  }
  test("Induction step 3: NonEmpty(x, l, r inc y) y < x") {
    val x = 2
    val y = 1
    assert(!(new NonEmpty(x, Empty, Empty incl y) contains y))
  }
  test("Induction step 4: NonEmpty(x, l, r inc y)") {
    val x = 1
    val y = 2
    assert(new NonEmpty(x, Empty, Empty incl y) contains y)
  }
  test("Induction step 5: NonEmpty(x, l inc y, r) y < x") {
    val x = 2
    val y = 1
    assert(new NonEmpty(x, Empty incl y, Empty) contains y)
  }
  test("Induction step 6: NonEmpty(x, l inc y, r) y > x") {
    val x = 1
    val y = 2
    assert(!(new NonEmpty(x, Empty incl y, Empty) contains y))
  }
  test("Induction step 7: x!=y") {
    val x = 1
    val y = 2
    assert(!( (Empty incl y) contains x))
  }
  test("x, y, z") {
    val x = 1 // head
    val y = 2 // left leave
    val z = 3 // right leave
    assert(!(new NonEmpty(x, Empty incl y, Empty incl z) contains y))
    assert(new NonEmpty(x, Empty incl y, Empty incl z) contains z)
  }
}

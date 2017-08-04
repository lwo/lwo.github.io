import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import c1.w4.Nat

trait Numbers {

  val Zero = Nat(0)
  val One: Nat = Nat(1)
  val Two: Nat = Nat(2)
  val Three: Nat = Nat(3)
  val Four: Nat = Nat(4)
  val Five: Nat = Nat(5)
  val Six: Nat = Nat(6)
  val Seven: Nat = Nat(7)
  val Eight: Nat = Nat(8)
  val Nine: Nat = Nat(9)
  val Ten: Nat = Nat(10)
}

@RunWith(classOf[JUnitRunner])
class NatSuite extends FunSuite {

  test("Comparison") {
    new Numbers {
      assert(Zero == Zero)
      assert(One == One)
      assert(!(One == Two))
    }
  }

  test("Adding") {
    new Numbers {
      assert(Zero + Zero == Zero)
      assert(Zero + One == One)
      assert(One + Zero == One)
      assert(One + One == Two)
      assert(Two + Five == Seven)
    }
  }

  test("Subtraction") {
    new Numbers {
      assert(Nine - Nine == Zero)
      assert(Nine - One == Eight)
      assert(Nine - Eight == One)
      assert(One - One == Zero)
      assertThrows[Error] {
        One - Two
      }
    }
  }

  test("Multiplication") {
    new Numbers {
      assert(Zero * Zero == Zero)
      assert(One * Zero == Zero)
      assert(One * One == One)
      assert(Two * Three == Six)
      assert(Three * Two == Six)
    }
  }

  test("foldLeft sum") {
    new Numbers {
      val list: List[Nat] = List(Zero, One, Two, Three, Four, Five)
      assert( (list foldLeft Ten)(_+_) ==  Nat(25))
    }
  }

}
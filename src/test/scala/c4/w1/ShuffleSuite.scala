package c4.w1


import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class ShuffleSuite extends FunSuite {

  trait S {
    val shuffle = new Shuffle
  }

  test("Shuffle") {
    new S {
      val purchasesPerMonth: Array[(Int, (Int, Double))] = shuffle.purchasesPerMonth
      assert( purchasesPerMonth.length == 1)
    }
  }

}

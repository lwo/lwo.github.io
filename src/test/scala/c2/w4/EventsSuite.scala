package c2.w4

import c2.w4.frp._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EventsSuite extends FunSuite {

  test("imperative") {
    val a, b = new BankAccountPublisher
    val c = new Consolidator(List(a, b))

    assert (c.totalBalance == 0)
    a deposit 20
    assert (c.totalBalance == 20)

    b deposit 30
    assert (c.totalBalance == 50)

    a withdraw 20
    assert(c.totalBalance == 30)
  }

  test("functional") {
    def consolidate(observers: List[BankAccountSignal]): Signal[Int] = {
      Signal {
        (observers map (_.balance())).sum
      }
    }

    val a, b = new BankAccountSignal
    val signalBalance = consolidate(List(a, b))

    assert (signalBalance() == 0)
    a deposit 20
    assert (signalBalance() == 20)

    b deposit 30
    assert (signalBalance() == 50)

    a withdraw 20
    assert(signalBalance() == 30)

    def signalExchangeRateDollar = Signal(1.1D)
    val rate1 = Signal( signalBalance() * signalExchangeRateDollar())
    assert(rate1() == 33.0D)
    a deposit 10
    assert(rate1() == 44.0D)

    val x = Var(1)
    val y = Var(2)
    val z = Var(3)
    var count = Var(x() + y() + z())
    var e = count()
    assert( e == 6)
    println("=========================================================")
    x() = 4
    e = count()
    assert( e == 9)
    println("=========================================================")
    x() = 1
    e = count()
    assert( e == 6)

  }
}

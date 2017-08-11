package c2.w3

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BankAccountSuite extends FunSuite {

  test("Operational Equivalence") {
    val ba1 = new BankAccount
    val ba2 = new BankAccount

    var result1 = 0
    var result2 = 0

    result1 = ba1 deposit 100

    try {
      result2 = ba2 withdraw 100
    } catch {
      case e: Error => 0
    }

    result1 = ba2 deposit 100

    try {
      result2 = ba1 withdraw 100
    } catch {
      case e: Error => 0
    }

    if (result1 == result2)
      println("Equivalent")
    else
      println("Not equivalent")
  }

}
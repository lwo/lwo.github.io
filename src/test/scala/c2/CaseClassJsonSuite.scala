package c2

import c2.w1._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CaseClassJsonSuite extends FunSuite {

  trait json {
    val jsonParser = new jsonParser
    val data:JSON = JObj(Map(
      "firstName" -> JStr("John"),
      "lastName" -> JStr("Smith"),
      "address" -> JObj(Map(
        "streetAddress" -> JStr("21 2nd Street"),
        "state" -> JStr("NY"),
        "postalCode" -> JNum(10021)
      )),
      "phoneNumbers" -> JSeq(List(
        JObj(Map(
          "type" -> JStr("home"),
          "number" -> JStr("212 555-1234")
        )),
        JObj(Map(
          "type" -> JStr("fax"),
          "number" -> JStr("646 555-4567")
        ))
      ))
    ))
  }

  test("show") {
    new json {
      val str:String = jsonParser.show(data)
      println(str)
      assert( str === "{\"firstName\": \"John\", \"lastName\": \"Smith\", \"address\": {\"streetAddress\": \"21 2nd Street\", \"state\": \"NY\", \"postalCode\": 10021.0}, \"phoneNumbers\": [{\"type\": \"home\", \"number\": \"212 555-1234\"}, {\"type\": \"fax\", \"number\": \"646 555-4567\"}]}")
    }
  }

  test("search") {
    new json {
      val result = for {
        JObj(bindings) <- List(data)
        JSeq(phones) = bindings("phoneNumbers")
        JObj(phone) <- phones
        JStr(digits) = phone("number")
        if digits startsWith "212"
      } yield (bindings("firstName"), bindings("lastName"))
      println(result)
      assert(result === List((JStr("John"),JStr("Smith"))))
    }
  }

}
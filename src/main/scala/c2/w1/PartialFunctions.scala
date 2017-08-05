package c2.w1

class PartialFunctions {

  val f1: String => String = {
    case "ping" => "pong"
  }

  val f2: PartialFunction[String, String] = {
    case "ping" => "pong"
  }

  val f3: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: y :: rest => "two"
  }

  val f4: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: rest => rest match {
      case Nil => "two"
    }
  }

}
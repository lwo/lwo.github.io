package c2.w1

abstract class JSON {
  type JBinding = (String, JSON)
}

case class JSeq(elems: List[JSON]) extends JSON

case class JObj(bindings: Map[String, JSON]) extends JSON

case class JNum(num: Double) extends JSON

case class JStr(str: String) extends JSON

case class JBool(b: Boolean) extends JSON

case object JNull extends JSON

class jsonParser {

  def show(json: JSON): String = json match {

    case JSeq(elems) =>
      "[" + {
        elems map show mkString ", "
      } + "]"
    case JObj(bindings) =>
      val assocs = bindings map {
        case (key, value) => key + ": " + show(value)
        // The case would expand into:
        //   new Function1[JBinding, String] {
        //     def apply(x: JBinding) = x match {
        //       case (key, value) => key + ": " + value
        //     }
        //   }
      }
      "{" + (assocs mkString ", ") + "}"
    case JNum(num) => num.toString
    case JStr(str) => '\"' + str + '\"'
    case JBool(b) => b.toString
    case JNull => "null"
  }

}
package c1.w4

abstract class MyBoolean {

  def ifThenElse[T](t: => T, e: => T): T

  def &&(x: => MyBoolean): MyBoolean = ifThenElse(x, False)

  def ||(x: => MyBoolean): MyBoolean = ifThenElse(True, x)

  def unary_! : MyBoolean = ifThenElse(False, True)

  def ==(x: MyBoolean): MyBoolean = ifThenElse(x, x.unary_!)

  def !=(x: MyBoolean): MyBoolean = ifThenElse(x.unary_!, x)

  def <(x: MyBoolean): MyBoolean = ifThenElse(False, x)

  def >(x: MyBoolean): MyBoolean = ifThenElse(x.unary_!, False)

}
object False extends MyBoolean {
  def ifThenElse[T](first: => T, second: => T): T = second
}

object True extends MyBoolean {
  def ifThenElse[T](first: => T, second: => T): T = first
}

object main {
  def main(args: Array[String]): Unit = {

    val yes = println("true")
    val no = println("false")
    True.ifThenElse(yes, no)
    False.ifThenElse(yes, no)
    println("===================================================================")
  }
}
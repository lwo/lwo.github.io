package c1.w2

object ProductSum extends App {

  def productSum(f: Int => Int)(a: Int, b:Int): Int = {
    if ( a > b ) 1
    else f(a) + productSum(f)( a + 1, b)
  }

  def productSum2(f: Int => Int)(a: Int)(b:Int): Int = {
    if ( a > b ) 1
    else f(a) + productSum2(f)( a + 1)( b)
  }

  println(productSum(x=>x * x)(1, 10)) // square
  println(productSum2(x=>x * x * x)(1)(10)) // cube
}

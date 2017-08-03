package c1.w4

trait Expression {
  def eval: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.eval + e2.eval
    case Product(e1, e2) => e1.eval * e2.eval
    case Divide(e1, e2) => e1.eval / e2.eval
  }
  def show: String = this match {
    case Number(n) => ""+n
    case Sum(e1, e2) => e1.show + " + " + e2.show
    case Product(Sum(e1, e2), Sum(e3, e4)) => "("+e1.show + " + " + e2.show + ")"+" * " + "("+e3.show + " + " + e4.show + ")"
    case Product(Sum(e1, e2), e3) => "("+e1.show + " + " + e2.show + ")"+" * "+ e3.show
    case Product(e1, Sum(e2, e3)) => e1.show + " * " + "("+e2.show + " + " + e3.show + ")"
    case Product(e1, e2) => e1.show + " * " + e2.show
    case Var(s) => s
  }

}
case class Number(n: Int) extends Expression
case class Sum(e1: Expression, e2: Expression) extends Expression
case class Var(x: String) extends Expression
case class Product(e1: Expression, e2: Expression) extends Expression
case class Divide(e1: Expression, e2: Expression) extends Expression

object test {
  def main(args: Array[String]): Unit = {
    val expr1 = Sum(Number(1), Number(2))
    val expr2 = Sum(Number(3), Number(4))
    val expr3 = Sum(expr1, expr2)
    val expr4 = Sum(Product(Number(2), Var("x")), Var("y"))
    val expr5 = Product(Sum(Number(2), Var("x")), Var("y"))
    val expr6 = Product(Var("y"), Sum(Number(2), Var("x")))
    val expr7 = Product(expr1, expr2)
    println(expr1.show+ " = "+ expr1.eval)
    println(expr2.show+ " = "+ expr2.eval) 
    println(expr3.show+ " = "+ expr3.eval) 
    
    println(expr4.show)
    println(expr5.show)
    println(expr6.show)
    println(expr7.show)
  }
}
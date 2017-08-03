package c1.w2

/**
  * Rational number: x/y
  * @param x numerator
  * @param y denominator
  */
class Rational(x: Int, y: Int) {
  require(y != 0, "Denominator cannot be zero.")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  private def abs(a: Int) = if (a >= 0) a else -a

  private val g = abs(gcd(x, y))

  val numerator: Int = x / g
  val denominator: Int = y / g

  def add(that: Rational): Rational = {
    new Rational(
      this.numerator * that.denominator + that.numerator * this.denominator,
      this.denominator * that.denominator
    )
  }

  def +(that: Rational): Rational = {
    new Rational(
      this.numerator * that.denominator + that.numerator * this.denominator,
      this.denominator * that.denominator
    )
  }

  def neg: Rational = new Rational(-numerator, denominator)

  def unary_- : Rational = new Rational(-numerator, denominator)

  def sub(that: Rational): Rational = add(that.neg)

  def -(that: Rational): Rational = this + -that


  def less(that: Rational): Boolean = {
    this.numerator * that.denominator < that.numerator * this.denominator
  }

  def <(that: Rational): Boolean = {
    this.numerator * that.denominator < that.numerator * this.denominator
  }

  def max(that: Rational): Rational = {
    if (this.less(that)) that else this
  }

  override def toString: String = numerator + "/" + denominator
}

object Main extends App {
  println(new Rational(1, 2) add new Rational(3, 4))
}
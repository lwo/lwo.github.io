package c1.w4

/**
  * Nat is a Natural number comprised out of Nat instances.
  * One is one Nat
  * Two is a Nat in a Nat
  * Three is a Nat in a Nat in a Nat
  * Four = etc
  */
abstract class Nat {
  def isZero: Boolean

  def predecessor: Nat

  def successor = new AddOne(this)

  def +(that: Nat): Nat
  //def plus(that: Nat): Nat

  def -(that: Nat): Nat

  def *(that: Nat): Nat

  def /(that: Nat): Nat

  def ==(that: Nat): Boolean

  def <(that: Nat): Boolean

  def <=(that: Nat): Boolean

  def >=(that: Nat): Boolean

  def >(that: Nat): Boolean
}

object AddZero extends Nat {
  def isZero = true

  def predecessor = throw new Error("0.predecessor")

  def +(that: Nat): Nat = that
  //def plus(that: Nat): Nat = that

  def -(that: Nat): AddZero.type = if (that.isZero) this else throw new Error("negative number")

  def *(that: Nat): Nat = AddZero

  def /(that: Nat): Nat = AddZero

  def ==(that: Nat): Boolean = that.isZero

  def <(that: Nat): Boolean = !that.isZero

  def <=(that: Nat): Boolean = true

  def >=(that: Nat): Boolean = that.isZero

  def >(that: Nat): Boolean = throw new Error("negative number")

  override def toString = "0 => AddZero"
}

class AddOne(val predecessor: Nat) extends Nat {
  def isZero = false

  /**
    * +
    *
    * Because of the .successor this could also be written as
    * new AddOne( predecessor + that)
    *
    * Note that the recursion will add an AddOne for each number in this
    * and when it reaches AddZero, it will get the base AddZero from that.
    * E.g. THREE plus TWO would be AddOne_this(AddOne_this(AddOne_that(Addone_that(AddZero))))
    *
    * @param that The Number to add
    * @return The result
    */
  def +(that: Nat): Nat = (predecessor + that).successor
  //def plus(that: Nat):Nat = this.+(that)

  def -(that: Nat): Nat = if (that.isZero) this else predecessor - that.predecessor

  def *(that: Nat): Nat = {
    def product(acc: Nat, x: Nat): Nat = {
      if (x.isZero)
        acc
      else
        product(acc + this, x.predecessor)
    }

    if (this.isZero || that.isZero) AddZero else product(this, that.predecessor)
  }

  def /(that: Nat): Nat = {
    that
  }

  def ==(that: Nat): Boolean = if (that.isZero) this.isZero else predecessor == that.predecessor

  def <(that: Nat): Boolean = !that.isZero && predecessor < that.predecessor

  def <=(that: Nat): Boolean = this == that || this < that

  def >=(that: Nat): Boolean = this == that || this > that

  def >(that: Nat): Boolean = predecessor > that.predecessor

  override def toString: String = {
    def findNumber(nat: Nat): Int =
      if (nat.isZero) 0
      else 1 + findNumber(nat.predecessor)

    val number = findNumber(this)
    String.valueOf(number) + " => " +
      ((1 to number) fold "AddZero") ((s, _) => "AddOne(" + s + ")")
  }
}

object testNat   extends App {

  def apply(n: Int): Nat = {
    if ( n == 0) AddZero
    else new AddOne(apply(n - 1))
  }

  val ZERO = apply(0)
  val ONE = apply(1)
  val TWO = apply(2)
  val THREE = apply(3)
  val FOUR = apply(4)
  val FIVE = apply(5)
  val FIFTEEN = apply(15)

  val list:List[Nat] = List(ONE, TWO, THREE, FOUR, FIVE)
  assert( {list filter (_ < THREE)} == List(ONE, TWO))
  assert( (list foldLeft ZERO)(_+_) == FIFTEEN)
}
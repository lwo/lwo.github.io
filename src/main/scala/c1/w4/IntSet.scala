package c1.w4

abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet

  /**
    * assertAllPos covariant
    * A upper bound, starting from IntSet:
    * @param r An IntSet
    * @tparam S Type IntSet
    * @return Are all positive?
    */
  def assertAllPos[S <: IntSet](r: S): Boolean = true
}


class Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  override def toString = "."
  def union(other: IntSet): IntSet = other
}

object Empty2 extends IntSet {
  def contains(x: Int): Boolean = false
  
  def incl(x: Int): IntSet = new NonEmpty(x, Empty2, Empty2)
  
  override def toString = "."
  def union(other: IntSet): IntSet = other
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean = {
    if(x < elem) left.contains(x)
    else if (x > elem) right.contains(x)
    else true
  }
  def incl(x: Int): IntSet =  {
    if(x < elem) new NonEmpty(elem, left.incl(x), right)
    else if(x > elem) new NonEmpty(elem, left, right.incl(x))
    else this
  }
  override def toString: String = "{"+left+elem+right+"}"
  
  def union(other: IntSet):IntSet = {
    ((left union right) union other) incl elem
  }
}

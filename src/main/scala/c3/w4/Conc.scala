package c3.w4


/**
  * Conc list: the parallel counterpart of Cons lists.
  * @tparam T A type
  */
sealed trait Conc[@specialized(Int, Long, Float, Double) +T] {
  def level: Int
  def size: Int
  def left: Conc[T]
  def right: Conc[T]
  def normalized: Conc[T] = this
}

/**
  * <> to ensure trees are always balanced:
  *   a node can not contain an Empty subtree
  *   the level difference between left and right subtrees is 0 or 1
  * @param left the left subtree
  * @param right the right subtree
  * @tparam T A type
  */
case class <>[+T](left: Conc[T], right: Conc[T]) extends Conc[T] {
  val level = 1 + math.max(left.level, right.level)
  val size = left.size + right.size
}
sealed trait Leaf[T] extends Conc[T] {
  def left = sys.error("Leaves do not have children.")
  def right = sys.error("Leaves do not have children.")
}

class Single[@specialized(Int, Long, Float, Double) T](val x: T) extends Leaf[T] {
  def level = 0
  def size = 1
  override def toString = s"Single($x)"
}

case object Empty extends Leaf[Nothing] {
  def level = 0
  def size = 0
}

/**
  * Append a Conc
  * @param left the left subtree
  * @param right the right subtree
  * @tparam T A type
  */
case class Append[+T](left: Conc[T], right: Conc[T]) extends Conc[T] {
  val level = 1 + math.max(left.level, right.level)
  val size = left.size + right.size

}

object Conc {
  def appendLeaf[T](xs: Conc[T], ys: Single[T]): Conc[T] = xs match {
    case Empty => ys
    case xs: Single[T] => new <>(xs,ys)
    case _ <> _ => Append(xs, ys)
    case xs: Append[T] => append(xs, ys)
  }

  @annotation.tailrec
  private def append[T](xs: Append[T], ys: Conc[T]): Conc[T] = {
    if (xs.right.level > ys.level) Append(xs, ys)
    else {
      val zs = new <>(xs.right, ys)
      xs.left match {
        case ws @ Append(_, _) => append(ws, zs)
        case ws if ws.level <= zs.level => <>(ws, zs)
        case ws => Append(ws, zs)
      }
    }
  }

  /**
    * <> is a concat operator
    * @param xs a left tree
    * @param ys a right tree
    * @tparam T a type
    * @return the new Conc
    */
  def <>[T](xs: Conc[T], ys: Conc[T]): Conc[T] = {
    if (xs == Empty) ys
    else if (ys == Empty) xs
    else concat(xs, ys)
  }

  private def concat[T](xs: Conc[T], ys: Conc[T]): Conc[T] = {
    val diff = ys.level - xs.level

    if(diff >= -1 && diff <= 1) {
      new <>(xs, ys)
    }

    // This xs tree is too large.
    else if(diff < -1) {
      // xs is left leaning
      if(xs.left.level >= xs.right.level) {
        val nr = concat(xs.right, ys)
        new <>(xs.left, nr)
      }
      // xs is right leaning
      else {
        val nrr = concat(xs.right.right, ys)
        if(nrr.level == xs.level - 3) {
          val nl = xs.left
          val nr = new <>(xs.right.left, nrr)
          new <>(nl ,nr)
        } else {
          val nl = new <>(xs.left, xs.right.left)
          val nr = nrr
          new <>(nl, nr)
        }
      }
    }

    // here the ys tree is too tall in height
    else {
      // ys is right leaning
      if (ys.right.level >= ys.left.level) {
        val nl = concat(xs, ys.left)
        new <>(nl, ys.right)
      }
      // ys is left leaning
      else {
        val nll = concat(xs, ys.left.left)
        if (nll.level == ys.level - 3) {
          val nl = new <>(nll, ys.left.right)
          val nr = ys.right
          new <>(nl, nr)
        } else {
          val nl = nll
          val nr = new <>(ys.left.right, ys.right)
          new <>(nl, nr)
        }
      }
    }
  }



}
package c3.w2


import common._

/**
  * ParallelMapReduceDependentElements
  *
  * Here the map function does rely on the outcome of a related accumulated element.
  * E.g. ScanLeft: the element depends on a calculated value from previous elements.
  *
  * List(a1, a2, ..., aN).scanLeft(f)(a0) = List(b0, b1, b2, ..., bN)
  * where b0 = a0 and bi = f(bi−1, ai) for 1 <= i <= N.
  */
class LeftScanElements {

  val THRESHOLD = 10

  /**
    * Sequential scanLeft operation.
    * This is easy as all previously calculated values are available.
    *
    * @param inp  The incoming array
    * @param from the left index,
    * @param to   the right index
    * @param a0   Initial element
    * @param f    A function on (ai, ai+1)
    * @param out  The result
    * @tparam A Type
    */
  def scanLeftSequential[A](inp: Array[A], from: Int, to: Int,
                            a0: A, f: (A, A) => A, out: Array[A]): Unit = {
    if (from < to) {
      var i = from
      var a = a0
      out(0) = a0
      while (i < to) {
        a = f(a, inp(i))
        i += 1
        out(i) = a
      }
    }
  }

  /**
    * Tree
    * A tree suitable for storing intermediate values
    *
    * @tparam A a type suitable for f
    */
  sealed abstract class Tree[+A] {
    val res: A
  }

  /**
    * Leaf
    *
    * @param left  the left index
    * @param right the right index
    * @param res   the intermediate value
    * @tparam A a type suitable for f
    */
  case class Leaf[A](left: Int, override val res: A, right: Int) extends Tree[A]

  /**
    * Node
    *
    * @param left  the from index
    * @param right the to index
    * @param res   the intermediate value
    * @tparam A a type suitable for f
    */
  case class Node[A](left: Tree[A], override val res: A, right: Tree[A]) extends Tree[A]

  /**
    * scanLeftParallel
    * Perform the scanLeft aggregation
    *
    * @param inp the raw input array
    * @param a0  the initial value
    * @param f   the function
    * @param out the result
    * @tparam A a type suitable for f
    */
  def scanLeftParallel[A](inp: Array[A], a0: A, f: (A, A) => A, out: Array[A]): Unit = {
    val tree = upsweep(inp, 0, inp.length, f)
    downsweep(inp, a0, f, tree, out)
    out(0) = a0
  }

  /**
    * upsweep
    * Create the tree from an array.
    * Create pre-calculated scan results with reduce..
    * Store the result in an intermediary field res.
    *
    * @param inp  The original array
    * @param from the left index
    * @param to   the right index
    * @param f    The function
    * @tparam A A type
    * @return A tree with intermediary values
    */
  private def upsweep[A](inp: Array[A], from: Int, to: Int, f: (A, A) => A): Tree[A] = {
    if (to - from < THRESHOLD)
      Leaf(from, reduce(inp, from + 1, to, inp(from), f), to)
    else {
      val mid = from + (to - from) / 2
      val (tL, tR) = parallel(upsweep(inp, from, mid, f), upsweep(inp, mid, to, f))
      Node(tL, f(tL.res, tR.res), tR)
    }
  }

  /**
    * reduce
    * Perform a scan for a small ranged sequence to locate the intermediary value.
    * E.g. 0 with 1, 2, 3, 4, 5 becomes the a0 for the next sequence.
    * Note this behaves as a scan, not a scanLeft or scanRight
    *
    * @param inp  the aggregated input
    * @param from the left index
    * @param to   the right index
    * @param a0   the intermediate value
    * @param f    the function
    * @tparam A A type
    * @return The reduce
    */
  private def reduce[A](inp: Array[A], from: Int, to: Int, a0: A, f: (A, A) => A): A = {
    var a = a0
    var i = from
    while (i < to) {
      a = f(a, inp(i))
      i = i + 1
    }
    a
  }

  /**
    * downsweep
    * For each node,
    *   take the left node
    *     take the a0
    *     recurse
    *   take the right node
    *     take the a0
    *     add to it the left node precalculated a0
    *     recurse
    * For the leaf
    *   calculate with scanLeft
    *
    * @param inp  Raw input array
    * @param a0   Initial value
    * @param f    The function
    * @param tree Intermediary tree
    * @param out  The result
    * @tparam A A type
    */
  private def downsweep[A](inp: Array[A], a0: A, f: (A, A) => A, tree: Tree[A], out: Array[A]): Unit = tree match {
    case Leaf(from, res, to) =>
      scanLeftSequential(inp, from, to, a0, f, out)
    case Node(l, _, r) =>
      parallel(downsweep(inp, a0, f, l, out), downsweep(inp, f(a0, l.res), f, r, out))
  }

}

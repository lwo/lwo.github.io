package c3.w2

import common._

/**
  * ParallelMapReduceIndependentElement
  *
  * Here the map function does not rely on the outcome of a previous accumulated value.
  * E.g. Sum
  */
class ParallelMapReduceIndependentElement {

  /**
    * reduce
    * Will perform an operation sequentially.
    *
    * @param tree The tree datastructure
    * @param f    A function which performs the operation
    * @tparam A A datatype
    * @return The aggregated result.
    */
  def reduce[A](tree: Tree[A], f: (A, A) => A): A = tree match {
    case Leaf(v) => v
    case Node(l, r) => f(reduce(l, f), reduce(r, f))
  }

  /**
    * parallelReduce
    * Will perform an operation in parallel.
    *
    * @param tree The tree datastructure
    * @param f    A function which performs the operation
    * @tparam A A datatype
    * @return The aggregated result.
    */
  def parallelReduce[A](tree: Tree[A], f: (A, A) => A): A = tree match {
    case Leaf(v) => v
    case Node(l, r) =>
      val (lb, rb) = parallel(parallelReduce(l, f), parallelReduce(r, f))
      f(lb, rb)
  }

}

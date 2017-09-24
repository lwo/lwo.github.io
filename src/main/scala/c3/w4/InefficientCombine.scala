package c3.w4

import c3.w3.Combiner

import scala.collection.mutable.ArrayBuffer

class InefficientCombine extends Combiner[Int, Array[Int]] {

  private val buffers = new ArrayBuffer[Int]

  override def combine(that: Combiner[Int, Array[Int]]) = {
    val xs = that.result
    val ys = this.buffers
    val r = new Array[Int](xs.length + ys.length)
    Array.copy(xs, 0, r, 0, xs.length) // n operations
    Array.copy(ys, 0, r, xs.length, ys.length) // m operations
    this
  }

  override def +=(elem: Int): InefficientCombine = {
    buffers += elem
    this
  }

  override def result: Array[Int] = buffers.result().toArray

  override def newBuilder = ??? // dummy
  override def hasNext = ???

  override def next() = ???
}

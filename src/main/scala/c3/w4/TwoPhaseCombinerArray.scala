package c3.w4

import scala.collection.parallel.Combiner
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag
import org.scalameter._
import common._

/**
  * Most data structures can be constructed in parallel using two-phase construction using an intermediate data store.
  * The combiner does not use the final data structure in its internal representation. The intermediate data structure:
  *   has an efficient combine method: O(logn+logm)O(log⁡n+log⁡m) or better
  *   Has an efficient += method (this ensures that individual processors can efficiently modify the data structure)
  *   Can be converted to the resulting data structure in O(n/P)O(n/P) time (n size of data structure, P number of processors)
  * @param parallelism the number of parallel processes ( available CPU )
  */
class TwoPhaseCombinerArray[T <: AnyRef: ClassTag](val parallelism: Int) extends Combiner[T, Array[T]] {
  private var numElems = 0

  /**
    * buffers - an intermediate data store of a double array to support the O(log n + log m) time
    * numElems  |0|6|10|n
    *            0 7
    *            1 8
    *            2 9
    *            3
    *
    */
  private val buffers = new ArrayBuffer[ArrayBuffer[T]]
  buffers += new ArrayBuffer[T]

  // O(1)
  def +=(elem: T): TwoPhaseCombinerArray.this.type = {
    buffers.last += elem
    numElems += 1
    this
  }

  // O(P)
  def combine[N <: T, That >: Array[T]](that: Combiner[N, That]): TwoPhaseCombinerArray[T] = {
    (that: @unchecked) match {
      case that: TwoPhaseCombinerArray[T] =>
        buffers ++= that.buffers // This copies the pointer into the second, etc array field
        numElems += that.numElems
        this
    }
  }

  /**
    * size - the number of elements
    * @return
    */
  def size = numElems

  def clear() = buffers.clear()


  /**
    * result
    * Use a parallel task to retrieve the intermediate combiner's data.
    * @return the array
    */
  def result: Array[T] = {
    val step = math.max(1, numElems / parallelism)
    val array = new Array[T](numElems)
    val starts = (0 until numElems by step) :+ numElems
    val chunks = starts.zip(starts.tail)
    val tasks = for ((from, end) <- chunks) yield task {
      copy(array, from, end)
    }
    tasks.foreach(_.join())
    array
  }

  private def copy(array: Array[T], from: Int, end: Int): Unit = {
    var i = from
    var j = 0
    while (i >= buffers(j).length) {
      i -= buffers(j).length
      j += 1
    }
    var k = from
    while (k < end) {
      array(k) = buffers(j)(i)
      i += 1
      if (i >= buffers(j).length) {
        i = 0
        j += 1
      }
      k += 1
    }
  }

}

object TwoPhaseCombinerArray {

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 60,
    Key.verbose -> true
  ) withWarmer new Warmer.Default

  def main(args: Array[String]) {
    val size = 1000000

    def run(p: Int) {
      val taskSupport = new collection.parallel.ForkJoinTaskSupport(
        new scala.concurrent.forkjoin.ForkJoinPool(p))
      val strings = (0 until size).map(_.toString)
      val time = standardConfig measure {
        val parallelized = strings.par
        parallelized.tasksupport = taskSupport
        def newCombiner = new TwoPhaseCombinerArray(p): Combiner[String, Array[String]]
        parallelized.aggregate(newCombiner)(_ += _, _ combine _).result
      }
      println(s"p = $p, time = $time ms")
    }

    run(1)
    run(2)
    run(4)
    run(8)
  }

}

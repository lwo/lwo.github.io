package c3.w2

import org.junit.runner.RunWith
import org.scalameter.{Key, Warmer, config}
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.util.Random

@RunWith(classOf[JUnitRunner])
class AssociativityMapReduceSuite extends FunSuite {

  trait mergeSort {

    val maxDepth = 7
    val xs = new Array[Int](1024 * 1024)
    def initialize(xs: Array[Int]) {
      var i = xs.length
      while (i != 0) {
        i -= 1
        xs(i) = Random.nextInt()
      }
    }

    initialize(xs)

    val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 60,
    Key.exec.benchRuns -> 60,
    Key.verbose -> true
    ) withWarmer new Warmer.Default


    val mergeSort = new MergeSort
  }

  test("Unit test sort") {
    new mergeSort {
      mergeSort.quickSort(xs, 0, xs.length)
      assert(xs(0) == xs.min)
      assert(xs(xs.length-1) == xs.max)
    }
  }

  test("Unit test parallel sort") {
    new mergeSort {
      mergeSort.parallelMergeSort(xs, maxDepth)
      assert(xs(0) == xs.min)
      assert(xs(xs.length-1) == xs.max)
    }
  }

  test("Merge sort measure sequential and parallel") {
    new mergeSort {
      val seqtime = standardConfig setUp {
        _ => initialize(xs)
      } measure {
        mergeSort.quickSort(xs, 0, xs.length)
      }
      println(s"sequential sum time: $seqtime ms")

      val partime = standardConfig setUp {
        _ => initialize(xs)
      } measure {
        mergeSort.parallelMergeSort(xs, maxDepth)
      }

      val speedup = seqtime.value / partime.value
      println(s"fork/join time: $partime ms")
      println(s"speedup: {$speedup}")
    }
  }

}

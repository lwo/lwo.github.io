package c3.w2

import java.util
import common._


/**
  * MergeSort
  */
class MergeSort {

  // Auxiliary storage
  @volatile var auxilary:Array[Int] = null

  def quickSort(xs: Array[Int], offset: Int, length: Int): Unit = {
    util.Arrays.sort(xs, offset, length)
  }

  def parallelMergeSort(xs: Array[Int], maxDepth: Int): Unit = {

    val ys: Array[Int] = new Array[Int](xs.length)
    //auxilary = ys // global

    def merge(src: Array[Int], dst: Array[Int], from: Int, mid: Int, until: Int) {
      var left = from
      var right = mid
      var i = from
      while (left < mid && right < until) {
        while (left < mid && src(left) <= src(right)) {
          dst(i) = src(left)
          i += 1
          left += 1
        }
        while (right < until && src(right) <= src(left)) {
          dst(i) = src(right)
          i += 1
          right += 1
        }
      }
      while (left < mid) {
        dst(i) = src(left)
        i += 1
        left += 1
      }
      while (right < until) {
        dst(i) = src(right)
        i += 1
        right += 1
      }
    }


    def sort(from: Int, until: Int, depth: Int): Unit = {
      if (depth == maxDepth) {
        quickSort(xs, from, until - from)
      } else {
        val mid = (from + until) / 2
        parallel(sort(from, mid, depth + 1), sort(mid, until, depth + 1))
        //       |3|10|7|.|.|.|.|.|            |1|4|8|.|.|.|.|.|

        val flip = (maxDepth - depth) % 2 == 0
        val src = if (flip) ys else xs
        val dst = if (flip) xs else ys
        merge(src, dst, from, mid, until)
      }
    }

    def copy(src: Array[Int], target: Array[Int], from: Int, until: Int, depth: Int): Unit = {
      if (depth == maxDepth) {
        Array.copy(src, from, target, from, until - from)
      } else {
        val mid = from + ((until - from) / 2)
        parallel(copy(src, target, from, mid, depth + 1), copy(src, target, mid, until, depth + 1))
      }
    }

    sort(0, xs.length, 0)
  }

}

package c3.w2

import java.nio.file.Paths

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class LeftScanElementsSuite extends FunSuite {

  trait config {
    val leftScan = new LeftScanElements
    val in = Array(2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
      31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
      73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
      127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
      179, 181, 191, 193, 197, 199, 211, 223, 227, 229,
      233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
      283, 293, 307, 311, 313, 317, 331, 337, 347, 349,
      353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
      419, 421, 431, 433, 439, 443, 449, 457, 461, 463,
      467, 479, 487, 491, 499, 503, 509, 521, 523, 541)

    val from = 0
    val to: Int = in.length
    val out: Array[Int] = Array.fill(in.length + 1)(Int.MinValue)
    val a0 = 100

    def f(x: Int, y: Int): Int = x + y

    val expect: Array[Int] = (in scanLeft a0) (f)
  }

  test("sequential scanLeft with array") {
    new config {
      leftScan.scanLeftSequential(in, from, to, a0, f, out)
      assert(expect sameElements out)
    }
  }

  test("parallel scanLeft with array") {
    new config {
      leftScan.scanLeftParallel(in, a0, f, out)
      assert(expect sameElements out)
    }
  }

}
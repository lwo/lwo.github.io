package c2

import c2.w1.Generator
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.util.Random

@RunWith(classOf[JUnitRunner])
class GeneratorSuite extends FunSuite {

  trait random {
    self =>
    val integers = new Generator[Int] {
      def generate: Int = scala.util.Random.nextInt()
    }
    val booleans: Generator[Boolean] = for (i <- integers) yield i > 0

    def pairs[T, U](t: Generator[T], u: Generator[U]): Generator[(T, U)] = for {
      x <- t // invokes flatMap
      y <- u // map
    } yield (x, y)
  }

  test("pairs") {
    new random {
      val result: Generator[(Int, Boolean)] = pairs(integers, booleans)
      result foreach println
    }
  }

  test("choose") {
    def choose(from: Int, to: Int) = new Generator[Int] {
      def generate: Int = if (from == to) from else Random.nextInt(to - from) + from
    }

    def oneOf[T](xs: T*): Generator[T] = {
      for (idx <- choose(0, xs.length)) yield xs(idx)
    }

    val choice = oneOf("red", "Green", "Blue")
    choice foreach println
  }


  test("trees") {

    trait Tree {
      override def toString: String = {
        def show(tree: Tree): String = tree match {
          case Leaf(i) => i.toString
          case Inner(left, right) => "(" + show(left) + "," + show(right) + ")"
        }

        show(this)
      }
    }
    case class Inner(left: Tree, right: Tree) extends Tree
    case class Leaf(x: Int) extends Tree

    new random {
      def leafs: Generator[Leaf] = for {
        x <- integers
      } yield Leaf(x)

      def inners: Generator[Inner] = for {
        l <- trees
        r <- trees
      } yield Inner(l, r)

      def trees: Generator[Tree] = for {
        cutoff <- booleans
        tree <- if (cutoff) leafs else inners
      } yield tree

      val result = trees.generate
      println(result)
    }
  }


  test("Test with random input for the post condition") {

    new random {

      def emptyList: Generator[List[Int]] = new Generator[List[Int]] {
        override def generate: List[Int] = Nil
      }

      def nonEmptyLists: Generator[List[Int]] = for {
        head <- integers
        tail <- lists
      } yield head :: tail

      def lists: Generator[List[Int]] = for {
        cutoff <- booleans
        list <- if (cutoff) emptyList else nonEmptyLists
      } yield list




      def testPairs[T](r: Generator[T], noTimes: Int = 100)(test: T => Boolean) {
        for (_ <- 0 until noTimes) {
          val value = r.generate
          assert(test(value), "Test failed for: " + value)
        }
        println("Test passed " + noTimes + " times")
      }

      testPairs(pairs(lists, lists)) {
        case (xs, ys) => (xs ::: ys).length > xs.length
      }
    }
  }

}

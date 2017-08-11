package c2.w1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class QueryDataStructureSuite extends FunSuite {

  trait Database {
    val database: Set[Book] = Set(
      Book(title = "Structure and Interpretation of Computer Programs",
        authors = List("Abelson, Harald", "Sussman, Gerald J.")),
      Book(title = "Introduction to Functional Programming",
        authors = List("Bird, Richard", "Wadler, Phil")),
      Book(title = "Effective Java",
        authors = List("Bloch, Joshua")),
      Book(title = "Effective Java 2",
        authors = List("Bloch, Joshua")),
      Book(title = "Java Puzzlers",
        authors = List("Bloch, Joshua", "Gafter, Neal")),
      Book(title = "Programming in Scala",
        authors = List("Ordersky, Martin", "Spoon, Lex", "Venners, Bill"))
    )
  }

  test("Bloch in authors") {
    new Database {
      val result: Set[String] = for {
        b <- database
        a <- b.authors if a startsWith "Bloch"
      } yield b.title

      println(result)
      assert(result === List("Effective Java", "Effective Java 2", "Java Puzzlers"))
    }
  }

  test("Program in title") {
    new Database {
      val result: Set[String] = for {
        b <- database
        a <- b.title if (b.title indexOf "Program") != -1
      } yield b.title

      println(result)
      assert(result === List("Effective Java", "Effective Java 2", "Java Puzzlers"))
    }
  }

  test("Authors who wrote at least two books") {
    new Database {
      val result: Set[String] = for {
        b1 <- database
        b2 <- database
        if b1.title < b2.title //  0.5N^2 - N
        a1 <- b1.authors
        a2 <- b2.authors
        if a1 == a2
      } yield a1
      assert(result === Set("Bloch", "Joshua"))
    }
  }

}

package c1.w6

object numberOfQueens extends App {

  def queens(n: Int): Set[List[Int]] = {
    def placeQueen(row: Int): Set[List[Int]] = {
      if (row == 0) Set(Nil)
      else {
        for {
          candidate <- placeQueen(row - 1) // create all rows N
          col <- 0 until n // for each column
          if isSafe(row, col, candidate)
        } yield col :: candidate
      }
    }

    placeQueen(n)
  }

  def isSafe(cr: Int, col: Int, queens: List[Int]): Boolean = {
    val row = queens.length
    val queensWithRow = (row - 1 to 0 by -1) zip queens
    val b = queensWithRow forall {
      case (r, c) => col != c && math.abs(col - c) != row - r
    }
    println(s"row=$cr col=$col isSafe=$b queens=$queens")
    b
  }

  def show(queens: List[Int]): String = {
    val lines = for (col <- queens.reverse)
      yield Vector.fill(queens.length)("*").updated(col, "X ").mkString

    "\n" + (lines mkString "\n")
  }

  queens(4) foreach (q => println(show(q)))
}
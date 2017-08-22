package c3.w3

import common._

trait Splitter[A] extends Iterator[A] {
  def split: Seq[Splitter[A]]
  def remaining: Int
  val threshold = 200

  def fold(z: A)(f: (A, A) => A): A = {
    if(remaining < threshold) foldLeft(z)(f)
    else {
      val children = for {
        child <- split
      } yield task { child.fold(z)(f) }

      children.map(_.join()).foldLeft(z)(f)
    }
  }
}
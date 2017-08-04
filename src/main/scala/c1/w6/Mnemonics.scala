package c1.w6

import scala.io.{BufferedSource, Source}

class Mnemonics {
  val in: BufferedSource = Source.fromResource("linuxwords.txt")
  val words: Stream[String] = in.getLines.toStream filter (word => word forall (chr => chr.isLetter))

  // Mnemonic map
  val mnemonicMap = Map(
    '2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
    '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ"
  )

  var char2Code: Map[Char, Char] = {
    for {
      (digit, str) <- mnemonicMap
      abc <- str
    } yield abc -> digit
  }

  def word2Code(word: String): String = word.toUpperCase map char2Code

  val wordsForNum: Map[String, Seq[String]] = {
    words groupBy word2Code withDefaultValue Seq()
  }

  def encode(number: String): Set[List[String]] = {
    if (number.isEmpty) Set(List())
    else {
      for {
        split <- 1 to number.length
        word <- wordsForNum(number take split)
        rest <- encode(number drop split)
      } yield word :: rest
    }.toSet
  }

  def translate(number: String): Set[String] = {
    encode(number) map (_ mkString " ")
  }
}
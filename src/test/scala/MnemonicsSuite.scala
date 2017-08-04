import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import c1.w6.Mnemonics

@RunWith(classOf[JUnitRunner])
class MnemonicsSuite extends FunSuite {

  test("word2Code") {
    val mnemonics = new Mnemonics
    assert(mnemonics.word2Code("java") == "5282")
  }

  test("wordsForNum") {
    val mnemonics = new Mnemonics
    val words = mnemonics.wordsForNum("5282").toList
    assert(words == List("Java", "lava"))
  }

  test("words for 7225247386") {
    val mnemonics = new Mnemonics
    val words = mnemonics.translate("7225247386")
    assert(words("Scala is fun"))
  }
}
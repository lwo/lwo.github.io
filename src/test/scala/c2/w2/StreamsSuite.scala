package c2.w2

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StreamsSuite extends FunSuite {

  trait s {
    def streams = new Streams
  }

  test("streams") {
    new s {
      private val listRange= streams.listRange(1, 10) take 3
      private val streamRange = streams.streamRange(1, 10) take 3
      assert(listRange == streamRange.toList)
    }
  }

  test("Square root") {
    new s {
      private val result = streams.sqrtStream(24.9)
      assert( result > 4.9 && result < 25)
    }
  }

}

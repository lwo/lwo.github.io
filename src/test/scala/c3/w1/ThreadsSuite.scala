package c3.w1

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ThreadsSuite extends FunSuite {

  trait threads {
    val test = new Threads
  }

  test("threads without monitor") {
    new threads {
      val t1 = test.noMonitor.startThreadSync
      val t2 = test.noMonitor.startThreadSync
      t1.start()
      t2.start()
      t1.join()
      t2.join()
    }
  }

  test("threads with monitor") {
    new threads {
      val t1 = test.withMonitor.startThreadSync
      val t2 = test.withMonitor.startThreadSync
      t1.start()
      t2.start()
      t1.join()
      t2.join()
    }
  }

}
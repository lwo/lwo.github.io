package c3.w1

/**
  * Threads with atomic methods use a single-ownership over a monitor to shared resources.
  */
class Threads {

  object noMonitor {

    private var uidCountSync = 0L

    private def getSharedMethodID: Long = {
      uidCountSync = uidCountSync + 1
      uidCountSync
    }

    def startThreadSync = {
      new Thread {
        override def run() {
          val uids = for (i <- 0 until 10) yield getSharedMethodID
          println(uids)
        }
      }
    }
  }

  object withMonitor {

    private val monitor = new AnyRef {}

    private var uidCountSync = 0L

    private def getSharedMethodID: Long = monitor.synchronized {
      uidCountSync = uidCountSync + 1
      uidCountSync
    }

    def startThreadSync = {
      new Thread {
        override def run() {
          val uids = for (i <- 0 until 10) yield getSharedMethodID
          println(uids)
        }
      }
    }
  }

}
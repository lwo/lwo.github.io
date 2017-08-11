package c3.w1

/**
  * Account
  *
  * Uses three monitors for the account and for the transfer from and to the source and target account.
  * Prevent deadlock by simply ordering the resource.
  *
  * @param amount The amount of value.
  */
class Account(private var amount: Int = 0) {
  val monitor = new AnyRef {}

  def getAmount(): Unit = println(this.amount)
  def getUniqueId: Long = monitor.synchronized {
    global.uidCount = global.uidCount+1
    global.uidCount
  }
  val uid: Long = getUniqueId

  private def lockAndTransfer(target: Account, n: Int) =
    this.synchronized {
      target.synchronized {
        this.amount -= n
        target.amount += n
      }
    }

  def transferDeadlock(target: Account, n: Int): Unit =
    this.synchronized { // Invoke monitor on this class instance
      target.synchronized { // invoke second monitor on target instance
        this.amount -= n
        target.amount += n
      }
    }
  def transfer(target: Account, n: Int): Unit =
    if(this.uid < target.uid) this.lockAndTransfer(target, n)
    else target.lockAndTransfer(this, -n) // garantees to wait on the release by a former thread.

}

object global {
  var uidCount:Long = 0
}
package c2.w4.frp

/**
  * BankAccountPublisher
  *
  */
class BankAccountPublisher extends Publisher {
  private var balance = 0
  
  def currentBalance: Int = balance  // manifest current view.
  
  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
    publish() // Notify the subscribers
  }
  
  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      publish()        // Notify the subscribers
    } else throw new Error("insufficient funds")
  }
}

/**
  * Consolidator
  *
  * A subscriber to BankAccountPublisher
  *
  * @param observed The subjects or subscribed to entity.
  */
class Consolidator(observed: List[BankAccountPublisher]) extends Subscriber {
  observed.foreach(_.subscribe(this))
  
  private var total: Int = _
  compute()
  
  private def compute() = {
    total = observed.map(_.currentBalance).sum
  }
  def handler(pub: Publisher): Unit = compute()
  def totalBalance: Int = total
}
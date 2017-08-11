package c2.w4.frp

/**
  * BankAccountSignal
  */
class BankAccountSignal {
  
  val balance = Var(0)
  
  def deposit(amount: Int): Unit = {
    if (amount > 0) {
      val _balance = balance()
      balance() = _balance + amount // need var _balance to prevent a cyclical call.
    }
  }
  
  def withdraw(amount: Int): Unit = {
    if (0 < amount && amount <= balance()) {
      val _balance = balance()
      balance() = _balance - amount
    } else throw new Error("insufficient funds")
  }

}
package c2.w3

/**
  * BankAccount
  *
  * The object has state, as balance has a history.
  * Hence results can differ, with the same input.
  */
class BankAccount {
  private var balance = 0

  def deposit(amount: Int): Int = {
    if (amount > 0) balance = balance + amount
    balance
  }

  def withdraw(amount: Int): Int = {
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      balance
    } else throw new Error("insufficient funds")
  }

  override def toString: String = {
    "Your balance is $" + balance
  }

}
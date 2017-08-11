package c2.w4.frp

import scala.util.DynamicVariable

/**
  * Signal
  *
  * @param expr the body that obtains and updates the value and notifies its subscribers.
  * @tparam T The expression ( function or constants ) that gives a value.
  */
class Signal[T](expr: => T) {

  import Signal._

  private var myExpr: () => T = _ // to be initialized by update
  private var myValue: T = _
  private var observers: Set[Signal[_]] = Set()
  update(expr)

  protected def update(expr: => T): Unit = {
    myExpr = () => expr
    computeValue()
  }

  protected def computeValue(): Unit = {
    val newValue = caller.withValue(this)(myExpr())
    if (myValue != newValue) {
      myValue = newValue
      val obs = observers
      observers = Set()
      println("Observers: ")
      obs foreach println
      obs.foreach(_.computeValue())
    }
  }

  /**
    * apply
    *
    * @return The current value of the signal.
    */
  def apply(): T = {
    observers += caller.value
    assert(!caller.value.observers.contains(this), "cyclic signal definition")
    myValue
  }
}

object NoSignal extends Signal[Nothing](???) {
  override def computeValue(): Unit = () // no signal
}

object Signal {

  // Avoid race conditions with DynamicVariable
    private val caller = new StackableVariable[Signal[_]](NoSignal)
//  private val caller = new DynamicVariable[Signal[_]](NoSignal)

  /**
    * apply
    *
    * Creates a signal
    *
    * @param expr the function or constant that returns the value
    * @tparam T Signal type
    * @return A Signal
    */
  def apply[T](expr: => T) = new Signal(expr)
}

/**
  * Var
  *
  * A signal that allows its value to be updated.
  *
  * @param expr the function or constant that returns the value
  * @tparam T The value.
  */
class Var[T](expr: => T) extends Signal[T](expr) {
  override def update(expr: => T): Unit = super.update(expr)
}

object Var {
  def apply[T](expr: => T) = new Var(expr)
}

/**
  * StackableVariable
  *
  * List of signals
  *
  * @param init
  * @tparam T
  */
class StackableVariable[T](init: T) {
  private var signals: List[T] = List(init)

  def value: T = signals.head

  def withValue[R](newValue: T)(op: => R): R = {
    signals = newValue :: signals
    try op finally signals = signals.tail
  }
}
package c2.w4.frp

/**
  * Publisher
  *
  * Holds a set of subscribers and notifies them with a pubish method
  */
trait Publisher {
  private var subscribers: Set[Subscriber] = Set()

  def subscribe(subscriber: Subscriber): Unit = {
    subscribers += subscriber
  }

  def unsubscribe(subscriber: Subscriber): Unit = {
    subscribers -= subscriber
  }

  def publish(): Unit = {
    subscribers.foreach(_.handler(this))
  }
}

/**
  * Subscriber
  *
  * Is invoked with a handler by a publisher.
  */
trait Subscriber {
  def handler(pub: Publisher)
}
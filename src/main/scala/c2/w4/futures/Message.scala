package c2.w4.futures

class Message(val from: String, val to: String){}

object Message {
  def apply(from: String, to: String): Message = {
    new Message(from, to)
  }
}

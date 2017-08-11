package c2.w4.futures
import scala.concurrent.Future


trait Request {}

object Request {
  def apply(bytes: Array[Byte]): Request = ???
}

trait Response {
  def isOK: Boolean

  def body: Array[Byte]
}
object Response {
  def apply(bytes: Array[Byte]): Response = ???
}

object Http {
  def apply(url: String, request: Request): Future[Response] = ???
}


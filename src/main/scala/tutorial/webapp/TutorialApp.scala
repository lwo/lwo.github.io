package tutorial.webapp

import org.scalajs.dom
import dom.{CanvasRenderingContext2D, document, html}
import Math._

import org.scalajs.dom.html.Canvas


object TutorialApp {

  def getElem[T](id: String): T = document.getElementById(id).asInstanceOf[T]

  val canvas: Canvas = getElem[html.Canvas]("canvas")
  val draw: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  def polygon(x: Double ): Double = pow(x, 2) - 2*x + 1D

  def main(args: Array[String]): Unit = {
    val (h, w) = (canvas.height, canvas.width)
    var x = 0.0
    val rr = 1000
    val graphs = Seq[(String, Double => Double)](
      ("red", sin),
      ("black", polygon),
      ("green", x => 2 - abs(x % 8 - 4)),
      ("blue", x => 3 * pow(sin(x / 12), 2) * sin(x))
    ).zipWithIndex
    dom.window.setInterval(() => {
      x = (x + 1) % w
      if (x == 0) draw.clearRect(0, 0, w, h)
      else for (((color, func), i) <- graphs) {
        val y = func(x/w * 75) * h/40 + h/graphs.size * (i+0.5)
        draw.fillStyle = color
        draw.fillRect(x, y, 1, 1)
      }
    }, 10)
  }
}




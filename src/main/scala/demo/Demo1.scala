package demo

import java.lang.Math._

import org.scalajs.dom
import org.scalajs.dom.{CanvasRenderingContext2D, html}
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Demo1")
object Demo1 {

  @JSExport
  def main(canvas: html.Canvas): Unit = {
    val draw: CanvasRenderingContext2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val (h, w) = (canvas.height, canvas.width)
    var x = 0.0
    var xx = x
    var inc = 1
    val graphs = Seq[(String, Double => Double)](
      ("red", sin),
      ("green", x => 2 - abs(x % 8 - 4)),
      ("blue", x => 3 * pow(sin(x / 12), 2) * sin(x))
    ).zipWithIndex
    dom.window.setInterval(() => {
      xx = xx + inc
      x = (x + 1) % w
      if (x == 0) {
        draw.clearRect(0, 0, w, h)
        inc = -inc
      }
      else for (((color, func), i) <- graphs) {
        val y = func(xx/w * 75) * h/40 + h/graphs.size * (i+0.5)
        draw.fillStyle = color
        draw.fillRect(xx, y, 1, 1)
      }
    }, 10)
  }
}




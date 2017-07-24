package demo

import java.lang.Math._

import org.scalajs.dom
import org.scalajs.dom.html
import paperjs.Basic.Point
import paperjs.Paper
import paperjs.Paths.Path
import paperjs.Styling.Color
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

@JSExportTopLevel("Demo2")
object Demo2 {

  @JSExport
  def main(canvas: html.Canvas): Unit = {

    Paper.setup(canvas)
    val (h, w) = (canvas.height, canvas.width)
    var x = 0.0
    var xx = x
    var inc = 1

    val graphs = Seq[(String, Double => Double, Path)](
      ("red", sin, new Path()),
      ("green", x => 2 - abs(x % 8 - 4), new Path()),
      ("blue", x => 3 * pow(sin(x / 12), 2) * sin(x), new Path())
    ).zipWithIndex
    dom.window.setInterval(() => {
      xx = xx + inc
      x = (x + 1) % w
      if (x == 0) {
        for (((color, func, path), i) <- graphs) {
          path.removeSegments(0, path.segments.length)
        }
        inc = -inc
      }
      else for (((color, func, path), i) <- graphs) {
        val y = func(xx / w * 75) * h / 40 + h / graphs.size * (i + 0.5)
        path.strokeColor = Color(color)
        path.add(new Point(xx, y))
        path.smooth()
      }
    }, 10)

  }
}




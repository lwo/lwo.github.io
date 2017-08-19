package c3.w1

import org.scalameter._

class BenchMarkDemo {

  private def n: Int = Int.MaxValue

  private val standardConfig = config(
    Key.exec.minWarmupRuns -> 20,
    Key.exec.maxWarmupRuns -> 40,
    Key.exec.benchRuns -> 20,
    Key.verbose -> true
  ) withWarmer new Warmer.Default


  object measurements {

    def withDefaultWarmer(runs: Int): Double = {
      (for (i <- 1 to runs) yield {
        withWarmer(new Warmer.Default) measure {
          (0 until n).toArray
        }
      }.value).sum / runs
    }

    def withConfigWarmer(runs: Int): Double = {

      (for (i <- 1 to runs) yield {
        standardConfig measure {
          (0 until n).toArray
        }
      }.value).sum / runs
    }

    def withMemoryFootprint(runs: Int): Double = {
      (for (i <- 1 to runs) yield {
        withMeasurer(new Measurer.MemoryFootprint) measure {
          (0 until n).toArray
        }
      }.value).sum / runs
    }

  }

}
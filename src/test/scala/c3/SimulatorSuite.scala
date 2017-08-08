package c3

import c2.w3.{Circuits, Gates, Parameters}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SimulatorSuite extends FunSuite {

  test("Run a simulation") {

    object sim extends Circuits with Parameters

    val in1, in2, sum, carry = new sim.Wire

    sim.halfAdder(in1, in2, sum, carry)
    sim.probe("sum", sum)
    sim.probe("carry", carry)

    in1 setSignal true
    sim.run()

    in2 setSignal true
    sim.run()

    in1 setSignal false
    sim.run()
  }

}
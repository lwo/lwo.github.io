package c1.w6

class Polynome(terms0: Map[Int, Double]) {
  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms: Map[Int, Double] = terms0 withDefaultValue 0D

  def + (other: Polynome) = new Polynome(terms ++ (other.terms map adjustBetter))
  def foldLeft (other: Polynome) = new Polynome((other.terms foldLeft terms)(addTerm))

  def addTerm(terms: Map[Int, Double], term: (Int, Double)): Map[Int, Double] = {
    val (exp, coeff) = term
    terms + (exp -> (coeff + terms(exp)))
  }

  def adjustOk(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
    terms get exp match {
      case Some(coeff1) => exp -> (coeff + coeff1)
      case None => exp -> coeff
    }
  }

  def adjustBetter(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
    exp -> (coeff + terms(exp))
  }

  override def toString: String = (
    for( (exp, coeff) <- terms.toList.sorted.reverse)
      yield coeff + "x^" + exp
    ) mkString " + "
}

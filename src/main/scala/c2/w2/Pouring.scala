package c2.w2

import scala.collection.immutable

/**
  * Pouring
  *
  * @param capacity each vector is a glass with the value of the vector being the number of units.
  */
class Pouring(capacity: Vector[Int]) {

  val glasses: Range = capacity.indices
  type State = Vector[Int]

  trait Move {
    def change(state: State): State
  }

  case class Empty(glass: Int) extends Move {
    def change(state: State): State = state updated(glass, 0)
  }

  case class Fill(glass: Int) extends Move {
    def change(state: State): State = state updated(glass, capacity(glass))
  }

  case class Pour(from: Int, to: Int) extends Move {
    def change(state: State): State = {
      val amount = state(from) min (capacity(to) - state(to))
      state updated(from, state(from) - amount) updated(to, state(to) + amount)
    }
  }

  val moves: immutable.IndexedSeq[Move with Product with Serializable] =
    (for (g <- glasses) yield Empty(g)) ++
      (for (g <- glasses) yield Fill(g)) ++
      (for (
        from <- glasses; to <- glasses if from != to) yield Pour(from, to))

  class Path(history: List[Move], val endState: State) {
    def extend(move: Move) = new Path(move :: history, move change endState)

    override def toString: String = (history.reverse mkString " ") + "--> " + endState
  }

  def from(paths: Set[Path], explored: Set[State]): Stream[Set[Path]] = {
    if (paths.isEmpty) Stream.empty
    else {
      val more = for {
        path <- paths
        next <- moves map path.extend
        if !(explored contains next.endState)
      } yield next
      paths #:: from(more, explored ++ (more map (_.endState)))
    }
  }

  val initialState: Vector[Int] = capacity map (x => 0)
  val initialPathOptimized = new Path(Nil, initialState)
  val pathSets: Stream[Set[Path]] = from(Set(initialPathOptimized), Set(initialState))

  def solution(target: Int): Stream[Path] = {
    for {
      pathSet <- pathSets
      path <- pathSet
      if path.endState contains target
    } yield path
  }

}
package c2.w2

import scala.collection.immutable

/**
  * Pouring
  *
  * @param capacity each vector is a glass with the value of the vector being the number of units.
  */
class Pouring(capacity: Vector[Int]) {


  val initialState: Vector[Int] = capacity map (x => 0)
  val initialPath = new Path(Nil)
  val initialPathOptimized = new PathOptimized(Nil, initialState)
  
  val glasses: Range = capacity.indices
  
  val moves: immutable.IndexedSeq[Move with Product with Serializable] =
    (for (g <- glasses) yield Empty(g)) ++
    (for (g <- glasses) yield Fill(g)) ++
    (for (from <- glasses; to <- glasses if from != to) yield Pour(from, to))
  
  
 def fromInefficient(paths: Set[Path]): Stream[Set[Path]] = {
   if(paths.isEmpty) Stream.empty
   else {
      val more = for {
        path <- paths
        next <- moves map path.extend
      } yield next
      paths #:: fromInefficient(more)
    }
  }

  def from(paths: Set[PathOptimized], explored: Set[State]): Stream[Set[PathOptimized]] = {
    if(paths.isEmpty) Stream.empty
    else {
      val more = for {
        path <- paths
        next <- moves map path.extend
        if !(explored contains next.endState)
      } yield next
      paths #:: from(more, explored ++ (more map (_.endState)))
    }
  }   
    
  val pathSetsInefficient: Stream[Set[Path]] = fromInefficient(Set(initialPath))
  val pathSets: Stream[Set[PathOptimized]] = from(Set(initialPathOptimized), Set(initialState))

  def solution(target: Int): Stream[PathOptimized] = {
    for {
      pathSet <- pathSets
      path <- pathSet
      if path.endState contains target
    } yield path
  }

  type State = Vector[Int]


  trait Move {
    def change(state: State): State
  }
  case class Empty(glass: Int) extends Move {
    def change(state: State): State = state updated (glass, 0) 
  }
  case class Fill(glass: Int) extends Move {
    def change(state: State): State = state updated (glass, capacity(glass)) 
  }
  case class Pour(from: Int, to: Int) extends Move {
    def change(state: State): State = {
      val amount = state(from) min (capacity(to) - state(to))
      state updated (from, state(from) - amount) updated (to, state(to) + amount)
    }
  }
   
  class Path(history: List[Move]) {
    def endState: State = (history foldRight initialState)(_ change _)
    def endState2: State = trackState(history)
    
    private def trackState(xs: List[Move]): State = xs match {
      case Nil => initialState
      case move :: xs1 => move change trackState(xs1)
    }
    
    def extend(move: Move) = new Path(move :: history)
    override def toString: String = (history.reverse mkString " ") + "--> " + endState
  }
  
  class PathOptimized(history: List[Move], val endState: State) {
    def extend(move: Move) = new PathOptimized(move :: history, move change endState)
    override def toString: String = (history.reverse mkString " ") + "--> " + endState
  }
  
}
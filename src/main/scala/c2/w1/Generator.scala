package c2.w1

trait Generator[+T] {
  self =>

  def generate: T // the random bit
  def foreach[U](f: T => U) {
    f(generate)
  }

  def map[S](f: T => S): Generator[S] = new Generator[S] { // new instance, hence use the self alias rather than this
    def generate: S = f(self.generate)
  }

  def flatMap[S](f: T => Generator[S]): Generator[S] = map(f).generate

}

object Generator {
  def apply[T](x:T):Generator[T] = new Generator[T] {
    def generate: T = x
  }
}

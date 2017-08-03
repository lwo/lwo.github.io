package c1.w2

object Currying extends App {
  def sumIntsNaive(a: Int, b: Int): Int = if (a > b) 0 else a + sumInts(a + 1, b)

  def sumCubesNaive(a: Int, b: Int): Int =
    if (a > b) 0 else cube(a) + sumCubes(a + 1, b)

  def sumInts(a: Int, b: Int): Int = sum(id, a, b)

  def sumCubes(a: Int, b: Int): Int = sum(cube, a, b)

  def sumFactorials(a: Int, b: Int): Int = sum(fact, a, b)

  def id(x: Int): Int = x

  def cube(x: Int): Int = x * x * x

  def fact(x: Int): Int = if (x == 0) 1 else x * fact(x - 1)

  def sum(f: Int => Int, a: Int, b: Int): Int = {
    if (a > b) 0
    else f(a) + sum(f, a + 1, b)
  }

  val anonymous: (Int) => Int = (x: Int) => x * x * x

  def sumIntsAnon(a: Int, b: Int): Int = sum(x => x, a, b)

  def sumCubesAnon(a: Int, b: Int): Int = sum(x => x * x * x, a, b)


  def sum2(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }

    loop(a, 0)
  }

  sum(id, 1, 4)
  sum2(id, 1, 4)
  sum(id, 13, 40)
  sum2(id, 13, 40)

  def sum3(f: Int => Int): (Int, Int) => Int = {
    def sumF(a: Int, b: Int): Int = {
      if (a > b) 0
      else f(a) + sumF(a + 1, b)
    }

    sumF
  }

  def fact3: Int => Int = {
    def factF(x: Int): Int = {
      if (x == 0) 1
      else x * factF(x)
    }

    factF
  }

  def sumInts3: (Int, Int) => Int = sum3(x => x)

  def sumCubes3: (Int, Int) => Int = sum3(x => x * x * x)

  def sumFactorials3: (Int, Int) => Int = sum3(fact3)

  sum3(cube)(1, 10)
  sumCubes3(1, 10)


  def sum4(f: Int => Int)(a: Int, b: Int): Int =
    if (a > b) 0
    else f(a) + sum4(f)(a + 1, b)

  def sumOfCubes4(a: Int, b: Int): Int = sum4(cube)(a, b)

  def product(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 1
    else f(a) * product(f)(a + 1, b)
  }

  def factFromProduct(n: Int): Int = product(id)(1, n)

  def binOperation(f: Int => Int)(g: (Int, Int) => Int, z: Int)(a: Int, b: Int): Int = {
    if (a > b) z
    else g(f(a), binOperation(f)(g, z)(a + 1, b))
  }

  def sum5(f: Int => Int)(a: Int, b: Int): Int = binOperation(f)((x, y) => x + y, 0)(a, b)

  def product2(f: Int => Int)(a: Int, b: Int): Int = binOperation(f)((x, y) => x * y, 1)(a, b)

  factFromProduct(9)
  fact(9)
  sum(cube, 2, 9)
  sum2(cube, 2, 9)
  sum3(cube)(2, 9)
  sum4(cube)(2, 9)
  sum5(cube)(2, 9)
  product(cube)(2, 9)
  product2(cube)(2, 9)
}
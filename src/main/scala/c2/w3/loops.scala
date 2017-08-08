package c2.w3

object loops {

  /**
    * WHILE
    * A while-true loop
    * while (condition) {command}
    *
    * @param condition the condition
    * @param command   a command
    */
  def WHILE(condition: => Boolean)(command: => Unit): Unit = {
    if (condition) {
      command
      WHILE(condition)(command)
    }
    else ()
  }

  /**
    * REPEAT
    * A repeat-while-true loop
    * {command} while (condition)
    *
    * @param command   the condition
    * @param condition a command
    */
  def REPEAT(command: => Unit)(condition: => Boolean): Unit = {
    command
    if (condition) ()
    else REPEAT(command)(condition)
  }

  /**
    * DO
    * A do-while-true loop
    * do
    *
    * @param command a command
    */
  class DO(command: => Unit) {
    def WHILE(condition: => Boolean): Unit = {
      command
      if (condition) ()
      else DO {
        command
      } WHILE condition
    }
  }

  object DO {
    def apply(command: => Unit) = new DO(command)
  }

}
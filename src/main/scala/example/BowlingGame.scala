package example

object BowlingGame {

  /**
   * Bowling Score calculator
   *
   * Strike: The score for the strike frame = 10 plus the total of the next TWO BALLS THROWN plus the previous frame score
   * Spare:  Spares are almost the same as strikes except they are worth 10 points plus the value of the next ball thrown
   *
   * @param pins list of knocked pins [0..10]
   * @return game score [0..300]
   */
  def score(pins: List[Int]): Int = {

    @annotation.tailrec
    def go(prev: List[Int], ps: List[Int], frame: Int, score: Int, roll: Int): Int = ps match {
      // got strike
      case 10 :: a :: b :: xs                              =>
//        println(s"STRIKE: ps: $ps, frame: $frame, score: $score, roll: $roll")
        val frameScore = 10 + a + b
        if (frame == 9) {
          go(a :: b :: prev, Nil, frame + 1, score + frameScore, roll)
        } else {
          go(a :: b :: prev, ps.tail, frame + 1, score + frameScore, roll)
        }
      // got spare
      case a :: b :: c :: xs if (a + b) == 10 && roll == 0 =>
//        println(s"SPARE: ps: $ps, frame: $frame, score: $score, roll: $roll")
        val frameScore = 10 + c
        if (frame == 9) {
          go(a :: b :: prev, Nil, frame + 2, score + frameScore, roll)
        } else {
          go(a :: b :: prev, c :: xs, frame + 2, score + frameScore, roll)
        }
      // normal
      case x :: xs                                         =>
//        println(s"NORML: ps: $ps, frame: $frame, score: $score, roll: $roll")
        val nextRoll  = if (roll == 0) 1 else 0
        val nextFrame = frame + (if (roll == 0) 0 else 1)
        go(x :: prev, xs, nextFrame, score + x, nextRoll)
      case Nil                                             =>
        score
    }

    go(
      prev = Nil,
      ps = pins,
      frame = 0,
      score = 0,
      roll = 0
    )
  }

}

package example

import org.scalatest._
import example.ModelSimple.Game
import example.ModelSimple.GamePlayer

class SimpleGameSpec extends funsuite.AnyFunSuite with matchers.must.Matchers {

  sealed trait FrameState extends Product with Serializable
  case object Normal      extends FrameState
  case object Strike      extends FrameState
  case object Spare       extends FrameState

  case class Row(frame: Int, ball: Int, score: Int, pins: Int, state: FrameState)

  // 4-balls window
  //

  case class TestGame(
      frame: Int,
      ball: Int,
      score: Int,
      private val log: List[Row] = Nil
  ) extends Game {

    def run(pins: Int) = {
      println(log.mkString(", "))

      val prevRow        = log.headOption
      val prevFrameScore = prevRow.map(_.score).getOrElse(0)
      val prevPins       = prevRow.map(_.pins).getOrElse(0)
      val prevState      = prevRow.map(_.state).getOrElse(Normal)

      def isStrike(pins: Int): Boolean = ball == 0 && pins == 10
      def isSpare(pins: Int): Boolean  = ball == 1 && (pins + prevPins) == 10

      prevState match {
        case Normal =>
          val r = Row(
            frame + (if (ball == 1) 1 else 0),
            if (ball == 0) 1 else 0,
            score + pins,
            pins,
            Normal
          )
          (r, r :: log)
      }

      val (row, newLog) = {
        if (isStrike(pins)) {
          val r = Row(
            frame + 1,
            0,
            score + pins,
            pins, // 10
            Strike
          )
          (r, r :: log)
        } else if (isSpare(pins)) {
          val r = Row(
            frame + (if (ball == 1) 1 else 0),
            if (ball == 0) 1 else 0,
            score + pins,
            pins, // pins + prevPins = 10
            Spare
          )
          (r, r :: log.tail)
        } else {
          val r = Row(
            frame + (if (ball == 1) 1 else 0),
            if (ball == 0) 1 else 0,
            score + pins,
            pins,
            Normal
          )
          (r, r :: log)
        }
      }

      TestGame(row.frame, row.ball, row.score, newLog)
    }
  }

  val gp = new GamePlayer {
    // always zero and return self
    private def init: Game = TestGame(0, 0, 0)

    // "play" list of pins and return state
    override def play(pins: List[Int]): ModelSimple.Game = {
      println(s"Starting new game: $pins")
      pins.foldLeft(this.init)((g, p) => g.run(p))
    }
  }

  test("Game Player must init Game with zero values") {
    val game: Game = gp.play(Nil)
    game.frame mustBe 0
    game.ball mustBe 0
    game.score mustBe 0
  }

  test("Game Player must return zero for unlucky player") {
    gp.play(List.fill(20)(0)).score mustBe 0
  }

  test("Game Player must return valid state after 3 rolls") {
    val res = gp.play(List(1, 2, 3))
    println(res)
    res.frame mustBe 1
    res.ball mustBe 1
    res.score mustBe 6
  }

  test("Game Player must return valid state after 7 rolls") {
    val res = gp.play(List(3, 5, 10, 2, 4, 0, 0))
    println(res)
    res.frame mustBe 4
    res.ball mustBe 0
    res.score mustBe 30
  }

  test("Game Player must calculate valid score for 10x1") {
    gp.play(List.fill(10)(1)).score mustBe 10
  }

  test("Game Player must calculate valid score for two rolls") {
    gp.play(List(4, 5)).score mustBe 9
  }

  test("Game Player must calculate valid score for six rolls") {
    gp.play(List(5, 3, 4, 5, 3, 4)).score mustBe 24
    gp.play(List(5, 5, 4, 5, 3, 4)).score mustBe 30
  }

  test("Game Player must calculate valid score for first strike") {
    gp.play(List(10, 3, 4, 5, 3)).score mustBe 32
  }

  /*
  test("Game Player must calculate valid score for last spare") {
    gp.play(List(4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 6, 5)).score mustBe 60
    gp.play(List(4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 5, 5)).score mustBe 54
  }

  test("Game Player must calculate valid score for pre-last strike") {
    gp.play(List(4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 10, 5, 5)).score mustBe 65
  }

  test("Game Player must calculate valid score for lucky games") {
    gp.play(List(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 9)).score mustBe 299
    gp.play(List.fill(10)(10)).score mustBe 300
  }
   */

}

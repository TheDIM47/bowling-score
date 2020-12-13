package example

import org.scalatest._
import example.ModelSimple.Game
import example.ModelSimple.GamePlayer

class SimpleGameSpec extends funsuite.AnyFunSuite with matchers.must.Matchers {

  case class TestGame(frame: Int, ball: Int, score: Int) extends Game {
    def run(pins: Int) = {
      val newBall: Int  = if (ball == 0) 1 else 0
      val newFrame: Int = frame + (if (ball == 1) 1 else 0)
      val newScore: Int = score + pins
      TestGame(newFrame, newBall, newScore)
    }
  }

  val gp = new GamePlayer {
    // always zero and return self
    private def init: Game = TestGame(0, 0, 0)

    // "play" list of pins and return state
    override def play(pins: List[Int]): ModelSimple.Game =
      pins.foldLeft(this.init)((g, p) => g.run(p))
  }

  test("Game Player must init Game with zero values") {
    val game: Game = gp.play(Nil)
    game.frame mustBe 0
    game.ball mustBe 0
    game.score mustBe 0
  }

  test("Game Player must return valid state after 3 balls") {
    val res = gp.play(List(1, 2, 3))
    println(res)
    res.frame mustBe 1
    res.ball mustBe 1
    res.score mustBe 6
  }
}

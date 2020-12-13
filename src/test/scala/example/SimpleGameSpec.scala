package example

import org.scalatest._
import example.ModelSimple.Game
import example.ModelSimple.GamePlayer

class SimpleGameSpec extends funsuite.AnyFunSuite with matchers.must.Matchers {

  test("Test Game Player must init Game with zero values") {
      val gp = new GamePlayer {
        // always zero and return self
        def init: Game = new Game {
          val frame = 0
          val ball = 0
          def run(pins: Int) = this
        }

        def play(game: ModelSimple.Game, pins: List[Int]): ModelSimple.Game = 
          pins.foldLeft(this.init)((g, p) => g.run(p))
      }

      val game: Game = gp.init 
      game.frame mustBe 0 
      game.ball mustBe 0

      val res = gp.play(game, List(1, 2, 3))
      res.frame mustBe 0 
      res.ball mustBe 0
  }
}

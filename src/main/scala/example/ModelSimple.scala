package example

object ModelSimple {

  trait Game {
    def frame: Int
    def ball: Int
    def score: Int
    def run(pins: Int): Game
  }

  trait GamePlayer {
    def init: Game
    def play(game: Game, pins: List[Int]): Game
  }


}

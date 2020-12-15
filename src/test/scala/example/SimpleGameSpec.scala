package example

import example.FrameGen.gameGen

import org.scalatest._
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class SimpleGameSpec extends funsuite.AnyFunSuite
  with matchers.must.Matchers
  with ScalaCheckPropertyChecks {

  import BowlingGame.score

  test("Game Player must init Game with zero values") {
    score(Nil) mustBe 0
  }

  test("Game Player must return zero for unlucky player") {
    score(List.fill(20)(0)) mustBe 0
  }

  test("Game Player must return valid state after 3 rolls") {
    score(List(1, 2, 3)) mustBe 6
  }

  test("Game Player must not spare rolls between frames") {
    score(List(3, 5, 5, 3)) mustBe 16
  }

  test("Game Player must return valid state after 7 rolls") {
    score(List(3, 5, 10, 2, 4, 0, 0)) mustBe 30
  }

  test("Game Player must calculate valid score for 10x1") {
    score(List.fill(10)(1)) mustBe 10
  }

  test("Game Player must calculate valid score for two rolls") {
    score(List(4, 5)) mustBe 9
  }

  test("Game Player must calculate valid score for six rolls") {
    score(List(5, 3, 4, 5, 3, 4)) mustBe 24
    score(List(5, 5, 4, 5, 3, 4)) mustBe 30
  }

  test("Game Player must calculate valid score for first strike") {
    score(List(10, 3, 4, 5, 3)) mustBe 32
  }

  test("Game Player must calculate valid score for second frame strike") {
    score(List(5, 3, 10, 3, 4, 5, 3)) mustBe 40
  }

  test("Game Player must calculate valid score for lucky games") {
    score(List.fill(9)(10) ++ List(0, 0)) mustBe 240
    score(List.fill(12)(10)) mustBe 300
    score(List.fill(11)(10) ++ List(9)) mustBe 299
  }

  test("Game Player must calculate valid score for last spare") {
    score(List(4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 6, 5)) mustBe 60
  }

  test("Game Player must calculate valid score for long game") {
    score(List(4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 5)) mustBe 54
  }

  test("Game Player must calculate valid score for pre-last strike") {
    score(List(4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 4, 1, 10, 5, 5)) mustBe 65
  }

  test("Game player should calculate random games") {
    forAll(gameGen) { r =>
      val (f, l) = r
      val game = f.flatMap(v => List(v._1, v._2)) ++ List(l._1, l._2, l._3)
      score(game) must be <= 300
    }
  }
}

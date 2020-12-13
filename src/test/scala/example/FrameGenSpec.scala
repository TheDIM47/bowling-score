package example

import org.scalatest._
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class FrameGenSpec extends funsuite.AnyFunSuite
    with matchers.must.Matchers
    with ScalaCheckPropertyChecks {

  test("FrameGen generates valid pairs") {
    forAll(FrameGen.frameGen) { frame =>
      val (f, s) = frame
      if (f == 10) s mustBe 0
      else if (s == 10) f mustBe 0
      else (f + s) must be <= 10
    }
  }

  test("LastFrameGen generates valid triples") {
    forAll(FrameGen.lastFrameGen) { frame =>
      val (f, s, t) = frame
      (f + s + t) must be <= 10
    }
  }

}

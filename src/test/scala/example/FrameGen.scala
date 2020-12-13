package example

object FrameGen {
  import org.scalacheck.Gen

  val frameGen: Gen[(Int, Int)] =
    for {
      first  <- Gen.choose[Int](0, 10) 
      second <- Gen.choose[Int](0, 10 - first)
    } yield (first, second)

  val lastFrameGen: Gen[(Int, Int, Int)] =
    for {
      first  <- Gen.choose[Int](0, 10) 
      second <- Gen.choose[Int](0, 10 - first)
      third  <- Gen.choose[Int](0, 10 - (first + second))
    } yield (first, second, third)
}

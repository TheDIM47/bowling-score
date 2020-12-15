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
      third  <- Gen.choose[Int](0, if (first == 0) 0 else (10 - (first + second)))
    } yield (first, second, third)

  val gameGen = {
    for {
      start <- Gen.sequence[List[(Int, Int)], (Int, Int)](List.fill[Gen[(Int, Int)]](9)(frameGen))
      last <- lastFrameGen
    } yield (start, last)
  }
}

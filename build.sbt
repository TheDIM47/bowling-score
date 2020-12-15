name := "bowling-score"
scalaVersion := "2.13.4"
version := "0.1.0-SNAPSHOT"
organization := "com.juliasoft"
organizationName := "juliasoft"

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"       % "3.2.3"   % Test,
  "org.scalacheck"    %% "scalacheck"      % "1.15.1"  % Test,
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.3.0" % Test
)

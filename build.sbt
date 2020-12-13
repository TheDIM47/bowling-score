ThisBuild / scalaVersion := "2.13.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.juliasoft"
ThisBuild / organizationName := "juliasoft"

lazy val root = (project in file("."))
  .settings(
    name := "bowling-score",
    libraryDependencies ++= Seq(
      "org.scalatest"     %% "scalatest"       % "3.2.3"   % Test,
      "org.scalatestplus" %% "scalacheck-1-15" % "3.2.3.0" % Test
    )
  )

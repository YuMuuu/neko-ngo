import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"

  lazy val mongoDBJavaStreamDriver = "org.mongodb" % "mongodb-driver-reactivestreams" % "1.13.1"

  lazy val cats = "org.typelevel" %% "cats-core" % "2.2.0-M2"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.1.3"
  lazy val catsFree = "org.typelevel" %% "cats-free" % "2.2.0-M2"

  lazy val fs2 = "co.fs2" %% "fs2-core" % "2.4.0"
  lazy val fs2ReactiveStreams = "co.fs2" %% "fs2-reactive-streams" % "2.4.0"
}

import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val mongoDBJavaDriver = "org.mongodb" % "mongo-java-driver" % "3.12.4"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.2.0-M2"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.1.3"
  lazy val catsFree = "org.typelevel" %% "cats-free" % "2.2.0-M2"
}

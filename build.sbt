import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("nekongo"))
  .settings(
    name := "neko-ngo",
    libraryDependencies ++= Seq(cats,
                                catsEffect,
                                catsFree,
                                fs2,
                                fs2ReactiveStreams,
                                mongoDBJavaStreamDriver)
  )

lazy val integrationTest = (project in file("integration-test"))
  .settings(
    name := "neko-ngo",
    libraryDependencies ++= Seq(scalaTest % Test)
  )
  .dependsOn(root)

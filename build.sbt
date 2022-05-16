name := "Dumutru"

version := "1.0"

scalaVersion := "2.13.2"

val AkkaVersion = "2.6.19"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % "10.2.9",
)
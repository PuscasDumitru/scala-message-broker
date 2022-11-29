name := "message-broker"

version := "1.0"

scalaVersion := "2.13.9"

lazy val akkaVersion = "2.6.20"

fork := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % "10.4.0",
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.play" %% "play-json" % "2.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

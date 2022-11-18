name := "message-broker"

version := "1.0"

scalaVersion := "2.13.9"

lazy val akkaVersion = "2.6.20"

fork := true

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core-experimental" % "2.0.2",
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

name := "Dumitru-MessageBroker"

version := "1.0"

scalaVersion := "2.13.2"

val AkkaVersion = "2.6.19"
val AkkaHttpVersion = "10.2.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-mongodb" % "3.0.4",
  "org.mongodb" % "mongodb-driver-reactivestreams" % "1.12.0",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0",
  "com.lightbend.akka" %% "akka-stream-alpakka-file" % "2.0.2",
  "com.google.code.gson" %% "gson" %% "2.1",
)

enablePlugins(JavaAppPackaging)

enablePlugins(DockerPlugin)

// change the name of the project adding the prefix of the user
packageName in Docker := "dumitru/" +  packageName.value
//the base docker images
dockerBaseImage := "java:8-jre-alpine"
//the exposed port
dockerExposedPorts := Seq(9000)
//exposed volumes
dockerExposedVolumes := Seq("/opt/docker/logs")

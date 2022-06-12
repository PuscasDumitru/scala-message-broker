name := "Dumitru"

version := "1.0"

scalaVersion := "2.13.2"

val AkkaVersion = "2.6.19"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % "10.2.9",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.9"
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

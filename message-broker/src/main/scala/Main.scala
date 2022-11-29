import akka.actor.{ActorSystem, Props}

object Main extends App {
  val system = ActorSystem()
  
  val consumer = system.actorOf(Props[Consumer], "consumer")
  val publisher = system.actorOf(Props[Publisher], "publisher")

}
import akka.actor.{ActorSystem, Props}

object Main extends App {
  val system = ActorSystem()
  //val subscriber = system.actorOf(Props[Subscriber], "subscriber")
  val publisher = system.actorOf(Props[Publisher], "publisher")

}
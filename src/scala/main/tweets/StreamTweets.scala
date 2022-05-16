package tweets

import akka.actor._
import akka.routing.{RoundRobinPool, DefaultResizer}
import akka.util.Timeout

import TweetMessages._
import akka.pattern._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


import scala.concurrent.Future

object StreamTweets extends App {

    // Create an actor system
    val system = ActorSystem("TweetActorSystem")
    // Log eveything the system
    val log = actorSystem.log

    // Define TweetActor
    val tweetActor = system.actorOf(Props[TweetActor], name = "TweetActor")

    // 5 second timeout
    implicit val timeout = Timeout(5 second)

    // Use Akka Ask Pattern and send a bunch of requests to TweetActor")
    val tweetRequests = (1 to 10).map(i => (tweetActor ? GetTweets()).mapTo[String])
    for {
    results <- Future.sequence(tweetRequests)
    } yield println(s"Tweet1 = $results")

    Thread.sleep(5000)
    val isTerminated = system.terminate()  
}

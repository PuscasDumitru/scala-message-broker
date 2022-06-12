package tweets

import akka.actor._
import akka.routing.{RoundRobinPool, DefaultResizer}
import akka.util.Timeout
import akka.NotUsed
import akka.pattern._
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http 
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import spray.json.DefaultJsonProtocol._
import TweetMessages._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Success
import scala.concurrent.Future


object StreamTweets extends App {

    // Create an actor system
    val system = ActorSystem("TweetActorSystem")

    // Dispatcher
    implicit val dispatcher = system.dispatcher

    // For streaming 
    implicit val mat: ActorMaterializer = ActorMaterializer()

    // Log eveything the system
    val log = actorSystem.log

    // Define TweetActor
    val tweetActor = system.actorOf(Props[TweetActor], name = "TweetActor")

    // 5 second timeout
    implicit val timeout = Timeout(5 second)

    implicit val tweetFormat = jsonFormat4(Tweet)

    /*
        tweet.mapAsync(elem => (tweetActor ? elem).mapTo[String])
            .runWith(Sink.foreach(/* Send to router to process */))
            .onComplete(t => {
                println(s"finished: $t")
            })
    */
    val response: Future[Seq[Tweet]] = 
        Http()
            .singleRequest(HttpRequest(uri = "http://localhost:4000/tweets/1"))
            .flatMap { response => Unmarshal(response).to[Seq[Tweet]] }
    
    // Use Akka Ask Pattern and send a bunch of requests to TweetActor")
    response.map(i => (tweetActor ? GetTweets()).mapTo[String])
    
    /*for {
    results <- Future.sequence(tweetRequests)
    } yield println(s"Tweet1 = $results")
    */

    Thread.sleep(5000)
    val isTerminated = system.terminate()  
}

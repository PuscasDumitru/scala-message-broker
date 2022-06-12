package tweets

import akka.actor.actorRef2Scala
import akka.actor.Actor
import akka.actor.ActorLogging

/**
 * The class will be the work horse of the application.
 * It will receive messages to find tweets from the SES server
 *
 */
class TweetWorkerActor extends Actor with ActorLogging {
    var state: Int = 0

    // This will be called when the worker restarts 
    override def postRestart(reason: Throwable): Unit = {
        println("Starting WorkerActor instance hashcode # {}", this.hashCode())
    }

    // Will be caled when the actor is stopped
    override def postStop() {
        println("Stopping WorkerActor instance hashcode # {}", this.hashCode())
    }

    // Send the actor to findTweets
    def receive: Receive = {
        case GetTweets: String =>
            sender ! findTweets()
    }

    /**
     * The function will stream tweets back to the main function.   
     * The tweets will be read from the RTP server provided
     */
    def findTweets(): String = {
        println("Getting Tweets....")

        // Sleep the actor
        println("Delaying the response")
        Thread.sleep(500)

        // Twitter Feed Routes and url
        val host = "127.0.0.1"
        val port = 4000
        val ses_routes: List[String] =  List("/tweets/1","/tweets/2","/emotion_values","/")

        List("")
    }
}
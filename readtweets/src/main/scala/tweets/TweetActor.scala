package tweets

import akka.actor.{Actor, ActorLogging, Props}
import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.dispatch.Future
import akka.pattern.ask
import akka.dispatch.Await
import akka.util.Timeout


class TweetActor extends Actor with ActorLogging {

	/**
	 * Define the supervisor strategy. If a worker fails restart it. Any other  
	 *	error escalate to the supervisor.
	 */
    override def supervisorStrategy: SupervisorStrategy =
      	OneForOneStrategy(maxNrOfRetries = 3, withinTimeRange = 5 seconds) {
      		// Restart the worker if it fails 
        	case _: WorkerFailedException =>
          		println("Worker failed exception, will restart.")
          		Restart
          	// Catch any other exception and send them up to the supervisor
        	case _: Exception =>
          		println("Worker failed, will need to escalate up the hierarchy")
          		Escalate
  	}	

    // We will not create one worker actor.
    // val workerActor = context.actorOf(Props[TweetWorkerActor], name = "TweetWorkerActor")

    // The pool will initially have at least 2 workers and an upper bound of 20 workers
	val resizer = DefaultResizer(lowerBound = 2, upperBound = 20)
	/* We are using a resizable RoundRobinPool. 
	 * The  pool will be initialized and its ActorRef stored in tweetWorkerRouterPool
	 */
	val props = RoundRobinPool(5, Some(resizer), supervisorStrategy = supervisorStrategy).props(Props[TweetWorkerActor])
	val tweetWorkerRouterPool: ActorRef = context.actorOf(props, "TweetWorkerRouter")

    def receive = {
    	/**
    	 * The GetTweet clause forwards the message to the Router pool which will
    	 * spawn a Worker actor to go fetch the tweets.
    	 */
      	case getTweets @ GetTweet() =>
        	println("Getting a list of tweets")
        	tweetWorkerRouterPool forward getTweets
    }
}
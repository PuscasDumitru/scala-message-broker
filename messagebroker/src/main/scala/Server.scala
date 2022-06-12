package dumitru.messagebroker

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._ 
import akka.http.scaladsl.server.Route 
import akka.http.scaladsl.unmarshalling.Unmarshaller.identityUnmarshaller 

import dumitru.messagebroker.jsonsupport.{Tweet, Tweets, JsonSupport}
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn
import scala.concurrent.Future 
import scala.util.{Failure, Success, Try}

object Server extends App with LazyLogging {

	implicit val system = ActorSystem("dumitru-server")
	implicit val materializer = ActorMaterializer()
	implicit val executionContext = system.dispatcher 

	val host = "127.0.0.1"
	val port = 8080

	// routes 
	val routes: Route = {
	    path("publish" / "topic") { 
	      post {
	        entity(as[Tweet]) { tweet =>
	          logger.info(s"creating tweet = $tweet")
	          // Call the Exchange.publish
	          TweetBusImpl.publish(topic, tweet)
	          complete(StatusCodes.Created, s"Created tweet = $tweet")
	        }
	      } 
	    } ~ path("subscribe" / "topic") { 
	      get {
	        onSuccess(tweetDao.fetchtweets()) { tweets =>
	          TweetBusImpl.subscribe(topic)
	          complete(StatusCodes.OK, tweets)
	        }
	      }
	    } ~ path("unsubscribe" / "topic") {
	      get {
	        onComplete(tweetDao.fetchtweets()) {
	          case Success(tweets) => 
	          	TweetBusImpl.unsubscribe(topic)
	          	complete(StatusCodes.OK, tweets)
	          case Failure(ex) => complete(s"Failed to fetch tweets = ${ex.getMessage}")
	        }
	      }
	    }
	}

	// Start the server and bind it
	val httpServerFuture = Http().bindAndHandle(routes, host, port)
	httpServerFuture.onComplete {
		case Success(binding) =>
			logger.info(s"TCP Server is UP...")
		case Failure(e) =>
			logger.error(s"TCP Server is DOWN...")
			system.terminate()
	}

	// Keep server running until user presses return/exit 
	StdIn.readLine() 
	
	httpServerFuture
		.flatMap(_.unbind()) // unbind the port
		.onComplete(_ => system.terminate()) // shutdown server

}
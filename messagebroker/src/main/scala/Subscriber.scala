package dumitru.messagbroker

import dumitru.messagebroker.jsonsupport.Tweet
import akka.actor._

class Subscriber extends Actor with ActorLogging {

  	override def receive: Receive = {
    	case Subscribe(topic) =>
      		log.info("Received subscribe event: {}", topic)
    	case Unsubscribe(topic) =>
      		log.info("Received unsubscribe event: {}", topic)
    	case any@_ =>
      		log.warning("Unknown message: {}", any)
  	}

  	override def preStart(): Unit = {
    	log.info("subscriber start...")
    	context.system.eventStream.subscribe(self, classOf[Event])
  	}

  	override def postStop(): Unit = {
    	log.info("subscriber stop...")
  	}
}
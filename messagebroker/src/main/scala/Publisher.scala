package dumitru.messagebroker

import akka.actor._

class Publisher extends Actor with ActorLogging {
  
  	override def receive: Receive = {
    	case Publish(topic) =>
      		log.info("publish topic: {}", topic)
      		context.system.eventStream.publish(topic)
    	case any@_ =>
      		log.warning("Unknown event: {}", any)
  	}

  	override def preStart(): Unit = log.info("publisher start...")
}

case class Publish(events: Event)
package dumitru.messagebroker 

object Tweet {

	// TODO : Consume  


	val onEvent = (topic: String, message: Any) => Some(topic) collect {
		case "#akka" =>
			println("Topic": + topic + " Tweet: " + message)
	}
}
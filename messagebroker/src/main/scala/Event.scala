package dumitru.messagebroker 

trait Event {}
case class Publish(topic: String, content: AnyRef) extends Event
case class Subscribe(topic: String) extends Event 
case class UnSubscribe(topic: String) extends Event
case class Acknowledge extends Event 
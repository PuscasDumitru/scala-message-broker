package dumitru.messagebroker

import akka.actor.{Actor, Props, ActorSystem}
import akka.event.EventBus 
import akka.event.LookupClassification

final case class MessageTweet(topic: String, tweet: Tweet)

/**
 * Publishes the tweet from the MessageTweet object when the topic 
 * matches the String specified when subscribing
 */
class TweetBusImpl extends EventBus with LookupClassification {
  type Event = MessageTweet
  type Classifier = String 
  type Subscriber = ActorRef

  // is used for exctracting the classifier from the incoming events
  override protected def classify(event: Event): Classifier = event.topic 

  // Will be invoked for each event for all subscibers which registered for the event
  override protected def publish(event: Event, subscriber: Subscriber): Unit = {
    // Send the tweet to all the topic subscibers
    subscriber ! event.tweet
  }

  override protected def compareSubscribers(a: Subscriber, b: Subscriber): Int = a.compareTo(b)

  // The default size for internal topic size
  override protected def mapSize: Int = 128
}

/**
 * The exchange is the EventStream that will allow actors to subscribe and consume 
 * messages published.
 * 
 */
object TweetBusImpl {
	val system = ActorSystem("message-broker")
	val bus = new TweetBusImpl()

	// Create the publish and subscribe actors to interface with the http
	system.actorOf(Props(new Subscriber(bus)), "subsciber-actor")
	system.actorOf(Props(new Publisher(bus)), "publisher-actor")

	/**
	 * Publish fun to be called
	 */
	def publish(topic: String, message: Tweet): Unit = {
		bus.publish(MessageTweet(topic, message))
	}

	/** 
	 * Subscribe to the topic
	 */
	def subscribe(topic): Unit = {
		bus.subscribe(self, topic)
	}
}

import akka.actor.{Actor, ActorRef, ActorSelection, Props}

import play.api.libs.json._
import scala.collection.mutable.ArrayBuffer

class Publisher extends Actor {
  
    val serverManager: ActorRef = context.actorOf(Props(new TCPConnectionManager("localhost", 8081)))

    override def receive: Receive = {
        case message: String =>
            val json: JsValue = Json.parse(message)
            val topic = (json \ "topic").as[String]
            
            if (!Data.topics.contains(topic)) { 
                Data.topics += (topic -> ArrayBuffer[String]())
            }
            
            Data.topics(topic) += message

            // Data.topics.keys.foreach{i =>
            //     print("Key = " + i)
            //     println(" Value = " + Data.topics(i).length)
            // }
    }
}

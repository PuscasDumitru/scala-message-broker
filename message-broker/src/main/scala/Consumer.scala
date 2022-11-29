
import akka.actor.{Actor, ActorRef, ActorSelection, Props}

import play.api.libs.json._
import scala.collection.mutable.ArrayBuffer

class Consumer extends Actor {
  
    val serverManager: ActorRef = context.actorOf(Props(new TCPConnectionManager("localhost", 8082)))

    override def receive: Receive = {
        case data: Data.MessageJson =>
            try {
                if (!data.messageReceived.isEmpty()) {
                var splitMessage = data.messageReceived.split(" ")
                var command = splitMessage(0)
                var topic = splitMessage(1)
                var cleanRemote = data.sender.substring(1)
                
                if (command == "subscribe") {
                    if (!Data.consumers.contains(cleanRemote)) {
                        Data.consumers += (cleanRemote -> ArrayBuffer[String]())
                    }

                    Data.consumers(cleanRemote) += topic
                }
                else if (command == "unsubscribe") {
                    Data.consumers(cleanRemote) -= topic
                }

                }
            } catch {
                case e: Exception =>
                println("Something went wrong: " + e.getMessage)
      }
            
            // println(data.messageReceived)
            // println(data.sender)
            // val json: JsValue = Json.parse(message)
            // val topic = (json \ "topic").as[String]
            
            // if (!Data.topics.contains(topic)) { 
            //     Data.topics += (topic -> ArrayBuffer[String]())
            // }
            
            // Data.topics(topic) += message

            // Data.consumers.keys.foreach{i =>
            //     print("Key = " + i)
            //     println(" Value = " + Data.consumers(i).length)
            // }
    }
}

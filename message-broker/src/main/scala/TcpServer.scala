import java.net.InetSocketAddress

import akka.actor.{ActorSystem, Props, Actor}
import akka.io.{Tcp, IO}
import akka.io.Tcp._
import akka.util.ByteString
import scala.collection.mutable.ArrayBuffer

class TCPConnectionManager(address: String, port: Int) extends Actor {
  import context.system
  IO(Tcp) ! Bind(self, new InetSocketAddress(address, port))

  var remote: String = ""
  override def receive: Receive = {
    case Bound(local) =>
      println(s"Server started on $local")
    
    case Connected(remote, local) =>
      this.remote = remote.toString
      val handler = context.actorOf(Props[TCPConnectionHandler])
      println(s"New connnection: $local -> $remote")
      sender() ! Register(handler)
    
    case message: String =>
      context.parent ! Data.MessageJson(message, remote)
  }
}

class TCPConnectionHandler extends Actor {
  override def receive: Actor.Receive = {
    case Received(data) =>
      val decoded = data.decodeString("ISO-8859-1")
      
      context.parent ! decoded
      // sender() ! ResumeReading
      sender() ! Write(ByteString(s"Message retrieved: $decoded"))

    case message: ConnectionClosed =>
      println("Connection has been closed")
      context stop self
  }
}

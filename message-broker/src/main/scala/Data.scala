import scala.collection.mutable.ArrayBuffer

object Data {
  var consumers: scala.collection.mutable.Map[String, ArrayBuffer[String]] = scala.collection.mutable.Map[String, ArrayBuffer[String]]()
  var topics: scala.collection.mutable.Map[String, ArrayBuffer[String]] = scala.collection.mutable.Map[String, ArrayBuffer[String]]()

  case class MessageJson(messageReceived: String, sender: String)
}

package dumitru.messagebroker.jsonsupport 

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

final case class Tweet(screen_name: String, description: String)
final case class Tweets(tweets: Seq[Tweet])

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  import spray.json._
  implicit val printer = PrettyPrinter

  implicit val tweetFormat = jsonFormat2(Tweet)
  implicit val tweetsJsonFormat = jsonFormat1(Tweets)
}
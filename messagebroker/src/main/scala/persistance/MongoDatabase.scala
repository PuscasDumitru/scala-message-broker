package dumitru.messagebroker.persistance 

import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

import com.mongodb.reactivestreams.client.MongoCollection

import dumitru.messagebroker.jsonsupport.Jsonsupport._

import akka.NotUsed
import akka.stream.alpakka.file.scaladsl.FileTailSource
import akka.stream.scaladsl.Source

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object CodecRegistry {
  	val tweetCodec = fromRegistries(fromProviders(classOf[Tweet]), DEFAULT_CODEC_REGISTRY)
}

object Collections {

	final val client = MongoClients.create("mongodb://localhost:27019")
  	final val db = client.getDatabase("tweet-db")

  	val tweetCollection: MongoCollection[Tweet] = db.getCollection(classOf[Tweet].getSimpleName, classOf[Tweet])
     .withCodecRegistry(CodecRegistry.tweetCodec)

    val insertionResult = Source(Seq(tweetData)).runWith(MongoSink.insertOne(Collections.tweetCollection))
	insertionResult.map{ res =>
		println("Mongo record inserted successfully.. " + res)
	}.recover{
		case ex =>
			ex.printStackTrace()
	}

 }
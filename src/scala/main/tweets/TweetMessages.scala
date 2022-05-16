package tweets


/**
 * Define protocol to be used by the actor system. 
 * Which is a GetTweet message and a WorkerFailedException for a failed worker message
 */
object TweetMessages {

    case class GetTweets()

    case class WorkerFailedException(error: String) extends Exception(error)
}
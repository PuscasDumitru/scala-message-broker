
Running the Code
----------------

Follow these steps to run the code:

1. `cd` into the directory.
1. Type `sbt run` to start the actor system.

When the local actor system starts, it will send an initial message
to the remote actor system. The remote actor will send a reply through
its `sender` reference, and this will continue five times. When the
action stops, stop each system by pressing Ctrl-C.

Rtp Server
-----------------

`curl --url http://localhost:4000`

`
{"routes":["/tweets/1","/tweets/2","/emotion_values","/"],"lab_description":"\n            
(1) Process tweets, that is compute the sentiment score, as they come\n            
(2) Have a group of workers and a supervisor\n            
(3) Dynamically change the number of actors depending on the load\n            
(4) In case of panic message, kill the worker actor then restart\n            
(5) Actors also must have a random sleep, in the range of 50ms to 500ms, normally distributed\n            
","general_description":"To start streaming data, access the /tweets/1 and /tweets/2 routes. Data is in SSE/EventSource format and is made of real Twitter API data, to access the text use the text field of the obtained json. The dictionary of sentiment scores can be found at the /emotion_values route. Use the `Sum(word_em_score) / Nr. of words in a tweet` to compute the emotional score of a tweet."}
`

Tweet Reply
------------------
%EventsourceEx.Message{
  data: "{\"message\": {\"tweet\": {\"user\":{\"screen_name\":\"migangelp8\",\"follow_request_sent\":null,\"time_zone\":null,\"profile_use_background_image\":true,\"profile_image_url_https\":\"https://pbs.twimg.com/profile_images/713862887408869376/jsTL9w_j_normal.jpg\",\"geo_enabled\":false,\"favourites_count\":32,\"description\":null,\"default_profile_image\":false,\"profile_background_tile\":false,\"url\":null,\"notifications\":null,\"profile_background_color\":\"C0DEED\",\"name\":\"Miguel Perez\",\"created_at\":\"Mon Sep 01 20:32:22 +0000 2014\",\"profile_background_image_url\":\"http://abs.twimg.com/images/themes/theme1/bg.png\",\"contributors_enabled\":false,\"location\":null,\"listed_count\":1,\"profile_background_image_url_https\":\"https://abs.twimg.com/images/themes/theme1/bg.png\",\"lang\":\"es\",\"is_translator\":false,\"profile_text_color\":\"333333\",\"statuses_count\":1163,\"profile_sidebar_border_color\":\"C0DEED\",\"utc_offset\":null,\"verified\":false,\"profile_link_color\":\"0084B4\",\"profile_image_url\":\"http://pbs.twimg.com/profile_images/713862887408869376/jsTL9w_j_normal.jpg\",\"id\":2758566867,\"profile_sidebar_fill_color\":\"DDEEF6\",\"default_profile\":true,\"following\":null,\"followers_count\":56,\"friends_count\":81,\"id_str\":\"2758566867\",\"protected\":false},\"truncated\":false,\"timestamp_ms\":\"1461007847658\",\"text\":\"@NTN24ve @InformadorVeraz y que pasa si se niega a ir?\",\"source\":\"<a href=\\\"http://twitter.com/download/android\\\" rel=\\\"nofollow\\\">Twitter for Android</a>\",\"retweeted\":false,\"retweet_count\":0,\"place\":null,\"lang\":\"es\",\"is_quote_status\":false,\"in_reply_to_user_id_str\":\"200267797\",\"in_reply_to_user_id\":200267797,\"in_reply_to_status_id_str\":\"722140180300177408\",\"in_reply_to_status_id\":722140180300177408,\"in_reply_to_screen_name\":\"NTN24ve\",\"id_str\":\"722145369921077249\",\"id\":722145369921077249,\"geo\":null,\"filter_level\":\"low\",\"favorited\":false,\"favorite_count\":0,\"entities\":{\"user_mentions\":[{\"screen_name\":\"NTN24ve\",\"name\":\"NTN24 Venezuela\",\"indices\":[0,8],\"id_str\":\"200267797\",\"id\":200267797},{\"screen_name\":\"InformadorVeraz\",\"name\":\"InformadorVeraz\",\"indices\":[9,25],\"id_str\":\"95038850\",\"id\":95038850}],\"urls\":[],\"symbols\":[],\"hashtags\":[]},\"created_at\":\"Mon Apr 18 19:30:47 +0000 2016\",\"coordinates\":null,\"contributors\":null}, \"unix_timestamp_100us\": 16523062290197}}",
  dispatch_ts: ~U[2022-05-11 21:57:09.271747Z],
  event: "message",
  id: nil
}
:ok

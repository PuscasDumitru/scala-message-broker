> Real-Time Programming Lab
> Message Broker 
> Dumitru Puscas, FAF-192


`MessageBroker` is a TCP server created with Scala using the Akka library, any client/producer can connect to it using a tool like `telnet` or `netcat`.
The system was designed using the Actor Model, specifically the Publish/Subscribe topology.


### **RTP Server(producer)**

`curl --url http://localhost:4000`
     
1. Process tweets, that is compute the sentiment score, as they come            
2. Have a group of workers and a supervisor         
3. Dynamically change the number of actors depending on the load            
4. In case of panic message, kill the worker actor then restart            
5. Actors also must have a random sleep, in the range of 50ms to 500ms, normally distributed            
","general_description":"To start streaming data, access the /tweets/1 and /tweets/2 routes. Data is in SSE/EventSource format and is made of real Twitter API data, to access the text use the text field of the obtained json. The dictionary of sentiment scores can be found at the /emotion_values route. Use the `Sum(word_em_score) / Nr. of words in a tweet` to compute the emotional score of a tweet."}


### **`MessageBroker` Actors**

- `Server` - TCP server to which the subscribers and publishers connect
- `Manager` - The first actor to get the command from subscriber/publisher and somehow process it (add topic, check subscription), lays the ground for the command to be further processed
- `WorkerPool` - generic WP implementation, used to spawn a `Worker.Controller` workers
- `Worker.Controller` - multiple workers that handles the client commands (`:pub, :unsub, :sub`)
- `Events` - shared state/store for keeping current session of published events
- `Subscriptions` - keeping and sharing current topics subscriptions and subscribers counter of unacknowledged messages (in order to know if we can notify a subscriber about a newly published event or we should wait for some `:ack`s commands to decrease the counter)

### **`MessageBroker` General Diagram**

![generalDiag](./docs/images/mb_message_exchange.png)

### **`MessageBroker` Supervision Tree** 

![treeDiag](./docs/images/sup_tree.png)

### **`MessageBroker` Message Exchange Diagram**

![messageDiag](./docs/images/common.png)

The system uses Akka’s EventStream API to allow other apps to publish and subscribe to topics and content. 
An Akka EventStream is a pub-sub stream of events both system and user generated, where subscribers are ActorRefs and the channels are Classes and Events are any java.lang.Object. EventStreams employ SubchannelClassification, which means that if you listen to a Class, you'll receive any message that is of that type or a subtype.

### **Endpoints**

•	http://localhost:8080/publish/{topic} POST {Message} 
•	http://localhost:8080/subscribe/{topic} GET
•	http://localhost:8080/unsubscribe/{topic} GET


### **Running The Code**

Follow these steps to run the code:

1. `cd` into the directory.
1. Type `sbt run` to start the actor system.

When the local actor system starts, it will send an initial message
to the remote actor system. The remote actor will send a reply through
its `sender` reference, and this will continue five times. When the
action stops, stop each system by pressing Ctrl-C.


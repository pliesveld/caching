# caching
Source code examples and demo from Mark Richards microservices caching session at NFJS.

See the [demo](demo/) folder for a recording.

The presentation slides are available [here.](https://nofluffjuststuff.com/s/slides/2018/speaker/Mark_Richards/microservices_caching_strategies/Microservices_Caching.pdf)

### environment setup

Run the RabbitMQ server:

`docker run -p 5672:5672 -d --hostname my-rabbit --name some-rabbit rabbitmq:3`

#### Initialize Queue
_To setup all of the exchanges, queues, and bindings used by these examples, run the following_:

`./gradlew initQueue`

#### Start the DataWriter
_The DataWriter receives update requests over the `datapump.q` queue and simulates a slow operation such as writing to a backend data store._  

`./gradlew runDataWriter`

#### Start multiple CustomerInfoService
_CustomerInfoService consumes client requests to update the name.  Each service instance listens for update events over the queue `name.q`.  When an event occurs, an distributed in-memory cache is updated first.  The hazlecast distributed in-memory cache updates the memory of the other service instances currently running.  In addition, the name update is routed to the `DataWriter` for persistent storage over the `datapump.q` queue._

Open a new terminal and run the following to start the service and initialize the cache:

`./gradlew runService --args load` 

Open two new terminals run the following in each:

`./gradlew runService`

#### Send Update Name Event
_Run the following multiple times each with a different placeholder value for the argument:_

`./gradlew update --args <Name>`

#### Diagnostics

_The following command examines the queues inside the RabbitMQ server:_

`watch docker exec some-rabbit rabbitmqctl list_queues`

# Task Multithreading
*Develop a multi-threaded application using shared
resources. Any entity wishing to access the shared
resource must be a stream. The application should be implemented
functionality defined by an individual task.*

## Requirements
* The program must use the synchronization capabilities provided
java.util.concurrent and java.util.concurrent.locks libraries.
* Do not use synchronized, volatile, as well as BlockingQueue and others to a limited extent
thread safe collections.
* Classes and other entities of the application must be competently structured by packages and have a name that reflects their functionality.
* Use the State template to describe the state of an object, if only these states more than two.
* You can use Callable whenever possible to create streams.
* Instead of Thread.sleep, use only the capabilities of the TimeUnit enumeration.
* Read object initialization data from a file. The data in the file is correct.
* Use Log4J2 to record logs.
* It is allowed to use the main method to display the work of threads.

## Port
Ships call at the port for unloading and / or loading containers and are moored to
to the berths. Each berth can have only one ship. Containers
are reloaded from the ship to the port warehouse and / or from the warehouse to the ship. Number of containers
cannot exceed the capacity of a warehouse or ship. Each ship must
to be served.
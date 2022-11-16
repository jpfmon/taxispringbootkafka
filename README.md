# taxispringbootkafka

This is Spring Boot rest api application to receive geolocation data from taxis, process the data, write it into Kafka topics:
- Contains docker.compose.yml file to set up in Docker a Kafka cluster with 2 kafka servers and 2 zookeeper servers.
- Exposes 2 rest api endpoints:
  - "/": to test the controller and, to ease testing purposes, displays a html form to send data to second endpoint
  - "/checkin": accepts 3 required path paramenters:
    - taxiId: the Id of the taxi sending the geolocation data
    - lat: stands for latitude
    - long: stands for longitude
    both latitude and longitude values are validated. If not valid, a text is displayed in the view stating so.
  - Two topics: input topic (inputSignalsTopic) and output (outputtaxidisntace)
  - One producer that writes the data received, as an object of type Signal, into an input topic, which has 3 partitions. The data of each taxi is sent providing key, so all its data will be written into the same partition.
  - Three tracker consumers (SignalTrackerConsumers), each of them is dedicated to one partition. Each consumer processes the input message, received as Signal, and calculates the actual total distance traveled for the given taxi, and writes this data (taxiId and totalDistance) as a TaxiDistance object to the output topic.
  - One consumer (OutputTaxiDistanceConsumer), which reads from the output topic and logs the taxiId and total distance traveled.

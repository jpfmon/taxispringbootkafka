package com.montojo.taxikafka.signaltrackerconsumers;

import com.montojo.taxikafka.producers.inputproducer.Signal;
import com.montojo.taxikafka.distanceservice.DistanceService;
import com.montojo.taxikafka.producers.outputproducer.OutputProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class TrackerConsumer {

    @Autowired
    private DistanceService distanceService;

    @Autowired
    private OutputProducer outputProducer;
    private static final Logger logger = LoggerFactory.getLogger(TrackerConsumer.class);

    @KafkaListener(containerFactory = "signalKafkaListenerContainerFactory", topics = "${kafka.topic.signal.input.name}", concurrency = "3",groupId = "${kafka.topic.signal.input.group}",
                    topicPartitions = {@TopicPartition(topic = "${kafka.topic.signal.input.name}", partitions = "0")})
    public void getSignalMessagePartition0(@Payload Signal signal,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                 @Header(KafkaHeaders.OFFSET) Long offset,
                                 @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key){

        logger.info("\n\nReceived in Listener partition 0, a message with key {}, which is the same as taxiId: {}, with latitude {} and longitude {}, from topic: {}, partition: {}, offset {}\n\n",
                key, signal.getTaxiId(), signal.getLatitude(),signal.getLongitude(),topic, partition, offset);

        float totalDistance =  distanceService.processSignal(signal);

        System.out.println("***********************************");
        System.out.println("\n In TrackerConsumer\nTotalDistance traveled received is: " + totalDistance + " kms for taxi: " + signal.getTaxiId());
        System.out.printf("Previous coordinates, latitude %s , longitude %s", signal.getLatitude(), signal.getLongitude());
        System.out.println("\n***********************************\n");

        outputProducer.write(signal.getTaxiId(), totalDistance);
    }

    @KafkaListener(containerFactory = "signalKafkaListenerContainerFactory", topics = "${kafka.topic.signal.input.name}", concurrency = "3",groupId = "${kafka.topic.signal.input.group}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic.signal.input.name}", partitions = "1")})
    public void getSignalMessagePartition1(@Payload Signal signal,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                 @Header(KafkaHeaders.OFFSET) Long offset,
                                 @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key){

        logger.info("\n\nReceived in Listener partition 1, a message with key {}, which is the same as taxiId: {}, with latitude {} and longitude {}, from topic: {}, partition: {}, offset {}\n\n",
                key, signal.getTaxiId(), signal.getLatitude(),signal.getLongitude(),topic, partition, offset);

        float totalDistance =  distanceService.processSignal(signal);

        System.out.println("***********************************");
        System.out.println("\n In TrackerConsumer\nTotalDistance traveled received is: " + totalDistance + " kms for taxi: " + signal.getTaxiId());
        System.out.printf("Previous coordinates, latitude %s , longitude %s", signal.getLatitude(), signal.getLongitude());
        System.out.println("\n***********************************\n");

        outputProducer.write(signal.getTaxiId(), totalDistance);
    }

    @KafkaListener(containerFactory = "signalKafkaListenerContainerFactory", topics = "${kafka.topic.signal.input.name}", concurrency = "3",groupId = "${kafka.topic.signal.input.group}",
            topicPartitions = {@TopicPartition(topic = "${kafka.topic.signal.input.name}", partitions = "2")})
    public void getSignalMessagePartition2(@Payload Signal signal,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                 @Header(KafkaHeaders.OFFSET) Long offset,
                                 @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key){

        logger.info("\n\nReceived in Listener partition 2, a message with key {}, which is the same as taxiId: {}, with latitude {} and longitude {}, from topic: {}, partition: {}, offset {}\n\n",
                key, signal.getTaxiId(), signal.getLatitude(),signal.getLongitude(),topic, partition, offset);

        float totalDistance =  distanceService.processSignal(signal);

        System.out.println("***********************************");
        System.out.println("\n In TrackerConsumer\nTotalDistance traveled received is: " + totalDistance + " kms for taxi: " + signal.getTaxiId());
        System.out.printf("Previous coordinates, latitude %s , longitude %s", signal.getLatitude(), signal.getLongitude());
        System.out.println("\n***********************************\n");

        outputProducer.write(signal.getTaxiId(), totalDistance);
    }
}

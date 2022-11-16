package com.montojo.taxikafka.consumers.outputconsumer;

import com.montojo.taxikafka.producers.outputproducer.TaxiDistance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OutputTaxiDistanceConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OutputTaxiDistanceConsumer.class);

    @KafkaListener(containerFactory = "taxiDistanceKafkaListenerContainerFactory", topics = "${kafka.topic.taxidistance.output.name}", groupId = "${kafka.topic.taxidistance.output.group}")
    public void getSignalMessagePartition0(@Payload TaxiDistance taxiDistance,
                                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                           @Header(KafkaHeaders.OFFSET) Long offset,
                                           @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key){

        logger.info("\n\nTHIS IS THE FINAL LOG\n\n" +
                        "Received in Output Listener, a message " +
                        "\nwith offset {} " +
                        "\nwith key {}, which is the same as " +
                        "\ntaxiId: {}, " +
                        "\ntotal distance: {} " +
                        "\n",
                offset,key, taxiDistance.getId(), taxiDistance.getDistance());
    }
}

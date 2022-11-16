package com.montojo.taxikafka.producers.outputproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OutputProducer {
    @Autowired
    private final KafkaTemplate<String, TaxiDistance> kafkaTemplate;

    @Value("${kafka.topic.taxidistance.output.name}")
    private String topic;

    private static final Logger logger = LoggerFactory.getLogger(OutputProducer.class);

    public OutputProducer(KafkaTemplate<String, TaxiDistance> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void write(String taxiId, float totalDistance) {
        TaxiDistance taxiDistance = new TaxiDistance(taxiId, totalDistance);
        //sending Distance for each taxi messages with taxiId as key.
        logger.info("\n\nWriting into output topic: taxiId {} distance {}", taxiId, taxiDistance);
        kafkaTemplate.send(topic, taxiId,taxiDistance);
    }
}

package com.montojo.taxikafka.producers.inputproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SignalInputProducer {
    @Autowired
    private final KafkaTemplate<String, Signal> kafkaTemplate;

    @Value("${kafka.topic.signal.input.name}")
    private String topic;

    public SignalInputProducer(KafkaTemplate<String, Signal> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Writes each taxi signal to topic, setting for key the taxiid,
     * so all signals from one taxi go to the same partition
     * @param signal
     */
    public void writeSignal(Signal signal) {
        //sending Signal for each taxi messages with taxiId as key.
        kafkaTemplate.send(topic, signal.getTaxiId(), signal);
    }
}

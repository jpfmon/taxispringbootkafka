package com.montojo.taxikafka.kafkaconfig;

import com.montojo.taxikafka.producers.inputproducer.Signal;
import com.montojo.taxikafka.producers.outputproducer.TaxiDistance;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class ConsumersConfig {

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.topic.signal.input.group}")
    private String inputSignalConsumersGroup;

    @Value("${kafka.topic.taxidistance.output.group}")
    private String taxiDistanceConsumerGroup;

    //consumers factory for inputsignalstopic
    @Bean
    public ConsumerFactory<String, Signal> signalConsumerFactory(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, inputSignalConsumersGroup);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"com.montojo.taxikafka.producers.inputproducer.Signal");
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Signal> signalKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Signal> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(signalConsumerFactory());
        return factory;
    }

    //consumer factory for outputTaxiDistance topic
    @Bean
    public ConsumerFactory<String, TaxiDistance> taxiDistanceConsumerFactory(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, taxiDistanceConsumerGroup);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        properties.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"com.montojo.taxikafka.producers.outputproducer.TaxiDistance");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TaxiDistance> taxiDistanceKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, TaxiDistance> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(taxiDistanceConsumerFactory());
        return factory;
    }
}

package com.montojo.taxikafka.kafkaconfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
//@EnableKafka
public class TopicsConfig {


    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.topic.signal.input.name}")
    private String inputSignalsTopic;

    @Value("${kafka.topic.taxidistance.output.name}")
    private String taxiDistanceOutputTopic;

    @Bean
    public NewTopic inputSignalsTopic(){
        return TopicBuilder.name(inputSignalsTopic)
                .partitions(3)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic outputTopic(){
        return TopicBuilder.name(taxiDistanceOutputTopic)
                .partitions(3)
                .replicas(2)
                .build();
    }
}

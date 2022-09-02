package com.odeyalo.analog.neflix.subscriptionservice.config.kafka;


import com.odeyalo.analog.neflix.subscriptionservice.config.kafka.support.GsonSerializer;
import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;
import com.stripe.model.Event;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class KafkaProducerConfiguration {

    @Value("${kafka.connection.url}")
    protected String APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL = "localhost:9092";

    protected final Logger logger = LoggerFactory.getLogger(KafkaProducerConfiguration.class);

    @Bean
    public KafkaTemplate<String, EmailMessageDTO> mailMessageDTOKafkaTemplate(ProducerFactory<String, EmailMessageDTO> factory) {
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public ProducerFactory<String, EmailMessageDTO> mailMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig(), new StringSerializer(), new JsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, Event> stringEventKafkaTemplate() {
        return new KafkaTemplate<>(eventProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Event> eventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig(), new StringSerializer(), new GsonSerializer<Event>());
    }

    @Bean
    public HashMap<String, Object> kafkaProducerConfig() {
        HashMap<String, Object> config = new HashMap<>(5);
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL);
        config.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class);
        return config;
    }
}

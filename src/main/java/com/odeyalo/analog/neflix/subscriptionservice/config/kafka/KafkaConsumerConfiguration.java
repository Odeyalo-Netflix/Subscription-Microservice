package com.odeyalo.analog.neflix.subscriptionservice.config.kafka;

import com.odeyalo.analog.neflix.subscriptionservice.config.kafka.support.GsonDeserializer;
import com.stripe.model.Event;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;

@Configuration
public class KafkaConsumerConfiguration {
    private static final String APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL = "localhost:9092";

    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter() {
        return new StringJsonMessageConverter();
    }

    @Bean
    public ConsumerFactory<String, Event> stringEventConsumerFactory() {
        final GsonDeserializer<Event> jsonDeserializer = new GsonDeserializer<>(Event.class);
        return new DefaultKafkaConsumerFactory<>(gsonDeserializerConsumerConfig(), new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Event> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringEventConsumerFactory());
        return factory;
    }

    @Bean
    public HashMap<String, Object> gsonDeserializerConsumerConfig() {
        HashMap<String, Object> config = genericConsumerConfig();
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class);
        return config;
    }


    protected HashMap<String, Object> genericConsumerConfig() {
        HashMap<String, Object> config = new HashMap<>(5);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, APACHE_KAFKA_MESSAGE_BROKER_CONNECTION_URL);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "id.12");
        return config;
    }
}

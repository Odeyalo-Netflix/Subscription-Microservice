package com.odeyalo.analog.neflix.subscriptionservice.config.kafka.support;

import com.google.gson.Gson;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Custom written serializer for Apache Kafka. It written for using classes that not supported Jackson,
 * like Stripe Event class that uses Gson for parsing JSON
 * @param <T> - param to serialize
 *
 * @see com.stripe.model.Event
 */
public class GsonSerializer<T> implements Serializer<T> {
    private final Gson gson = new Gson();
    private final Logger logger = LoggerFactory.getLogger(GsonSerializer.class);

    @Override
    public void configure(Map configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) {
            this.logger.info("Received null data. Skipped");
            return null;
        }
        String json = gson.toJson(data);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, T data) {
        return serialize(topic, data);
    }

    @Override
    public void close() {}
}

package com.odeyalo.analog.neflix.subscriptionservice.config.kafka.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

/**
 * Custom written Kafka Deserializer that deserialize JSON using GSON.
 * It useful when class is not supported Jackson or uses Gson library
 * @param <T> - class to deserialize
 */
public class GsonDeserializer<T> implements Deserializer<T> {
    private final Class<?> classToDeserialize;
    private final Gson gson = new GsonBuilder().create();

    public GsonDeserializer(Class<?> classToDeserialize) {
        this.classToDeserialize = classToDeserialize;
    }

    @Override
    public void configure(Map<String, ?> config, boolean isKey) {}


    @Override
    public T deserialize(String topic, byte[] bytes) {
        return (T) gson.fromJson(new String(bytes), classToDeserialize);
    }


    @Override
    public void close() {}
}

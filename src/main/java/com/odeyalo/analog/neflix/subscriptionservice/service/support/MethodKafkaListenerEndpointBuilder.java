package com.odeyalo.analog.neflix.subscriptionservice.service.support;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import java.lang.reflect.Method;
import java.util.Collections;

public class MethodKafkaListenerEndpointBuilder<K, V> {
    private String id;
    private Object bean;
    private String topic;
    private boolean autoStartup;
    private String groupId;
    private BeanFactory factory;
    private HandlerMethodArgumentResolver resolver;
    private Method method;

    public static <K, V> MethodKafkaListenerEndpointBuilder<K, V>  builder() {
        return new MethodKafkaListenerEndpointBuilder<>();
    }

    private MethodKafkaListenerEndpointBuilder() {
    }

    public MethodKafkaListenerEndpointBuilder<K, V> bean(Object bean) {
        this.bean = bean;
        return this;
    }

    public MethodKafkaListenerEndpointBuilder<K, V> id(String id) {
        this.id = id;
        return this;
    }

    public MethodKafkaListenerEndpointBuilder<K, V> topics(String topic) {
        this.topic = topic;
        return this;
    }

    public MethodKafkaListenerEndpointBuilder<K, V> autoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
        return this;
    }

    public MethodKafkaListenerEndpointBuilder<K, V> groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public MethodKafkaListenerEndpointBuilder<K, V> factory(BeanFactory factory) {
        this.factory = factory;
        return this;
    }

    public MethodKafkaListenerEndpointBuilder<K, V> resolver(HandlerMethodArgumentResolver resolver) {
        this.resolver = resolver;
        return this;
    }

    public MethodKafkaListenerEndpointBuilder<K, V> method(Method method) {
        this.method = method;
        return this;
    }

    public MethodKafkaListenerEndpoint<K, V> build() {
        MethodKafkaListenerEndpoint<K, V> endpoint = new MethodKafkaListenerEndpoint<>();
        endpoint.setBean(bean);
        endpoint.setId(id);
        endpoint.setTopics(topic);
        endpoint.setAutoStartup(autoStartup);
        endpoint.setGroupId(groupId);
        endpoint.setBeanFactory(factory);
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setArgumentResolvers(Collections.singletonList(resolver));
        endpoint.setMessageHandlerMethodFactory(factory);
        endpoint.setMethod(method);
        return endpoint;
    }
}

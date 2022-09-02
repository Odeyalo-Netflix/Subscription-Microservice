package com.odeyalo.analog.neflix.subscriptionservice.service.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import java.util.List;


/**
 * The abstract runtime kafka listener registrar that provides a more simple registration process
 * @param <K> - key
 * @param <V> - value
 */
public abstract class AbstractRuntimeMethodKafkaMessageListenerEndpointRegistrar<K, V> implements InitializingBean, ApplicationContextAware {
    protected final KafkaListenerEndpointRegistry registry;
    protected ApplicationContext context;
    protected final ConcurrentKafkaListenerContainerFactory<K, V> kafkaListenerContainerFactory;
    protected final Logger logger = LoggerFactory.getLogger(AbstractRuntimeMethodKafkaMessageListenerEndpointRegistrar.class);


    public AbstractRuntimeMethodKafkaMessageListenerEndpointRegistrar(KafkaListenerEndpointRegistry registry,
                                                                      ConcurrentKafkaListenerContainerFactory<K, V> kafkaListenerContainerFactory) {
        this.registry = registry;
        this.kafkaListenerContainerFactory = kafkaListenerContainerFactory;
}

    @Override
    public void afterPropertiesSet() throws Exception {
        for (MethodKafkaListenerEndpoint<K, V> endpoint : getListenerEndpoints()) {
            this.registry.registerListenerContainer(endpoint, kafkaListenerContainerFactory);
        }
    }

    public abstract List<MethodKafkaListenerEndpoint<K, V>> getListenerEndpoints();

    protected abstract HandlerMethodArgumentResolver getArgumentResolver();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

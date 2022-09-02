package com.odeyalo.analog.neflix.subscriptionservice.service.support;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.StripeEventHandler;
import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.support.StripeEventHandlerAutoRegistry;
import com.stripe.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StripeEventRuntimeMethodKafkaMessageListenerEndpointRegistrar extends AbstractRuntimeMethodKafkaMessageListenerEndpointRegistrar<String, Event> {
    private final List<StripeEventHandlerAutoRegistry> handlersAutoRegistry;

    @Autowired
    public StripeEventRuntimeMethodKafkaMessageListenerEndpointRegistrar(KafkaListenerEndpointRegistry registry,
                                                                         ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerContainerFactory,
                                                                         List<StripeEventHandlerAutoRegistry> handlersAutoRegistry) {
        super(registry, kafkaListenerContainerFactory);
        this.handlersAutoRegistry = handlersAutoRegistry;
    }


    @Override
    public List<MethodKafkaListenerEndpoint<String, Event>> getListenerEndpoints() {
        List<MethodKafkaListenerEndpoint<String, Event>> list = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        for (StripeEventHandlerAutoRegistry autoRegistry : handlersAutoRegistry) {
            try {
                String topic = autoRegistry.getEventType();
                StripeEventHandler handler = autoRegistry.getHandler();
                MethodKafkaListenerEndpoint<String, Event> endpoint = MethodKafkaListenerEndpointBuilder
                        .<String, Event>builder()
                        .id("ENDPOINT_" + atomicInteger.getAndIncrement())
                        .method(handler.getClass().getMethod("handleEvent", Event.class))
                        .autoStartup(true)
                        .bean(handler)
                        .resolver(getArgumentResolver())
                        .factory(context)
                        .topics(topic)
                        .groupId("1")
                        .build();
                list.add(endpoint);
                this.logger.info("Added MethodKafkaListenerEndpoint: {}", endpoint);
            } catch (Exception e) {
                this.logger.error("Error during listener endpoint creation.", e);
            }
        }
        return list;
    }


    @Override
    protected HandlerMethodArgumentResolver getArgumentResolver() {
        return new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter parameter) {
                return parameter.getParameter().getType().equals(Event.class);
            }

            @Override
            public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
                return message.getPayload();
            }
        };
    }
}

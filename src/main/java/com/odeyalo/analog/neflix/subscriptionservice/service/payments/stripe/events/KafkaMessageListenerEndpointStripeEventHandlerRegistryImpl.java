package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.StripeEventHandler;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.List;

/**
 * Implementation StripeEventHandlerRegistry that registry the event handlers in Kafka
 */
public class KafkaMessageListenerEndpointStripeEventHandlerRegistryImpl implements StripeEventHandlerRegistry {
    @Override
    public void addEventHandler(String eventType, StripeEventHandler handler) {
        ContainerProperties properties = new ContainerProperties(eventType);
        properties.setMessageListener(handler);
    }

    @Override
    public void removeEventHandler(Class<? extends StripeEventHandler> handler) {

    }

    @Override
    public boolean containsHandler(String eventType) {
        return false;
    }

    @Override
    public StripeEventHandler getSpecificEventHandler(String eventType, Class<? extends StripeEventHandler> event) {
        return null;
    }

    @Override
    public List<StripeEventHandler> getEventHandlers(String eventType) {
        return null;
    }
}

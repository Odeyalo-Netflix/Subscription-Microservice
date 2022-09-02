package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.StripeEventHandler;
import com.stripe.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
//todo add factory to access the executor strategy
/**
 * Execute the specific event using StripeEventHandlerContainer.
 * WARNING: This implementation is not async. You can use the KafkaListenerTriggerStripeEventHandlerExecutorStrategy to enable async support
 * @see com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.KafkaListenerTriggerStripeEventHandlerExecutorStrategy
 * @see com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.StripeEventHandlerExecutorStrategy
 * @see com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.StripeEventHandlerContainer
 */
@Component
public class StripeEventHandlerExecutorStrategyImpl implements StripeEventHandlerExecutorStrategy, StripeEventHandlerExecutorStrategyAutoRegistry {
    private final StripeEventHandlerRegistry registry;
    public static final String STRATEGY_NAME = "STRIPE_CONTAINER_NAME";

    @Autowired
    public StripeEventHandlerExecutorStrategyImpl(StripeEventHandlerRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void executeStripeEventHandler(Event event) {
        String type = event.getType();
        List<StripeEventHandler> eventHandlers = registry.getEventHandlers(type);
        if (eventHandlers != null) {
            eventHandlers.forEach(eventHandler -> eventHandler.handleEvent(event));
        }
    }

    @Override
    public String getStrategyKey() {
        return STRATEGY_NAME;
    }

    @Override
    public StripeEventHandlerExecutorStrategy getStrategy() {
        return this;
    }
}

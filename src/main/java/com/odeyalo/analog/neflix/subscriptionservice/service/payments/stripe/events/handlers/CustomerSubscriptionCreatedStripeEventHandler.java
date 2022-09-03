package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.support.StripeEventHandlerAutoRegistry;
import com.stripe.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CustomerSubscriptionCreatedStripeEventHandler implements StripeEventHandler, StripeEventHandlerAutoRegistry {
    protected final Logger logger = LoggerFactory.getLogger(CustomerSubscriptionCreatedStripeEventHandler.class);

    @Override
    public abstract void handleEvent(Event event);

    protected boolean isCorrectEventReceived(Event event) {
        return getEventType().equals(event.getType());
    }

    @Override
    public String getEventType() {
        return "customer.subscription.created";
    }

    @Override
    public StripeEventHandler getHandler() {
        return this;
    }
}

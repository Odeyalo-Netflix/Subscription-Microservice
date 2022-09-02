package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.support.StripeEventHandlerAutoRegistry;
import com.stripe.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionScheduleCreatedStripeEventHandler implements StripeEventHandler, StripeEventHandlerAutoRegistry {
    private final Logger logger = LoggerFactory.getLogger(SubscriptionScheduleCreatedStripeEventHandler.class);
    @Override
    public void handleEvent(Event event) {
        this.logger.info("Handle subscription_schedule.created event");
    }

    @Override
    public String getEventType() {
        return "subscription_schedule.created";
    }

    @Override
    public StripeEventHandler getHandler() {
        return this;
    }
}

package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.support.StripeEventHandlerAutoRegistry;
import com.stripe.model.Event;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionScheduleAbortedStripeEventHandler implements StripeEventHandler, StripeEventHandlerAutoRegistry {

    @Override
    public void handleEvent(Event event) {

    }

    @Override
    public String getEventType() {
        return "subscription_schedule.aborted";
    }

    @Override
    public StripeEventHandler getHandler() {
        return this;
    }
}

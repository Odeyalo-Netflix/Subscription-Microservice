package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.support;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.StripeEventHandler;

public interface StripeEventHandlerAutoRegistry {

    String getEventType();

    StripeEventHandler getHandler();
}

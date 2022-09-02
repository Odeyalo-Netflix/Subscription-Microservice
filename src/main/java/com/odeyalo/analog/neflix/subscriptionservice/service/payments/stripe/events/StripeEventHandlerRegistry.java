package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.StripeEventHandler;

import java.util.List;

public interface StripeEventHandlerRegistry {

    void addEventHandler(String eventType, StripeEventHandler handler);

    void removeEventHandler(Class<? extends StripeEventHandler> handler);

    boolean containsHandler(String eventType);

    StripeEventHandler getSpecificEventHandler(String eventType, Class<? extends StripeEventHandler> event);

    List<StripeEventHandler> getEventHandlers(String eventType);
}

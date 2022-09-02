package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers;

import com.stripe.model.Event;

public interface StripeEventHandler {

    void handleEvent(Event event);

}

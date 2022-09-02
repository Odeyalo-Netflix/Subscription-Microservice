package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

import com.stripe.model.Event;

/**
 * The interface represents HOW events will be executed.
 * Using a simple container or using message broker.
 *
 * @see KafkaListenerTriggerStripeEventHandlerExecutorStrategy
 * @see StripeEventHandlerExecutorStrategyImpl
 */
public interface StripeEventHandlerExecutorStrategy {

    void executeStripeEventHandler(Event event);

}

package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

/**
 * Simple registry interface that registry a class in some factory, like StripeEventHandlerExecutorStrategyFactory or registry to Kafka container
 * It can be called like markable interface that simple contains metadata that will be used during registration in container
 * @see StripeEventHandlerExecutorStrategyFactory
 */
public interface StripeEventHandlerExecutorStrategyAutoRegistry {
    /**
     * An Strategy type. Key of this strategy
     * @return - strategy name
     */
    String getStrategyKey();

    /**
     * Strategy that will be registered with specific key. It can be called Value
     * @return - StripeEventHandlerExecutorStrategy to registry
     */
    StripeEventHandlerExecutorStrategy getStrategy();
}

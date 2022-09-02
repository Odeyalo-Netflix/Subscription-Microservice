package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

/**
 * Simple factory that creates or returns already existed object
 * @see StripeEventHandlerExecutorStrategy
 */
public interface StripeEventHandlerExecutorStrategyFactory {

    /**
     * Registry the strategy in factory with event type
     * @param eventType
     * @param strategy
     */
    void registryStrategy(String eventType, StripeEventHandlerExecutorStrategy strategy);

    /**
     * Returns the strategy by type
     * @param strategyType - type of strategy
     * @return - a strategy to execute event handler
     */
    StripeEventHandlerExecutorStrategy getStrategy(String strategyType);

}

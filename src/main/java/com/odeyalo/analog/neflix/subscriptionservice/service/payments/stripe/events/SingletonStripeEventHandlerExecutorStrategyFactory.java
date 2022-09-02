package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The StripeEventHandlerExecutorStrategyFactory implementation that always returns an already existed object without creation.
 */
@Component
public class SingletonStripeEventHandlerExecutorStrategyFactory implements StripeEventHandlerExecutorStrategyFactory {
    private final Map<String, StripeEventHandlerExecutorStrategy> strategies;
    private final Logger logger = LoggerFactory.getLogger(SingletonStripeEventHandlerExecutorStrategyFactory.class);

    /**
     * Initialize the factory with StripeEventHandlerExecutorStrategyAutoRegistry that contains key and value.
     * @param strategyAutoRegistry - strategies to registry
     */
    @Autowired
    public SingletonStripeEventHandlerExecutorStrategyFactory(List<StripeEventHandlerExecutorStrategyAutoRegistry> strategyAutoRegistry) {
        this.strategies = new HashMap<>();
        for (StripeEventHandlerExecutorStrategyAutoRegistry registry : strategyAutoRegistry) {
            String strategyKey = registry.getStrategyKey();
            StripeEventHandlerExecutorStrategy strategy = registry.getStrategy();
            this.strategies.put(strategyKey, strategy);
        }
        this.logger.info("The factory was successfully initialized with: {} elements", strategyAutoRegistry.size());
    }
    /**
     * Initialize factory with already existed container
     * @param strategies - strategies to registry
     */
    public SingletonStripeEventHandlerExecutorStrategyFactory(Map<String, StripeEventHandlerExecutorStrategy> strategies) {
        this.strategies = strategies;
        this.logger.info("The factory was successfully initialized with: {} elements", strategies.size());
    }

    @Override
    public void registryStrategy(String eventType, StripeEventHandlerExecutorStrategy strategy) {
        this.strategies.put(eventType, strategy);
    }

    @Override
    public StripeEventHandlerExecutorStrategy getStrategy(String strategyType) {
        if (!strategies.containsKey(strategyType)) {
            throw new IllegalArgumentException(String.format("The strategy for the type: %s is not exist", strategyType));
        }
        return strategies.get(strategyType);
    }

    public StripeEventHandlerExecutorStrategy getStrategy(boolean isAsync) {
        if (isAsync) {
            return this.strategies.get(KafkaListenerTriggerStripeEventHandlerExecutorStrategy.STRATEGY_NAME);
        }
        return this.strategies.get(StripeEventHandlerExecutorStrategyImpl.STRATEGY_NAME);
    }
}

package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

import com.stripe.model.Event;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 *
 * The StripeEventHandlerExecutorStrategy implementation, that handle an event in async mode using Kafka broker.
 * Triggers the event handlers with specific type using Kafka.
 * Also implements StripeEventHandlerExecutorStrategyAutoRegistry to auto registry in Factory
 * @see StripeEventHandlerExecutorStrategyFactory
 */
@Component
public class KafkaListenerTriggerStripeEventHandlerExecutorStrategy implements StripeEventHandlerExecutorStrategy, StripeEventHandlerExecutorStrategyAutoRegistry {
    private final KafkaTemplate<String, Event> eventKafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(KafkaListenerTriggerStripeEventHandlerExecutorStrategy.class);
    public static final String STRATEGY_NAME = "KAFKA_STRATEGY_NAME";

    @Autowired
    public KafkaListenerTriggerStripeEventHandlerExecutorStrategy(KafkaTemplate<String, Event> eventKafkaTemplate) {
        this.eventKafkaTemplate = eventKafkaTemplate;
    }

    @SneakyThrows
    @Override
    public void executeStripeEventHandler(Event event) {
        String topic = event.getType();
        ListenableFuture<SendResult<String, Event>> result = this.eventKafkaTemplate.send(topic, event);
        result.addCallback(result1 -> logger.info("Sent: {}", result1.getProducerRecord().value().getId()),
                ex -> logger.error("Message sending finished with error", ex));
        this.logger.info("Triggered the event with type: {}", topic);
    }

    @Override
    public String getStrategyKey() {
        return STRATEGY_NAME;
    }

    @Override
    public StripeEventHandlerExecutorStrategy getStrategy() {
        return this;
    }
}

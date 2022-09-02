package com.odeyalo.analog.neflix.subscriptionservice.controller;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.KafkaListenerTriggerStripeEventHandlerExecutorStrategy;
import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.StripeEventHandlerExecutorStrategyFactory;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks")
@Log4j2
public class WebhookController {
    String endpointSecret = "whsec_2b7648180e516bf5e108a20dfc58de0f18883ddd2ae6d56988c5d81afab63159";
    private final StripeEventHandlerExecutorStrategyFactory factory;
    public WebhookController(@Qualifier("singletonStripeEventHandlerExecutorStrategyFactory") StripeEventHandlerExecutorStrategyFactory factory) {
        this.factory = factory;
    }

    @PostMapping("/stripe")
    public void webhookStripeEvents(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, signature, endpointSecret);
        log.info("Received event with type: {}", event.getType());
        this.factory.getStrategy(KafkaListenerTriggerStripeEventHandlerExecutorStrategy.STRATEGY_NAME).executeStripeEventHandler(event);
    }
}

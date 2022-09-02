package com.odeyalo.analog.neflix.subscriptionservice.config;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.WebhookEndpoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class StripeConfiguration {

    @Value("${app.payment.stripe.keys.secret}")
    private String stripeApiKey;
    @Value("${app.payment.stripe.url}")
    private String url;

    @Bean
    void initializeStripeWithApiKey() {
        Stripe.apiKey = stripeApiKey;
    }

    @Bean
    void  initializeWebhooks() throws StripeException {
        List<Object> enabledEvents = new ArrayList<>();
        enabledEvents.add("subscription_schedule.aborted");
        enabledEvents.add("subscription_schedule.canceled");
        enabledEvents.add("subscription_schedule.completed");
        enabledEvents.add("subscription_schedule.created");
        enabledEvents.add("subscription_schedule.expiring");
        enabledEvents.add("subscription_schedule.released");
        enabledEvents.add("subscription_schedule.updated");
        Map<String, Object> params = new HashMap<>();
        params.put("url", url);
        params.put("enabled_events", enabledEvents);
        WebhookEndpoint.create(params);
    }
}

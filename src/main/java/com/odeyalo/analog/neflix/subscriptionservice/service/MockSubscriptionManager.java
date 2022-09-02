package com.odeyalo.analog.neflix.subscriptionservice.service;

import com.odeyalo.analog.neflix.subscriptionservice.common.StartSubscriptionData;
import com.odeyalo.analog.neflix.subscriptionservice.entity.SubscriptionInformation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MockSubscriptionManager implements SubscriptionManager {
    private final Map<String, SubscriptionInformation> subscriptions = new ConcurrentHashMap<>();

    @Override
    public void startSubscription(StartSubscriptionData data) {
        String userId = data.getUserId();
        SubscriptionInformation subscriptionInformation = new SubscriptionInformation(userId, true, LocalDate.now(), LocalDate.now().plusDays(30));
        this.subscriptions.put(userId, subscriptionInformation);
    }

    @Override
    public void cancelSubscription(String userId) {
        this.subscriptions.remove(userId);
    }

    @Override
    public SubscriptionInformation getSubscriptionInfo(String userId) {
        return this.subscriptions.get(userId);
    }
}

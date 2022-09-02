package com.odeyalo.analog.neflix.subscriptionservice.service;

import com.odeyalo.analog.neflix.subscriptionservice.common.StartSubscriptionData;
import com.odeyalo.analog.neflix.subscriptionservice.entity.SubscriptionInformation;

public interface SubscriptionManager {

    void startSubscription(StartSubscriptionData data);

    void cancelSubscription(String userId);

    SubscriptionInformation getSubscriptionInfo(String userId);
}

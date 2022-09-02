package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.subscriptions;

import com.odeyalo.analog.neflix.subscriptionservice.config.security.AuthenticatedUserInformation;
import com.odeyalo.analog.neflix.subscriptionservice.dto.StripeSubscriptionPaymentInfo;
import com.odeyalo.analog.neflix.subscriptionservice.entity.StripeSubscriptionInformation;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;

public interface StripeSubscriptionService {

    Subscription createSubscription(String customerId, StripeSubscriptionPaymentInfo info) throws StripeException;
}

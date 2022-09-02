package com.odeyalo.analog.neflix.subscriptionservice.service;

import com.odeyalo.analog.neflix.subscriptionservice.common.StartStripeSubscriptionData;
import com.odeyalo.analog.neflix.subscriptionservice.common.StartSubscriptionData;
import com.odeyalo.analog.neflix.subscriptionservice.config.security.AuthenticatedUserInformation;
import com.odeyalo.analog.neflix.subscriptionservice.dto.StripeSubscriptionPaymentInfo;
import com.odeyalo.analog.neflix.subscriptionservice.entity.StripeSubscriptionInformation;
import com.odeyalo.analog.neflix.subscriptionservice.entity.SubscriptionInformation;
import com.odeyalo.analog.neflix.subscriptionservice.repository.StripeSubscriptionInformationRepository;
import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.StripeCustomerCreator;
import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.subscriptions.StripeSubscriptionService;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
public class StripeSubscriptionManager implements SubscriptionManager {
    private final StripeSubscriptionInformationRepository repository;
    private final StripeCustomerCreator customerCreator;
    private final StripeSubscriptionService subscriptionCreator;
    private final Logger logger = LoggerFactory.getLogger(StripeSubscriptionManager.class);

    @Autowired
    public StripeSubscriptionManager(StripeSubscriptionInformationRepository repository, StripeCustomerCreator customerCreator, StripeSubscriptionService subscriptionCreator) {
        this.repository = repository;
        this.customerCreator = customerCreator;
        this.subscriptionCreator = subscriptionCreator;
    }

    @Override
    public void startSubscription(StartSubscriptionData data) {
        if (!(data instanceof StartStripeSubscriptionData)) {
            throw new IllegalArgumentException("Data must be of type: {}" + StartStripeSubscriptionData.class.getName());
        }
        StartStripeSubscriptionData subscriptionData = (StartStripeSubscriptionData) data;
        try {
            String userId = subscriptionData.getUserId();
            String tokenId = subscriptionData.getTokenId();
            AuthenticatedUserInformation info = (AuthenticatedUserInformation) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Customer customer = customerCreator.createCustomer(info);
            Subscription subscription = subscriptionCreator.createSubscription(customer.getId(), new StripeSubscriptionPaymentInfo("PREMIUM", tokenId, info.getUsername()));
            StripeSubscriptionInformation stripeSubscriptionInformation = StripeSubscriptionInformation.builder()
                    .stripeUserId(customer.getId())
                    .stripeSubscriptionId(subscription.getId())
                    .userId(userId)
                    .activeTo(LocalDate.of(2022, Month.AUGUST, 30))
                    .started(LocalDate.now())
                    .isActive(true)
                    .build();
            this.repository.save(stripeSubscriptionInformation);
        } catch (Exception e) {
            this.logger.error("Error was occurred", e);
        }
    }

    @Override
    public void cancelSubscription(String userId) {
        StripeSubscriptionInformation info = this.repository.findStripeSubscriptionInformationByStripeUserId(userId);
        info.setActive(false);
    }

    @Override
    public SubscriptionInformation getSubscriptionInfo(String userId) {
        StripeSubscriptionInformation info = repository.findStripeSubscriptionInformationByUserId(userId);
        System.out.println(info);
        return info;
    }
}

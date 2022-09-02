package com.odeyalo.analog.neflix.subscriptionservice.repository;

import com.odeyalo.analog.neflix.subscriptionservice.entity.StripeSubscriptionInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StripeSubscriptionInformationRepository extends JpaRepository<StripeSubscriptionInformation, String> {

    StripeSubscriptionInformation findStripeSubscriptionInformationByStripeUserId(String stripeUserId);

    StripeSubscriptionInformation findStripeSubscriptionInformationByUserId(String userId);
}

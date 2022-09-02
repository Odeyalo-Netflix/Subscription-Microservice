package com.odeyalo.analog.neflix.subscriptionservice.repository;

import com.odeyalo.analog.neflix.subscriptionservice.entity.SubscriptionInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionInformationRepository extends JpaRepository<SubscriptionInformation, String> {
}

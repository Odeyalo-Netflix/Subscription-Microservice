package com.odeyalo.analog.neflix.subscriptionservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "subscriptionId")
@SuperBuilder
public class StripeSubscriptionInformation extends SubscriptionInformation {
    @Column(nullable = false)
    private String stripeUserId; //An id of user in Stripe system
    @Column(nullable = false)
    private String stripeSubscriptionId; //An id of subscription in Stripe
}

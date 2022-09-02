package com.odeyalo.analog.neflix.subscriptionservice.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@SuperBuilder
public class StartStripeSubscriptionData extends StartSubscriptionData {
    private String tokenId;

    public StartStripeSubscriptionData(String userId, String plan, String tokenId) {
        super(userId, plan);
        this.tokenId = tokenId;
    }
}

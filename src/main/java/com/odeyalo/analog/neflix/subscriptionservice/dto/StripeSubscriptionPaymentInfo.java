package com.odeyalo.analog.neflix.subscriptionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeSubscriptionPaymentInfo {
    public String subscriptionPlan;
    private String tokenId;
    private String email;
}

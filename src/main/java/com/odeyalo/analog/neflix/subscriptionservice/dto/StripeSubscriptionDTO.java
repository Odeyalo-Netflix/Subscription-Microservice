package com.odeyalo.analog.neflix.subscriptionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StripeSubscriptionDTO {
    private String publicKey;
    private String subscriptionPlan;
}

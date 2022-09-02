package com.odeyalo.analog.neflix.subscriptionservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartSubscriptionData {
    protected String userId;
    protected String plan;
}

package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe;

import com.odeyalo.analog.neflix.subscriptionservice.config.security.AuthenticatedUserInformation;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;

import java.util.Map;

public interface StripeCustomerCreator {

    Customer createCustomer(AuthenticatedUserInformation userInfo) throws StripeException;


    void overrideDefaultCustomerParameters(Map<String, Object> params);
}

package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe;

import com.odeyalo.analog.neflix.subscriptionservice.config.security.AuthenticatedUserInformation;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class DefaultStripeCustomerCreator implements StripeCustomerCreator {
    private Map<String, Object> params = new HashMap<>();
    {
        params.put("description", "This is my user");
    }
    @Override
    public Customer createCustomer(AuthenticatedUserInformation userInfo) throws StripeException {
        params.put("description", "this is user with id: {}" + userInfo.getId());
        return Customer.create(params);
    }

    @Override
    public void overrideDefaultCustomerParameters(Map<String, Object> params) {
        this.params = params;
    }
}

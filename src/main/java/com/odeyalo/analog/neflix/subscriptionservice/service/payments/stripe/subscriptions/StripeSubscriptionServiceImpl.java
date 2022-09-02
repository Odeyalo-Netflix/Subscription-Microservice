package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.subscriptions;

import com.odeyalo.analog.neflix.subscriptionservice.dto.StripeSubscriptionPaymentInfo;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StripeSubscriptionServiceImpl implements StripeSubscriptionService {

    @Override
    public Subscription createSubscription(String customerId, StripeSubscriptionPaymentInfo info) throws StripeException {
        List<Object> items = new ArrayList<>();
        System.out.println(info);
        Map<String, Object> item1 = new HashMap<>();
        item1.put("price", "price_1LaiE9AiIen9m7IvDFOv800o");
        items.add(item1);
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);
        params.put("items", items);
        params.put("token", info.getTokenId());
        return Subscription.create(params);
    }
}

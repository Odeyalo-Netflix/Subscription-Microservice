package com.odeyalo.analog.neflix.subscriptionservice;

import com.google.gson.Gson;
import com.odeyalo.analog.neflix.subscriptionservice.common.StartStripeSubscriptionData;
import com.odeyalo.analog.neflix.subscriptionservice.common.StartSubscriptionData;
import com.stripe.model.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class SubscriptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionServiceApplication.class, args);
//        String json = "{\n" +
//                "  \"userId\": \"1\",\n" +
//                "  \"plan\": \"PREMIUM\",\n" +
//                "  \"token\": \"1\"\n" +
//                "}";
//        Event data = new Event();
//        Gson gson = new Gson();
//        Event data1 = gson.fromJson(json, Event.class);
//        System.out.println(data1);
    }

}

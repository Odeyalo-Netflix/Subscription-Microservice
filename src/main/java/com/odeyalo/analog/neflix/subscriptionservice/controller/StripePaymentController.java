package com.odeyalo.analog.neflix.subscriptionservice.controller;

import com.odeyalo.analog.neflix.subscriptionservice.common.StartStripeSubscriptionData;
import com.odeyalo.analog.neflix.subscriptionservice.common.StartSubscriptionData;
import com.odeyalo.analog.neflix.subscriptionservice.config.security.AuthenticatedUserInformation;
import com.odeyalo.analog.neflix.subscriptionservice.dto.StripeSubscriptionPaymentInfo;
import com.odeyalo.analog.neflix.subscriptionservice.entity.SubscriptionInformation;
import com.odeyalo.analog.neflix.subscriptionservice.service.SubscriptionManager;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/payment/stripe")
@CrossOrigin(origins = "*")
public class StripePaymentController {
    private final SubscriptionManager subscriptionManager;
//    @Autowired
//    private KafkaTemplate<String, StartSubscriptionData> template;

    @Autowired
    public StripePaymentController(@Qualifier("stripeSubscriptionManager") SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    @PostMapping("/session/create")
    public void createPaymentSession(HttpServletResponse res) throws StripeException, IOException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("http://localhost:3000/success")
                .setCancelUrl("http://localhost:3000/cancel")
                .setClientReferenceId(getUserId())
                .putMetadata("userId", getUserId())
                .setSubscriptionData(SessionCreateParams.SubscriptionData.builder().putMetadata("userId", getUserId()).build())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice("price_1LaiE9AiIen9m7IvDFOv800o").build())
                .build();
        Session session = Session.create(params);
        res.sendRedirect(session.getUrl());
    }


    @GetMapping("/subscription/status")
    public ResponseEntity<?> currentSubscriptionUserStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedUserInformation info = (AuthenticatedUserInformation) authentication.getPrincipal();
        SubscriptionInformation subscriptionInfo = this.subscriptionManager.getSubscriptionInfo(info.getId());
        return ResponseEntity.ok(subscriptionInfo);
    }

    @PostMapping("/subscription/start")
    public ResponseEntity<?> startSubscription(Authentication authentication, @RequestBody StripeSubscriptionPaymentInfo body) {
        AuthenticatedUserInformation info = (AuthenticatedUserInformation) authentication.getPrincipal();
        String id = info.getId();
        this.subscriptionManager.startSubscription(new StartStripeSubscriptionData(id, body.getSubscriptionPlan(), body.getTokenId()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/subscription/cancel")
    public ResponseEntity<?> cancelUserSubscription(Authentication authentication) {
        AuthenticatedUserInformation info = (AuthenticatedUserInformation) authentication.getPrincipal();
        String id = info.getId();
        this.subscriptionManager.cancelSubscription(id);
        return ResponseEntity.ok().build();
    }
    private String getUserId() {
        AuthenticatedUserInformation principal = (AuthenticatedUserInformation) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return principal.getId();
    }


//    @RequestMapping("/kafka/send")
//    public void send(@RequestBody StartSubscriptionData data) {
//        this.template.send("topicName", data);
//    }
}

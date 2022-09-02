package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers;

import com.odeyalo.analog.neflix.subscriptionservice.entity.StripeSubscriptionInformation;
import com.odeyalo.analog.neflix.subscriptionservice.repository.StripeSubscriptionInformationRepository;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class UpdateUserCustomerSubscriptionCreatedStripeEventHandler extends CustomerSubscriptionCreatedStripeEventHandler {
    private final StripeSubscriptionInformationRepository repository;

    public UpdateUserCustomerSubscriptionCreatedStripeEventHandler(StripeSubscriptionInformationRepository repository) {
        this.repository = repository;
    }


    @Override
    public void handleEvent(Event event) {
        if (!getEventType().equals(event.getType())) {
            this.logger.error("Wrong event parameter was received: {}", event);
            return;
        }
        Optional<StripeObject> optional = event.getDataObjectDeserializer().getObject();
        if (optional.isEmpty()) {
            this.logger.error("Stripe object is not presented!, {}", event);
            return;
        }

        StripeObject stripeObject = optional.get();
        Subscription subscription = (Subscription) stripeObject;
        StripeSubscriptionInformation subscriptionInformation = buildStripeSubscriptionInformation(subscription);
        this.repository.save(subscriptionInformation);
        this.logger.info("Saved stripe subscription info with data: {}", subscriptionInformation);
    }

    protected StripeSubscriptionInformation buildStripeSubscriptionInformation(Subscription subscription) {
        LocalDate endedAt = Instant.ofEpochSecond(subscription.getCurrentPeriodEnd())
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate startedAt = Instant.ofEpochSecond(subscription.getCurrentPeriodStart())
                .atZone(ZoneId.systemDefault()).toLocalDate();
        String userId = subscription.getMetadata().get("userId");
        String customerId = subscription.getCustomer();
        String subscriptionId = subscription.getId();

        return StripeSubscriptionInformation.builder()
                .stripeSubscriptionId(subscriptionId)
                .stripeUserId(customerId)
                .isActive(true)
                .activeTo(endedAt)
                .started(startedAt)
                .userId(userId)
                .build();
    }
}

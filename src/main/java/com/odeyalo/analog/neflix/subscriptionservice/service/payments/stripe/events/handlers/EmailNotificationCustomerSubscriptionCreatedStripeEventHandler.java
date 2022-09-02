package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers;
import com.odeyalo.support.clients.notification.dto.EmailMessageDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmailNotificationCustomerSubscriptionCreatedStripeEventHandler extends CustomerSubscriptionCreatedStripeEventHandler {
    private final KafkaTemplate<String, EmailMessageDTO> template;
    private static final String EMAIL_NOTIFICATION_KAFKA_TOPIC = "MAIL_MESSAGE_SENDER_TOPIC";

    @Autowired
    public EmailNotificationCustomerSubscriptionCreatedStripeEventHandler(KafkaTemplate<String, EmailMessageDTO> template) {
        this.template = template;
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
        String customer = subscription.getCustomer();
        try {
            String email = Customer.retrieve(customer).getEmail();
            this.template.send(EMAIL_NOTIFICATION_KAFKA_TOPIC,
                    new EmailMessageDTO(email,
                            "Congratulation! You are a Premium member now. Enjoy your favourite films in the best quality with ad-free!",
                            "You are Premium member now!"));
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}

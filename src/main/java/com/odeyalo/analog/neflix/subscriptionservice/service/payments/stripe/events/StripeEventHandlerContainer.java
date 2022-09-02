package com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events;

import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.StripeEventHandler;
import com.odeyalo.analog.neflix.subscriptionservice.service.payments.stripe.events.handlers.support.StripeEventHandlerAutoRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * StripeEventHandlerRegistry implementation that registry event handlers in Map and can registry handlers using StripeEventHandlerAutoRegistry
 * @see StripeEventHandlerRegistry
 * @see StripeEventHandlerAutoRegistry
 */
@Component
public class StripeEventHandlerContainer implements StripeEventHandlerRegistry {
    private final Map<String, List<StripeEventHandler>> handlers;
    private final Logger logger = LoggerFactory.getLogger(StripeEventHandlerContainer.class);

    /**
     * Initialize the container using list of StripeEventHandlerAutoRegistry
     * @see StripeEventHandlerAutoRegistry
     * @param autoRegistries - handlers definition that will be used to registry
     */
    @Autowired
    public StripeEventHandlerContainer(List<StripeEventHandlerAutoRegistry> autoRegistries) {
        this.handlers = new HashMap<>();
        for (StripeEventHandlerAutoRegistry autoRegistry : autoRegistries) {
            String eventType = autoRegistry.getEventType();
            StripeEventHandler handler = autoRegistry.getHandler();
            this.addEventHandler(eventType, handler);
        }
    }

    /**
     * Initialize a container with already existed handlers
     * @param handlers
     */
    public StripeEventHandlerContainer(Map<String, List<StripeEventHandler>> handlers) {
        this.handlers = handlers;
    }


    @Override
    public void addEventHandler(String eventType, StripeEventHandler handler) {
        List<StripeEventHandler> handlersList = this.handlers.computeIfAbsent(eventType, k -> new ArrayList<>());
        handlersList.add(handler);
        this.handlers.put(eventType, handlersList);
        this.logger.info("Registered event type: {}, handler: {}", eventType, handler);
    }

    @Override
    public void removeEventHandler(Class<? extends StripeEventHandler> handler) {
        for (Map.Entry<String, List<StripeEventHandler>> entry : this.handlers.entrySet()) {
            List<StripeEventHandler> handlersList = entry.getValue();
            handlersList.removeIf(eventHandler -> eventHandler.getClass() == handler);
        }
    }

    @Override
    public boolean containsHandler(String eventType) {
        return this.handlers.containsKey(eventType);
    }

    @Override
    public StripeEventHandler getSpecificEventHandler(String eventType, Class<? extends StripeEventHandler> event) {
        List<StripeEventHandler> stripeEventHandlers = this.handlers.get(eventType);
        if (stripeEventHandlers != null) {
            for (StripeEventHandler handler : stripeEventHandlers) {
                if (handler.getClass() == event) {
                    return handler;
                }
            }
        }
        return null;
    }

    @Override
    public List<StripeEventHandler> getEventHandlers(String eventType) {
        return handlers.get(eventType);
    }
}

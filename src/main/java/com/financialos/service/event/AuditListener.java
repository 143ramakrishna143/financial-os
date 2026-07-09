package com.financialos.service.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditListener {
    private final Logger logger = LoggerFactory.getLogger(AuditListener.class);

    @EventListener
    public void onTransactionCreated(TransactionCreatedEvent event) {
        // Simple audit: log to console. Extend to persist audit entries later.
        logger.info("TransactionCreatedEvent fired for transaction: {}", event.getTransaction().getTransactionReference());
    }
}


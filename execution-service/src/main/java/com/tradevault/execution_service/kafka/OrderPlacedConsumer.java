package com.tradevault.execution_service.kafka;

import com.tradevault.execution_service.service.ExecutionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedConsumer {

    private final ExecutionService executionService;

    public OrderPlacedConsumer(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @KafkaListener(topics = "orders.placed", groupId = "execution-service-group")
    public void consume(OrderPlacedEvent event) {
        executionService.processOrder(event);
    }
}
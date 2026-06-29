package com.tradevault.order_service.service;

import com.tradevault.order_service.dto.PlaceOrderRequest;
import com.tradevault.order_service.entity.Order;
import com.tradevault.order_service.entity.OrderStatus;
import com.tradevault.order_service.exception.DuplicateOrderException;
import com.tradevault.order_service.kafka.OrderEventProducer;
import com.tradevault.order_service.kafka.OrderPlacedEvent;
import com.tradevault.order_service.repository.OrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
    }

    @Transactional
    public Order placeOrder(PlaceOrderRequest request) {

        Optional<Order> existing = orderRepository.findByIdempotencyKey(request.getIdempotencyKey());
        if (existing.isPresent()) {
            return existing.get();
        }

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setIdempotencyKey(request.getIdempotencyKey());
        order.setSymbol(request.getSymbol());
        order.setOrderType(request.getOrderType());
        order.setSide(request.getSide());
        order.setQuantity(request.getQuantity());
        order.setLimitPrice(request.getLimitPrice());
        order.setStatus(OrderStatus.PLACED);

        Order saved;
        try {
            saved = orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            return orderRepository.findByIdempotencyKey(request.getIdempotencyKey())
                    .orElseThrow(() -> new DuplicateOrderException(
                            "Duplicate order detected but original could not be found"));
        }

        OrderPlacedEvent event = new OrderPlacedEvent(
                saved.getId(),
                saved.getUserId(),
                saved.getSymbol(),
                saved.getSide(),
                saved.getQuantity(),
                saved.getLimitPrice(),
                saved.getIdempotencyKey()
        );
        orderEventProducer.publish(event);

        return saved;
    }
}
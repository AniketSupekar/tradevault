package com.tradevault.order_service.service;

import com.tradevault.order_service.dto.PlaceOrderRequest;
import com.tradevault.order_service.entity.Order;
import com.tradevault.order_service.entity.OrderStatus;
import com.tradevault.order_service.exception.DuplicateOrderException;
import com.tradevault.order_service.repository.OrderRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order placeOrder(PlaceOrderRequest request) {

        // Optional fast-path check — not the real guarantee, just avoids
        // unnecessary work on obvious repeat calls. The DB constraint below
        // is what actually prevents the race condition.
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

        try {
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            // Two requests raced past the check above at the same time.
            // The DB's unique constraint on idempotency_key rejected the
            // second insert — this is the real safety net.
            return orderRepository.findByIdempotencyKey(request.getIdempotencyKey())
                    .orElseThrow(() -> new DuplicateOrderException(
                            "Duplicate order detected but original could not be found"));
        }
    }
}
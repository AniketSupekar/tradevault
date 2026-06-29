package com.tradevault.order_service.kafka;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class OrderPlacedEvent {

    private UUID orderId;
    private UUID userId;
    private String symbol;
    private String side;
    private Integer quantity;
    private BigDecimal limitPrice;
    private String idempotencyKey;
    private Instant timestamp;

    public OrderPlacedEvent() {}

    public OrderPlacedEvent(UUID orderId, UUID userId, String symbol, String side,
                             Integer quantity, BigDecimal limitPrice, String idempotencyKey) {
        this.orderId = orderId;
        this.userId = userId;
        this.symbol = symbol;
        this.side = side;
        this.quantity = quantity;
        this.limitPrice = limitPrice;
        this.idempotencyKey = idempotencyKey;
        this.timestamp = Instant.now();
    }

    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public String getSymbol() { return symbol; }
    public String getSide() { return side; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getLimitPrice() { return limitPrice; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public Instant getTimestamp() { return timestamp; }
}
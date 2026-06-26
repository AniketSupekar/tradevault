package com.tradevault.order_service.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public class PlaceOrderRequest {

    @NotNull
    private UUID userId;

    @NotBlank
    private String idempotencyKey;

    @NotBlank
    private String symbol;

    @NotBlank
    private String orderType; // MARKET or LIMIT

    @NotBlank
    private String side; // BUY or SELL

    @NotNull
    @Positive
    private Integer quantity;

    private BigDecimal limitPrice; // optional, only for LIMIT orders

    // --- getters and setters ---

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getIdempotencyKey() { return idempotencyKey; }
    public void setIdempotencyKey(String idempotencyKey) { this.idempotencyKey = idempotencyKey; }

    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    public String getSide() { return side; }
    public void setSide(String side) { this.side = side; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getLimitPrice() { return limitPrice; }
    public void setLimitPrice(BigDecimal limitPrice) { this.limitPrice = limitPrice; }
}
package com.tradevault.execution_service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "executions")
public class Execution {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "executed_price", nullable = false)
    private BigDecimal executedPrice;

    @Column(name = "filled_quantity", nullable = false)
    private Integer filledQuantity;

    @Column(nullable = false)
    private String venue;

    @Column(name = "executed_at", nullable = false)
    private Instant executedAt;

    @PrePersist
    protected void onCreate() {
        executedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public BigDecimal getExecutedPrice() { return executedPrice; }
    public void setExecutedPrice(BigDecimal executedPrice) { this.executedPrice = executedPrice; }

    public Integer getFilledQuantity() { return filledQuantity; }
    public void setFilledQuantity(Integer filledQuantity) { this.filledQuantity = filledQuantity; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public Instant getExecutedAt() { return executedAt; }
}
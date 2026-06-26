package com.tradevault.order_service.entity;

public enum OrderStatus {
    PENDING_VALIDATION,
    PLACED,
    PARTIALLY_FILLED,
    FILLED,
    SETTLED,
    REJECTED,
    CANCELLED
}
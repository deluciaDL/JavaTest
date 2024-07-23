package com.example.hexagonal.application.dto;

import com.example.hexagonal.domain.model.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class CreatePriceRequest {

    private Long brandId;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Long feeId;
    private Long productId;
    private int priority;
    private BigDecimal amount;
    private Currency currency;

    public CreatePriceRequest(Long brandId, LocalDateTime dateFrom, LocalDateTime dateTo, Long feeId, Long productId,
            int priority, BigDecimal amount, Currency currency) {
        this.brandId = brandId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.feeId = feeId;
        this.productId = productId;
        this.priority = priority;
        this.amount = amount;
        this.currency = currency;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "CreatePriceRequest{" + "brandId=" + brandId + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo
                + ", feeId=" + feeId + ", productId=" + productId + ", priority=" + priority + ", amount=" + amount
                + ", currency=" + currency + '}';
    }
}

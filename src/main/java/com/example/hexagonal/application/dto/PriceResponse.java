package com.example.hexagonal.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceResponse {

    private Long productId;
    private Long brandId;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Long feeId;
    private BigDecimal amount;

    public PriceResponse(Long productId, Long brandId, LocalDateTime dateFrom, LocalDateTime dateTo, Long feeId, BigDecimal amount) {
        this.productId = productId;
        this.brandId = brandId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.feeId = feeId;
        this.amount = amount;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

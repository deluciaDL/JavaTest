package com.example.hexagonal.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceResponse {

    private Long productId;
    private Long brandId;
    @JsonFormat(pattern = "yyyy-MM-dd:HH.mm.ss")
    private LocalDateTime dateFrom;
    @JsonFormat(pattern = "yyyy-MM-dd:HH.mm.ss")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceResponse that = (PriceResponse) o;

        return new EqualsBuilder().append(productId, that.productId).append(brandId, that.brandId)
                .append(dateFrom, that.dateFrom).append(dateTo, that.dateTo).append(feeId, that.feeId)
                .append(amount, that.amount).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(productId).append(brandId).append(dateFrom).append(dateTo)
                .append(feeId).append(amount).toHashCode();
    }

    @Override
    public String toString() {
        return "PriceResponse{" + "productId=" + productId + ", brandId=" + brandId + ", dateFrom=" + dateFrom
                + ", dateTo=" + dateTo + ", feeId=" + feeId + ", amount=" + amount + '}';
    }
}

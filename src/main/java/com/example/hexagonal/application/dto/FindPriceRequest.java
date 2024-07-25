package com.example.hexagonal.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;

public class FindPriceRequest {

    @NotNull(message = "Date cannot be null")
    private LocalDateTime date;

    @NotNull(message = "brandId cannot be null")
    @Positive(message = "This value must be positive")
    private Long brandId;

    @NotNull(message = "productId cannot be null")
    @Positive(message = "This value must be positive")
    private Long productId;

    public FindPriceRequest() {
    }

    public FindPriceRequest(LocalDateTime date, Long brandId, Long productId) {
        this.date = date;
        this.brandId = brandId;
        this.productId = productId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FindPriceRequest request = (FindPriceRequest) o;

        return new EqualsBuilder().append(date, request.date).append(brandId, request.brandId)
                .append(productId, request.productId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(date).append(brandId).append(productId).toHashCode();
    }

    @Override
    public String toString() {
        return "FindPriceRequest{" + "date=" + date + ", brandId=" + brandId + ", productId=" + productId + '}';
    }
}

package com.example.hexagonal.infrastructure.jpa.mapper;

import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.infrastructure.jpa.entity.JpaPrice;

public class JpaPriceMapper {

    private JpaPriceMapper() {
    }

    // From price domain object to jpa entity object
    public static JpaPrice toJpaPrice(Price price) {
        JpaPrice jpaPrice = new JpaPrice();
        jpaPrice.setId(price.getId());
        jpaPrice.setBrandId(price.getBrandId());
        jpaPrice.setDateFrom(price.getDateFrom());
        jpaPrice.setDateTo(price.getDateTo());
        jpaPrice.setFeeId(price.getFeeId());
        jpaPrice.setProductId(price.getProductId());
        jpaPrice.setPriority(price.getPriority());
        jpaPrice.setAmount(price.getAmount());
        jpaPrice.setCurrency(price.getCurrency());
        return jpaPrice;
    }

    // From jpa entity object to price domain object
    public static Price toDomainPrice(JpaPrice jpaPrice) {
        return new Price(jpaPrice.getId(), jpaPrice.getBrandId(), jpaPrice.getDateFrom(), jpaPrice.getDateTo(),
                jpaPrice.getFeeId(), jpaPrice.getProductId(), jpaPrice.getPriority(), jpaPrice.getAmount(),
                jpaPrice.getCurrency());
    }
}

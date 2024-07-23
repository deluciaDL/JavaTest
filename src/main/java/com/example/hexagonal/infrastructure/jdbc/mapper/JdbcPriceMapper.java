package com.example.hexagonal.infrastructure.jdbc.mapper;

import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.infrastructure.jdbc.entity.JdbcPrice;
import org.springframework.stereotype.Component;

@Component
public class JdbcPriceMapper {
    // Convert domain model Price to jdbc entity jdbcPrice
    public JdbcPrice toEntity(Price price) {
        JdbcPrice jdbcPrice = new JdbcPrice();
        jdbcPrice.setId(price.getId());
        jdbcPrice.setProductId(price.getProductId());
        jdbcPrice.setBrandId(price.getBrandId());
        jdbcPrice.setCurrency(price.getCurrency());
        jdbcPrice.setAmount(price.getAmount());
        jdbcPrice.setDateFrom(price.getDateFrom());
        jdbcPrice.setDateTo(price.getDateTo());
        jdbcPrice.setPriority(price.getPriority());
        jdbcPrice.setFeeId(price.getFeeId());
        return jdbcPrice;
    }

    // Convert jdbc entity jdbcPrice to domain model Price
    public Price fromEntity(JdbcPrice jdbcPrice) {
        return new Price(jdbcPrice.getId(), jdbcPrice.getBrandId(), jdbcPrice.getDateFrom(), jdbcPrice.getDateTo(),
                jdbcPrice.getFeeId(), jdbcPrice.getProductId(), jdbcPrice.getPriority(), jdbcPrice.getAmount(),
                jdbcPrice.getCurrency());
    }
}

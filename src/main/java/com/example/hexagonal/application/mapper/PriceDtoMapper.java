package com.example.hexagonal.application.mapper;

import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.domain.model.Price;

public class PriceDtoMapper {

    private PriceDtoMapper() {
    }

    // Convert domain model Price to PriceResponse dto
    public static PriceResponse toPriceResponse(Price price) {
        return new PriceResponse(price.getProductId(), price.getBrandId(), price.getDateFrom(), price.getDateTo(),
                price.getFeeId(), price.getAmount());
    }
}

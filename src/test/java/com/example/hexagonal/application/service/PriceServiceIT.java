package com.example.hexagonal.application.service;

import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.application.service.PriceService;
import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class PriceServiceIT {

    @Autowired
    private PriceService priceService;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        // Setup test data
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateFrom = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).minusDays(1);
        LocalDateTime dateTo = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).plusDays(1);
        Long feeId = 3L;
        BigDecimal amount = BigDecimal.valueOf(10.50);

        Price price = new Price(1L, brandId, dateFrom, dateTo, feeId, productId, 0, amount, Currency.EUR);
        priceRepository.save(price);
    }

    @Test
    void testPriceServiceFindPriceIT() {

        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.APRIL,10,16,0,0);

        Long expectedProductId = 30L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).minusDays(1);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).plusDays(1);
        Long expectedFeeId = 3L;
        BigDecimal expectedAmount = BigDecimal.valueOf(10.50);

        // When
        PriceResponse response = priceService.findPrice(date, brandId, productId);

        // Then
        assertEquals(expectedProductId, response.getProductId());
        assertEquals(expectedBrandId, response.getBrandId());
        assertEquals(expectedDateFrom, response.getDateFrom());
        assertEquals(expectedDateTo, response.getDateTo());
        assertEquals(expectedFeeId, response.getFeeId());
        assertEquals(expectedAmount, response.getAmount());
    }
}

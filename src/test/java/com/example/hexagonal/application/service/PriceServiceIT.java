package com.example.hexagonal.application.service;

import com.example.hexagonal.application.dto.FindPriceRequest;
import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.domain.exception.PriceNotFoundException;
import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class PriceServiceIT {

    @Autowired
    private PriceService priceService;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        // Clean the DB first
        priceRepository.deleteAll();

        // Setup test data
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateFrom = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).minusDays(1);
        LocalDateTime dateTo = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).plusDays(1);
        Long feeId = 3L;
        BigDecimal amount = BigDecimal.valueOf(10.50);

        Price price = new Price(brandId, dateFrom, dateTo, feeId, productId, 0, amount, Currency.EUR);
        priceRepository.save(price);
    }

    @Test
    void testFindPriceIT() {

        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.APRIL,10,16,0,0);
        FindPriceRequest request = new FindPriceRequest(date, brandId, productId);

        Long expectedProductId = 30L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).minusDays(1);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).plusDays(1);
        Long expectedFeeId = 3L;
        BigDecimal expectedAmount = BigDecimal.valueOf(10.50);

        // When
        PriceResponse response = priceService.findPrice(request);

        // Then
        assertEquals(expectedProductId, response.getProductId());
        assertEquals(expectedBrandId, response.getBrandId());
        assertEquals(expectedDateFrom, response.getDateFrom());
        assertEquals(expectedDateTo, response.getDateTo());
        assertEquals(expectedFeeId, response.getFeeId());
        assertEquals(expectedAmount, response.getAmount());
    }

    @Test
    void testFindPriceWrongParamFindPriceRequest() {

        // Given
        Long productId = 1L;
        Long negativeProductId = -1L;
        Long negativeBrandId = -1L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.APRIL,10,16,0,0);;

        FindPriceRequest requestNegativeBrandId = new FindPriceRequest(date, negativeBrandId, productId);
        FindPriceRequest requestNoDate = new FindPriceRequest(null, brandId, productId);
        FindPriceRequest requestNegativeProductId = new FindPriceRequest(date, brandId, negativeProductId);
        FindPriceRequest requestNoBrand = new FindPriceRequest(date, null, productId);
        FindPriceRequest requestNoProduct = new FindPriceRequest(date, brandId, null);
        FindPriceRequest requestMultipleInvalidFields = new FindPriceRequest(null, negativeBrandId, null);

        // When && Then
        assertThrows(ConstraintViolationException.class, () -> {
            priceService.findPrice(requestNegativeBrandId);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            priceService.findPrice(requestNegativeProductId);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            priceService.findPrice(requestNoDate);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            priceService.findPrice(requestNoBrand);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            priceService.findPrice(requestNoProduct);
        });

        assertThrows(ConstraintViolationException.class, () -> {
            priceService.findPrice(requestMultipleInvalidFields);
        });


    }

    @Test
    void testFindPricePriceNotFoundExceptionIT() {

        // Given
        Long productId = 30L;
        Long brandId = 12L;
        LocalDateTime date = LocalDateTime.of(2020, Month.APRIL, 10, 16, 0, 0);
        FindPriceRequest request = new FindPriceRequest(date, brandId, productId);

        // When && Then
        assertThrows(PriceNotFoundException.class, () -> {
            priceService.findPrice(request);
        });
    }
}

package com.example.hexagonal.infrastructure.controller;

import com.example.hexagonal.JavaTestApplication;
import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.model.PriceResponseTestEntity;
import com.example.hexagonal.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JavaTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceControllerUAT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    @Qualifier("jpa")
    private PriceRepository priceRepository;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH.mm.ss");

    @BeforeEach
    public void setup() {
        // Clean the DB first
        priceRepository.deleteAll();

        // Setup test data
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateFrom = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).minusDays(1);
        LocalDateTime dateTo = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).plusDays(1);
        Long feeId = 3L;
        BigDecimal amount = new BigDecimal("10.50");

        Price price = new Price(brandId, dateFrom, dateTo, feeId, productId, 0, amount, Currency.EUR);

        priceRepository.save(price);
    }

    @Test
    void testGetPriceUAT() {
        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.APRIL,10,16,0,0);

        Long expectedProductId = 30L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).minusDays(1);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).plusDays(1);
        Long expectedFeeId = 3L;
        BigDecimal expectedAmount = new BigDecimal("10.50");

        // When
        ResponseEntity<PriceResponseTestEntity> responseEntity = restTemplate.getForEntity(
                "/prices?productId={productId}&brandId={brandId}&date={date}",
                PriceResponseTestEntity.class,
                productId, brandId, date
        );

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        PriceResponseTestEntity response = responseEntity.getBody();
        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo(expectedProductId);
        assertThat(response.getBrandId()).isEqualTo(expectedBrandId);
        assertThat(response.getAmount()).isEqualTo(expectedAmount);
        assertThat(response.getDateFrom()).isEqualTo(expectedDateFrom.format(dateTimeFormatter));
        assertThat(response.getDateTo()).isEqualTo(expectedDateTo.format(dateTimeFormatter));
        assertThat(response.getFeeId()).isEqualTo(expectedFeeId);
    }

    @Test
    void testGetPriceNotFoundUAT() {
        // Given
        Long productId = 99L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2021, Month.APRIL,10,16,0,0);

        // When
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/prices?productId={productId}&brandId={brandId}&date={date}",
                String.class,
                productId, brandId, date
        );

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetPriceWrongParamUAT() {
        // Given
        Long productId = 99L;
        String brandId = "zara";
        LocalDateTime date = LocalDateTime.of(2021, Month.APRIL,10,16,0,0);

        // When
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/prices?productId={productId}&brandId={brandId}&date={date}",
                String.class,
                productId, brandId, date
        );

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

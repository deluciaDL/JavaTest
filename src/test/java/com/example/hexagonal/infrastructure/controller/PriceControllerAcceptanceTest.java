package com.example.hexagonal.infrastructure.controller;

import com.example.hexagonal.JavaTestApplication;
import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PriceControllerAcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PriceRepository priceRepository;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH.mm.ss");

    @BeforeEach
    public void setup() {
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
    public void testGetPriceUAT() {
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
        ResponseEntity<PriceResponse> responseEntity = restTemplate.getForEntity(
                "/prices?productId={productId}&brandId={brandId}&date={date}",
                PriceResponse.class,
                productId, brandId, date
        );

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        PriceResponse response = responseEntity.getBody();
        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo(expectedProductId);
        assertThat(response.getBrandId()).isEqualTo(expectedBrandId);
        assertThat(response.getAmount()).isEqualTo(expectedAmount);
        assertThat(response.getDateFrom()).isEqualTo(expectedDateFrom.format(dateTimeFormatter));
        assertThat(response.getDateTo()).isEqualTo(expectedDateTo.format(dateTimeFormatter));
        assertThat(response.getFeeId()).isEqualTo(expectedFeeId);
    }

    @Test
    public void getPrice_whenPriceNotFound_returnsNotFound() {
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

    public static class PriceResponse {
        private Long productId;
        private Long brandId;
        private BigDecimal amount;
        private String dateFrom;
        private String dateTo;
        private Long feeId;

        // Getters and setters...

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

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(String dateFrom) {
            this.dateFrom = dateFrom;
        }

        public String getDateTo() {
            return dateTo;
        }

        public void setDateTo(String dateTo) {
            this.dateTo = dateTo;
        }

        public Long getFeeId() {
            return feeId;
        }

        public void setFeeId(Long feeId) {
            this.feeId = feeId;
        }
    }
}

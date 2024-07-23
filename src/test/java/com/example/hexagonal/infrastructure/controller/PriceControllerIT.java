package com.example.hexagonal.infrastructure.controller;

import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIT {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private MockMvc mockMvc;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH.mm.ss");

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
    void testPriceControllerGetPriceIT() throws Exception {
        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.of(2020, Month.APRIL,10,16,0,0);

        Long expectedProductId = 30L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).minusDays(1);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.APRIL,10,16,0,0).plusDays(1);
        Long expectedFeeId = 3L;
        BigDecimal expectedAmount = BigDecimal.valueOf(10.50);

        // When && Then
        mockMvc.perform(get("/prices").param("productId", productId.toString()).param("brandId", brandId.toString())
                        .param("date", dateParam.toString())).andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", equalTo(expectedProductId.intValue())))
                .andExpect(jsonPath("$.brandId", equalTo(expectedBrandId.intValue())))
                .andExpect(jsonPath("$.dateFrom", is(expectedDateFrom.format(dateTimeFormatter))))
                .andExpect(jsonPath("$.dateTo", is(expectedDateTo.format(dateTimeFormatter))))
                .andExpect(jsonPath("$.feeId", equalTo(expectedFeeId.intValue())))
                .andExpect(jsonPath("$.amount", equalTo(expectedAmount.doubleValue())));
    }
}

package com.example.hexagonal.infrastructure.controller;

import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIT {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private MockMvc mockMvc;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH.mm.ss");

    @BeforeEach
    void setUp() {
        // Clean the DB first
        priceRepository.deleteAll();

        // Setup test data
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime dateFrom = LocalDateTime.of(2020, Month.JUNE,14,0,0,0);
        LocalDateTime dateTo = LocalDateTime.of(2020, Month.DECEMBER,31,23,59,59);
        Long feeId = 1L;
        BigDecimal amount = BigDecimal.valueOf(35.50);

        Price price1 = new Price(brandId, dateFrom, dateTo, feeId, productId, 0, amount, Currency.EUR);

        productId = 35455L;
        brandId = 1L;
        dateFrom = LocalDateTime.of(2020, Month.JUNE,14,15,0,0);
        dateTo = LocalDateTime.of(2020, Month.JUNE,14,18,30,0);
        feeId = 2L;
        amount = BigDecimal.valueOf(25.45);

        Price price2 = new Price(brandId, dateFrom, dateTo, feeId, productId, 1, amount, Currency.EUR);

        productId = 35455L;
        brandId = 1L;
        dateFrom = LocalDateTime.of(2020, Month.JUNE,15,0,0,0);
        dateTo = LocalDateTime.of(2020, Month.JUNE,15,11,0,0);
        feeId = 3L;
        amount = BigDecimal.valueOf(30.50);

        Price price3 = new Price(brandId, dateFrom, dateTo, feeId, productId, 1, amount, Currency.EUR);

        productId = 35455L;
        brandId = 1L;
        dateFrom = LocalDateTime.of(2020, Month.JUNE,15,16,0,0);
        dateTo = LocalDateTime.of(2020, Month.DECEMBER,31,23,59,59);
        feeId = 4L;
        amount = BigDecimal.valueOf(38.95);

        Price price4 = new Price(brandId, dateFrom, dateTo, feeId, productId, 1, amount, Currency.EUR);

        priceRepository.save(price1);
        priceRepository.save(price2);
        priceRepository.save(price3);
        priceRepository.save(price4);
    }

    // Required test 1
    @Test
    void test1GetPriceIT() throws Exception {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.of(2020, Month.JUNE,14,10,0,0);

        Long expectedProductId = 35455L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.JUNE,14,0,0,0);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.DECEMBER,31,23,59,59);
        Long expectedFeeId = 1L;
        BigDecimal expectedAmount = BigDecimal.valueOf(35.50);

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

    // Required test 2
    @Test
    void test2GetPriceIT() throws Exception {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.of(2020, Month.JUNE,14,16,0,0);

        Long expectedProductId = 35455L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.JUNE,14,15,0,0);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.JUNE,14,18,30,0);
        Long expectedFeeId = 2L;
        BigDecimal expectedAmount = BigDecimal.valueOf(25.45);

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

    // Required test 3
    @Test
    void test3GetPriceIT() throws Exception {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.of(2020, Month.JUNE,14,21,0,0);

        Long expectedProductId = 35455L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.JUNE,14,0,0,0);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.DECEMBER,31,23,59,59);
        Long expectedFeeId = 1L;
        BigDecimal expectedAmount = BigDecimal.valueOf(35.50);

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

    // Required test 4
    @Test
    void test4GetPriceIT() throws Exception {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.of(2020, Month.JUNE,15,10,0,0);

        Long expectedProductId = 35455L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.JUNE,15,0,0,0);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.JUNE,15,11,0,0);
        Long expectedFeeId = 3L;
        BigDecimal expectedAmount = BigDecimal.valueOf(30.50);

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

    // Required test 5
    @Test
    void test5GetPriceIT() throws Exception {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.of(2020, Month.JUNE,16,21,0,0);

        Long expectedProductId = 35455L;
        Long expectedBrandId = 1L;
        LocalDateTime expectedDateFrom = LocalDateTime.of(2020, Month.JUNE,15,16,0,0);
        LocalDateTime expectedDateTo = LocalDateTime.of(2020, Month.DECEMBER,31,23,59,59);
        Long expectedFeeId = 4L;
        BigDecimal expectedAmount = BigDecimal.valueOf(38.95);

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

    @Test
    void testGetPriceNotFoundIT() throws Exception {
        // Given
        Long productId = 99L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.of(2020, Month.APRIL, 10, 16, 0, 0);

        String expectedErrorMessage = "No price found";

        // When && Then
        mockMvc.perform(get("/prices").param("productId", productId.toString()).param("brandId", brandId.toString())
                .param("date", dateParam.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void testGetPriceWrongParamIT() throws Exception {
        // Given
        Long productId = 99L;
        String brandId = "zara";
        LocalDateTime dateParam = LocalDateTime.of(2020, Month.APRIL, 10, 16, 0, 0);

        String expectedErrorMessage = "Invalid parameter type: brandId";

        // When && Then
        mockMvc.perform(get("/prices").param("productId", productId.toString()).param("brandId", brandId.toString())
                        .param("date", dateParam.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()));
    }


}

package com.example.hexagonal.infrastructure.controller;

import com.example.hexagonal.application.dto.FindPriceRequest;
import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.application.exception.InvalidPriceRequestException;
import com.example.hexagonal.application.service.PriceService;
import com.example.hexagonal.infrastructure.controller.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.when;

class PriceControllerTest {

    @InjectMocks
    private PriceController priceController;

    @Mock
    private PriceService priceService;

    private MockMvc mockMvc;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH.mm.ss");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc =
                MockMvcBuilders.standaloneSetup(priceController).setControllerAdvice(new GlobalExceptionHandler())
                        .build();
    }

    @Test
    void testGetPrice() throws Exception {

        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateFrom = LocalDateTime.now().minusDays(1);
        LocalDateTime dateTo = LocalDateTime.now().plusDays(1);
        Long feeId = 3L;
        BigDecimal amount = new BigDecimal("50.25");;

        LocalDateTime dateParam = LocalDateTime.now();

        FindPriceRequest request = new FindPriceRequest(dateParam, brandId, productId);
        PriceResponse priceResponse = new PriceResponse(productId, brandId, dateFrom, dateTo, feeId, amount);

        when(priceService.findPrice(request)).thenReturn(priceResponse);

        // When && Then
        mockMvc.perform(get("/prices").param("productId", productId.toString()).param("brandId", brandId.toString())
                .param("date", dateParam.toString())).andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", equalTo(productId.intValue())))
                .andExpect(jsonPath("$.brandId", equalTo(brandId.intValue())))
                .andExpect(jsonPath("$.dateFrom", is(priceResponse.getDateFrom().format(dateTimeFormatter))))
                .andExpect(jsonPath("$.dateTo", is(priceResponse.getDateTo().format(dateTimeFormatter))))
                .andExpect(jsonPath("$.feeId", equalTo(priceResponse.getFeeId().intValue())))
                .andExpect(jsonPath("$.amount", equalTo(priceResponse.getAmount().doubleValue())));
    }

    @Test
    void testGetPriceException() throws Exception {
        // Given When & Then
        mockMvc.perform(get("/prices")
                        .param("date", "")
                        .param("brandId", "")
                        .param("productId", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetPriceInvalidPriceRequestException() throws Exception {
        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.now();

        when(priceService.findPrice(any(FindPriceRequest.class))).thenThrow(new InvalidPriceRequestException(""));

        // When & Then
        mockMvc.perform(get("/prices")
                        .param("date", dateParam.toString())
                        .param("brandId", brandId.toString())
                        .param("productId", productId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidPriceRequestException));
    }
}

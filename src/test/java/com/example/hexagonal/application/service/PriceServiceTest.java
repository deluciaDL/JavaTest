package com.example.hexagonal.application.service;

import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.application.service.PriceService;
import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PriceServiceTest {

    @InjectMocks
    private PriceService priceService;

    @Mock
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPriceServiceFindPrice() {

        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateFrom = LocalDateTime.now().minusDays(1);
        LocalDateTime dateTo = LocalDateTime.now().plusDays(1);
        Long feeId = 3L;
        BigDecimal amount = BigDecimal.TEN;

        LocalDateTime dateParam = LocalDateTime.now();

        Price price = new Price(1L, brandId, dateFrom, dateTo, feeId, productId, 0, amount, Currency.EUR);
        PriceResponse priceResponse = new PriceResponse(productId, brandId, dateFrom, dateTo, feeId, amount);

        when(priceRepository.find(dateParam, brandId, productId)).thenReturn(Optional.of(price));

        // When
        PriceResponse response = priceService.findPrice(dateParam, brandId, productId);

        // Then
        assertEquals(priceResponse, response);
        verify(priceRepository, times(1)).find(dateParam, brandId, productId);
    }

    @Test
    void testPriceServiceFindPriceNotFound() {

        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.now();

        when(priceRepository.find(dateParam, brandId, productId)).thenReturn(Optional.empty());

        // When
        RuntimeException thrownException =
                assertThrows(RuntimeException.class, () -> priceService.findPrice(dateParam, brandId, productId));

        // Then
        assertEquals("No item found", thrownException.getMessage());
        verify(priceRepository, times(1)).find(dateParam, brandId, productId);
    }
}

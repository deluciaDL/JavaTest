package com.example.hexagonal.application.service;

import com.example.hexagonal.application.dto.FindPriceRequest;
import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.application.exception.InvalidPriceRequestException;
import com.example.hexagonal.application.exception.PriceNotFoundException;
import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PriceServiceTest {

    private final Validator validator = Mockito.mock(Validator.class);
    private final PriceRepository priceRepository = Mockito.mock(PriceRepository.class);;
    private final PriceService priceService = new PriceService(priceRepository, validator);

    // Create a mock ConstraintViolation to simulate validation errors
    private final ConstraintViolation<FindPriceRequest> violation = Mockito.mock(ConstraintViolation.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPrice() {

        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateFrom = LocalDateTime.now().minusDays(1);
        LocalDateTime dateTo = LocalDateTime.now().plusDays(1);
        Long feeId = 3L;
        BigDecimal amount = BigDecimal.TEN;

        LocalDateTime dateParam = LocalDateTime.now();

        FindPriceRequest request = new FindPriceRequest(dateParam, brandId, productId);
        Price price = new Price(1L, brandId, dateFrom, dateTo, feeId, productId, 0, amount, Currency.EUR);
        PriceResponse priceResponse = new PriceResponse(productId, brandId, dateFrom, dateTo, feeId, amount);

        when(priceRepository.find(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenReturn(Optional.of(price));
        when(validator.validate(any(FindPriceRequest.class))).thenReturn(Collections.emptySet());

        // When
        PriceResponse response = priceService.findPrice(request);

        // Then
        assertEquals(priceResponse, response);
        verify(priceRepository, times(1)).find(dateParam, brandId, productId);
    }

    @Test
    void testFindPriceNotFound() {

        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.now();

        FindPriceRequest request = new FindPriceRequest(dateParam, brandId, productId);

        String expectedErrorMessage = "No price found";

        when(priceRepository.find(any(LocalDateTime.class), any(Long.class), any(Long.class))).thenReturn(Optional.empty());
        when(validator.validate(any(FindPriceRequest.class))).thenReturn(Collections.emptySet());

        // When
        PriceNotFoundException thrownException =
                assertThrows(PriceNotFoundException.class, () -> priceService.findPrice(request));

        // Then
        assertEquals(expectedErrorMessage, thrownException.getMessage());
        verify(priceRepository, times(1)).find(dateParam, brandId, productId);
    }

    @Test
    void testFindPriceWrongParamFindPriceRequest() {

        // Given
        Long productId = 30L;
        Long brandId = 1L;
        LocalDateTime dateParam = LocalDateTime.now();

        FindPriceRequest request = new FindPriceRequest(dateParam, brandId, productId);

        when(violation.getMessage()).thenReturn("Validation error");
        when(validator.validate(any(FindPriceRequest.class))).thenReturn(Set.of(violation));

        // When
        assertThrows(InvalidPriceRequestException.class, () -> priceService.findPrice(request));

        // Then
        verify(priceRepository, times(0)).find(dateParam, brandId, productId);
    }
}

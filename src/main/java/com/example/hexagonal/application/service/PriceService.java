package com.example.hexagonal.application.service;

import com.example.hexagonal.application.dto.FindPriceRequest;
import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.application.exception.InvalidPriceRequestException;
import com.example.hexagonal.application.mapper.PriceDtoMapper;
import com.example.hexagonal.application.exception.PriceNotFoundException;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PriceService {

    private static final Logger LOG = LoggerFactory.getLogger(PriceService.class);

    private final Validator validator;

    private final PriceRepository priceRepository;

    public PriceService(@Qualifier("jpa") PriceRepository priceRepository, Validator validator) {
        this.priceRepository = priceRepository;
        this.validator = validator;
    }

    public PriceResponse findPrice(FindPriceRequest request) {

        Set<ConstraintViolation<FindPriceRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            throw new InvalidPriceRequestException(errorMessage);
        }

        LOG.info("Fetching price for productId: {}, brandId: {}, date: {}", request.getProductId(),
                request.getBrandId(), request.getDate());

        Optional<Price> price = priceRepository.find(request.getDate(), request.getBrandId(), request.getProductId());

        LOG.debug("Price found: {}", price.orElse(null));

        return price.map(PriceDtoMapper::toPriceResponse).orElseThrow(() -> new PriceNotFoundException("No price found"));
    }
}

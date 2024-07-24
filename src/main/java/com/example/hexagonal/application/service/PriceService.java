package com.example.hexagonal.application.service;

import com.example.hexagonal.application.dto.PriceResponse;
import com.example.hexagonal.application.mapper.PriceDtoMapper;
import com.example.hexagonal.domain.exception.PriceNotFoundException;
import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public PriceResponse findPrice(LocalDateTime date, Long brandId, Long productId) {
        Optional<Price> price = priceRepository.find(date, brandId, productId);
        return price.map(PriceDtoMapper::toPriceResponse).orElseThrow(() -> new PriceNotFoundException("No price found"));
    }
}

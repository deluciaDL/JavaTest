package com.example.hexagonal.infrastructure.configuration;

import com.example.hexagonal.application.service.PriceService;
import com.example.hexagonal.domain.repository.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PriceService priceService(PriceRepository priceRepository) {
        return new PriceService(priceRepository);
    }
}

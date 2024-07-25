package com.example.hexagonal.infrastructure.configuration;

import com.example.hexagonal.application.service.PriceService;
import com.example.hexagonal.domain.repository.PriceRepository;
import com.example.hexagonal.infrastructure.jdbc.adapter.JdbcPriceRepository;
import com.example.hexagonal.infrastructure.jdbc.mapper.JdbcPriceMapper;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class BeanConfiguration {

    @Bean
    public PriceService priceService(PriceRepository priceRepository, Validator validator) {
        return new PriceService(priceRepository, validator);
    }

    @Bean
    public PriceRepository priceRepository(JdbcTemplate jdbcTemplate, JdbcPriceMapper jdbcPriceMapper){
        return new JdbcPriceRepository(jdbcTemplate, jdbcPriceMapper);
    }
}

package com.example.hexagonal.infrastructure.jpa.adapter;

import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import com.example.hexagonal.infrastructure.jpa.entity.JpaPrice;
import com.example.hexagonal.infrastructure.jpa.mapper.JpaPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class JpaPriceRepositoryImpl implements PriceRepository {

    private static final Logger LOG = LoggerFactory.getLogger(JpaPriceRepositoryImpl.class);

    private final JpaPriceRepository jpaPriceRepository;

    public JpaPriceRepositoryImpl(@Lazy
    JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }

    @Override
    public Optional<Price> find(LocalDateTime date, long brandId, long productId) {

        LOG.debug("Executing query to find price for productId: {}, brandId: {}, date: {}", productId, brandId, date);

        return jpaPriceRepository.find(productId, brandId, date).map(JpaPriceMapper::toDomainPrice);
    }

    @Override
    @Transactional
    public void save(Price price) {

        LOG.debug("Saving price: {}", price);

        JpaPrice jpaPrice = JpaPriceMapper.toJpaPrice(price);
        jpaPriceRepository.save(jpaPrice);
    }

    @Override
    @Transactional
    public void deleteAll() {

        LOG.debug("Removing all prices from the database");

        jpaPriceRepository.deleteAll();
    }
}

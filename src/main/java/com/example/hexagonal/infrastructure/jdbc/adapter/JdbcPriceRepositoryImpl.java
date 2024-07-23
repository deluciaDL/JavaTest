package com.example.hexagonal.infrastructure.jdbc.adapter;

import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import com.example.hexagonal.infrastructure.jdbc.entity.JdbcPrice;
import com.example.hexagonal.infrastructure.jdbc.mapper.JdbcPriceMapper;
import com.example.hexagonal.infrastructure.jdbc.mapper.PriceRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPriceRepositoryImpl implements PriceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcPriceMapper jdbcPriceMapper;

    public JdbcPriceRepositoryImpl(JdbcTemplate jdbcTemplate, JdbcPriceMapper jdbcPriceMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcPriceMapper = jdbcPriceMapper;
    }

    @Override
    public Optional<Price> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Price> find(LocalDateTime date, long brandId, long productId) {
        JdbcPrice entity =
                jdbcTemplate.queryForObject(
                        "SELECT * FROM prices WHERE product_id = ? AND brand_id = ? AND ? BETWEEN start_date AND end_date ORDER BY priority DESC LIMIT 1",
                        new PriceRowMapper(), new Object[] { productId, brandId, date });

        return Optional.ofNullable(entity).map(jdbcPriceMapper::fromEntity);

    }

    @Override
    public Price save(Price price) {
        return null;
    }

    @Override
    public List<Price> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}

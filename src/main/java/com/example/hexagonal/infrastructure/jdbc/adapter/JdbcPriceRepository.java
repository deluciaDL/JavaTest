package com.example.hexagonal.infrastructure.jdbc.adapter;

import com.example.hexagonal.domain.model.Price;
import com.example.hexagonal.domain.repository.PriceRepository;
import com.example.hexagonal.infrastructure.jdbc.entity.JdbcPrice;
import com.example.hexagonal.infrastructure.jdbc.mapper.JdbcPriceMapper;
import com.example.hexagonal.infrastructure.jdbc.mapper.PricePreparedStatementSetter;
import com.example.hexagonal.infrastructure.jdbc.mapper.PriceRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPriceRepository implements PriceRepository {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcPriceMapper jdbcPriceMapper;

    public JdbcPriceRepository(JdbcTemplate jdbcTemplate, JdbcPriceMapper jdbcPriceMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcPriceMapper = jdbcPriceMapper;
    }

    @Override
    public Optional<Price> find(LocalDateTime date, long brandId, long productId) {
        List<JdbcPrice> jdbcPrice =
                jdbcTemplate.query(
                        "SELECT * FROM prices WHERE product_id = ? AND brand_id = ? AND ? BETWEEN start_date AND end_date "
                                + "ORDER BY priority DESC LIMIT 1",
                        new PriceRowMapper(), new Object[] { productId, brandId, date });

        return jdbcPrice.stream().findFirst().map(jdbcPriceMapper::fromEntity);

    }

    @Override
    public void save(Price price) {
        JdbcPrice jdbcPrice = jdbcPriceMapper.toEntity(price);

        jdbcTemplate.update(
                "INSERT INTO prices (id, product_id, brand_id, start_date, end_date, fee_id, price, priority, currency) "
                        + "VALUES (nextval('id_sequence'), ?, ?, ?, ?, ?, ?, ?, ?)",
                new PricePreparedStatementSetter(jdbcPrice));
    }

    @Override
    public List<Price> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM prices";
        jdbcTemplate.update(sql);
    }
}

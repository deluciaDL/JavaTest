package com.example.hexagonal.infrastructure.jdbc.mapper;

import com.example.hexagonal.domain.model.Currency;
import com.example.hexagonal.infrastructure.jdbc.entity.JdbcPrice;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceRowMapper implements RowMapper<JdbcPrice> {
    @Override
    public JdbcPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
        JdbcPrice jdbcPrice = new JdbcPrice();
        jdbcPrice.setId(rs.getLong("id"));
        jdbcPrice.setProductId(rs.getLong("product_id"));
        jdbcPrice.setBrandId(rs.getLong("brand_id"));
        jdbcPrice.setDateTo(rs.getTimestamp("end_date").toLocalDateTime());
        jdbcPrice.setDateFrom(rs.getTimestamp("start_date").toLocalDateTime());
        jdbcPrice.setCurrency(Currency.valueOf(rs.getString("currency")));
        jdbcPrice.setAmount(rs.getBigDecimal("price"));
        jdbcPrice.setPriority(rs.getInt("priority"));
        jdbcPrice.setFeeId(rs.getLong("fee_id"));

        return jdbcPrice;
    }
}

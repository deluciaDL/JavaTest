package com.example.hexagonal.infrastructure.jdbc.mapper;

import com.example.hexagonal.infrastructure.jdbc.entity.JdbcPrice;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PricePreparedStatementSetter implements PreparedStatementSetter {

    private final JdbcPrice jdbcPrice;

    public PricePreparedStatementSetter(JdbcPrice jdbcPrice) {
        this.jdbcPrice = jdbcPrice;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        ps.setLong(1, jdbcPrice.getProductId());
        ps.setLong(2, jdbcPrice.getBrandId());
        ps.setObject(3, jdbcPrice.getDateFrom());
        ps.setObject(4, jdbcPrice.getDateTo());
        ps.setLong(5, jdbcPrice.getFeeId());
        ps.setBigDecimal(6, jdbcPrice.getAmount());
        ps.setInt(7, jdbcPrice.getPriority());
        ps.setString(8, jdbcPrice.getCurrency().name());
    }
}

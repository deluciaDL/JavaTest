package com.example.hexagonal.domain.model;

import java.math.BigDecimal;

public class Fee {

    private Long id;
    private BigDecimal amount;
    private String description;

    public Fee() {
    }

    public Fee(Long id, BigDecimal amount, String description) {
        this.id = id;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

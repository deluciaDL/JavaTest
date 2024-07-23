package com.example.hexagonal.domain.repository;

import com.example.hexagonal.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findById(long id);

    Optional<Price> find(LocalDateTime date, long brandId, long productId);

    void save(Price price);

    List<Price> findAll();

    void deleteById(long id);
}

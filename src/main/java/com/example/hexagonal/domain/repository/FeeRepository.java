package com.example.hexagonal.domain.repository;

import com.example.hexagonal.domain.model.Fee;

import java.util.List;
import java.util.Optional;

public interface FeeRepository {

    Optional<Fee> findById(long id);

    Fee save(Fee fee);

    List<Fee> findAll();

    void deleteById(long id);
}

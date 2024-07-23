package com.example.hexagonal.domain.repository;

import com.example.hexagonal.domain.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {

    Optional<Brand> findById(long id);

    Brand save(Brand brand);

    List<Brand> findAll();

    void deleteById(long id);
}

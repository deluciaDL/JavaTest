package com.example.hexagonal.domain.repository;

import com.example.hexagonal.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(long id);

    Optional<Product> findByName(String name);

    Product save(Product product);

    void deleteById(long id);
}

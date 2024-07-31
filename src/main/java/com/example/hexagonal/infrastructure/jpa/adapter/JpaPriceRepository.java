package com.example.hexagonal.infrastructure.jpa.adapter;

import com.example.hexagonal.infrastructure.jpa.entity.JpaPrice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface JpaPriceRepository extends CrudRepository<JpaPrice, Long> {

    @Query("SELECT p FROM JpaPrice p WHERE p.productId = :productId AND p.brandId = :brandId AND :date BETWEEN p.dateFrom AND p.dateTo ORDER BY p.priority DESC LIMIT 1")
    Optional<JpaPrice> find(@Param("productId")
    Long productId, @Param("brandId")
    Long brandId, @Param("date")
    LocalDateTime date);

    @Transactional
    @Modifying
    @Query("DELETE FROM JpaPrice")
    void deleteAll();
}

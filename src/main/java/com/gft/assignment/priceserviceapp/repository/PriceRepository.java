package com.gft.assignment.priceserviceapp.repository;

import com.gft.assignment.priceserviceapp.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query(value = "SELECT p FROM Price p WHERE p.productId = :productId AND p.brandId = :brandId " +
            "AND p.startDate <= :applicationDate AND p.endDate >= :applicationDate " +
            "ORDER BY p.priority DESC")
    List<Price> findPricesByProductIdAndBrandIdAndStartDate(
            @Param("productId") Long productId,
            @Param("brandId") Integer brandId,
            @Param("applicationDate") LocalDateTime applicationDate);
}
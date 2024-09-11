package com.gft.assignment.priceserviceapp.unit.repository;

import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    public void setUp() {
        priceRepository.deleteAll(); // Clear previous data

        Price price1 = new Price(null, 1, LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59), 1, 35455L, 0, new BigDecimal("35.50"), "EUR");
        Price price2 = new Price(null, 1, LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30), 2, 35455L, 1, new BigDecimal("25.45"), "EUR");
        Price price3 = new Price(null, 1, LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0), 3, 35455L, 1, new BigDecimal("30.50"), "EUR");
        Price price4 = new Price(null, 1, LocalDateTime.of(2020, 6, 15, 16, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59), 4, 35455L, 1, new BigDecimal("38.95"), "EUR");

        // Ensure unique priorities
        priceRepository.save(price1);
        priceRepository.save(price2);
        priceRepository.save(price3);
        priceRepository.save(price4);
    }


    @Test
    public void testFindFirstByProductIdAndBrandIdAndStartDate() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Optional<Price> foundPrice = priceRepository.findTopByProductIdAndBrandIdAndStartDate(
                35455L, 1, applicationDate);

        assertTrue(foundPrice.isPresent());
        assertEquals(new BigDecimal("35.50"), foundPrice.get().getPrice());
    }

    @Test
    public void testFindFirstByProductIdAndBrandIdAndStartDate_ValidPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 15, 30);
        Optional<Price> foundPrice = priceRepository.findTopByProductIdAndBrandIdAndStartDate(
                35455L, 1, applicationDate);

        assertTrue(foundPrice.isPresent());
        assertEquals(new BigDecimal("25.45"), foundPrice.get().getPrice());
    }

    @Test
    public void testFindFirstByProductIdAndBrandIdAndStartDate_AnotherValidPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        Optional<Price> foundPrice = priceRepository.findTopByProductIdAndBrandIdAndStartDate(
                35455L, 1, applicationDate);

        assertTrue(foundPrice.isPresent());
        assertEquals(new BigDecimal("30.50"), foundPrice.get().getPrice());
    }

    @Test
    public void testFindFirstByProductIdAndBrandIdAndStartDate_LatestValidPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 17, 0);
        Optional<Price> foundPrice = priceRepository.findTopByProductIdAndBrandIdAndStartDate(
                35455L, 1, applicationDate);

        assertTrue(foundPrice.isPresent());
        assertEquals(new BigDecimal("38.95"), foundPrice.get().getPrice());
    }

    @Test
    public void testFindFirstByProductIdAndBrandIdAndStartDate_NotFound() {
        Long productId = 1L; // Example product ID
        Integer brandId = 1; // Example brand ID
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 12, 0); // Current date for testing

        Optional<Price> result = priceRepository.findTopByProductIdAndBrandIdAndStartDate(
                productId, brandId, applicationDate);

        assertFalse(result.isPresent(), "Expected no price to be found");
    }

}
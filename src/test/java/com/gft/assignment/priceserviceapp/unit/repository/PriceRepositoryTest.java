package com.gft.assignment.priceserviceapp.unit.repository;

import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    public void testFindPricesByProductIdAndBrandIdAndStartDate() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        List<Price> foundPrices = priceRepository.findPricesByProductIdAndBrandIdAndStartDate(
                35455L, 1, applicationDate);

        assertFalse(foundPrices.isEmpty());
        assertEquals(new BigDecimal("35.50"), foundPrices.get(0).getPrice());
    }

    @Test
    public void testFindPricesByProductIdAndBrandIdAndStartDate_ValidPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 15, 30);
        List<Price> foundPrices = priceRepository.findPricesByProductIdAndBrandIdAndStartDate(
                35455L, 1, applicationDate);

        assertFalse(foundPrices.isEmpty());
        assertEquals(new BigDecimal("25.45"), foundPrices.get(0).getPrice());
    }

    @Test
    public void testFindPricesByProductIdAndBrandIdAndStartDate_AnotherValidPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        List<Price> foundPrices = priceRepository.findPricesByProductIdAndBrandIdAndStartDate(
                35455L, 1, applicationDate);

        assertFalse(foundPrices.isEmpty());
        assertEquals(new BigDecimal("30.50"), foundPrices.get(0).getPrice());
    }

    @Test
    public void testFindPricesByProductIdAndBrandIdAndStartDate_LatestValidPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 17, 0);
        List<Price> foundPrices = priceRepository.findPricesByProductIdAndBrandIdAndStartDate(
                35455L, 1, applicationDate);

        assertFalse(foundPrices.isEmpty());
        assertEquals(new BigDecimal("38.95"), foundPrices.get(0).getPrice());
    }

    @Test
    public void testFindPricesByProductIdAndBrandIdAndStartDate_NotFound() {
        Long productId = 1L;
        Integer brandId = 1;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 12, 0); // Current date for testing

        List<Price> result = priceRepository.findPricesByProductIdAndBrandIdAndStartDate(
                productId, brandId, applicationDate);

        assertTrue(result.isEmpty(), "Expected no price to be found");
    }
}
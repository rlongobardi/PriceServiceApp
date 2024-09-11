package com.gft.assignment.priceserviceapp.unit.service;

import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.repository.PriceRepository;
import com.gft.assignment.priceserviceapp.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testGetApplicablePrice() {
        // Sample data for the test
        Price samplePrice = new Price(1L, 1, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), 1, 35455L, 1, new BigDecimal("35.50"), "EUR");

        when(priceRepository.findTopByProductIdAndBrandIdAndStartDate(
                anyLong(), anyInt(), any(LocalDateTime.class)))
                .thenReturn(Optional.of(samplePrice));

        Optional<Price> price = priceService.getApplicablePrice(35455L, 1, LocalDateTime.now());

        assertEquals(samplePrice, price.get()); // Assert the result is correct
    }

    @Test
    public void testGetApplicablePrice_NotFound() {
        when(priceRepository.findTopByProductIdAndBrandIdAndStartDate(
                anyLong(), anyInt(), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        Optional<Price> price = priceService.getApplicablePrice(35455L, 1, LocalDateTime.now());

        assertEquals(Optional.empty(), price); // Assert the result is empty
    }
}

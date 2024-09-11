package com.gft.assignment.priceserviceapp.unit.controller;

import com.gft.assignment.priceserviceapp.controller.PriceController;
import com.gft.assignment.priceserviceapp.exceptions.PriceNotFoundException;
import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.service.PriceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PriceControllerUTest {

    @InjectMocks
    private PriceController priceController;

    @Mock
    private PriceService priceService;

    public PriceControllerUTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPrice_Success() {
        Price price = new Price();
        price.setPrice(BigDecimal.valueOf(35.50));
        when(priceService.getApplicablePrice(35455L, 1, LocalDateTime.parse("2020-06-14T10:00:00"))).thenReturn(Optional.of(price));

        ResponseEntity<?> response = priceController.getPrice(LocalDateTime.parse("2020-06-14T10:00:00"), 35455L, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(price, response.getBody());
    }

    @Test
    void testGetPrice_NotFound() {
        when(priceService.getApplicablePrice(35455L, 1, LocalDateTime.parse("2020-06-14T10:00:00"))).thenReturn(Optional.empty());

        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () -> {
            priceController.getPrice(LocalDateTime.parse("2020-06-14T10:00:00"), 35455L, 1);
        });

        assertEquals("Price not found for productId: 35455 and brandId: 1", exception.getMessage());
    }
}
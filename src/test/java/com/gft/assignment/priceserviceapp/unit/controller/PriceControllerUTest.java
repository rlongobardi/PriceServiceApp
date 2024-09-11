package com.gft.assignment.priceserviceapp.unit.controller;

import com.gft.assignment.priceserviceapp.controller.PriceController;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(priceService.getPrice(1, 35455L, LocalDateTime.parse("2020-06-14T10:00:00"))).thenReturn(price);

        ResponseEntity<?> response = priceController.getPrice("2020-06-14T10:00:00", 35455L, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(price, response.getBody());
    }

    @Test
    void testGetPrice_NotFound() {
        when(priceService.getPrice(1, 35455L, LocalDateTime.parse("2020-06-14T10:00:00"))).thenReturn(null);

        ResponseEntity<?> response = priceController.getPrice("2020-06-14T10:00:00", 35455L, 1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
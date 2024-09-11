package com.gft.assignment.priceserviceapp.controller;

import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/v1")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/prices")
    public ResponseEntity<Price> getPrice(
            @RequestParam("applicationDate") String applicationDateStr,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Integer brandId) {
        LocalDateTime applicationDate;
        try {
            applicationDate = LocalDateTime.parse(applicationDateStr);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build(); // Return 400 Bad Request
        }

        Price price = priceService.getPrice(brandId, productId, applicationDate);
        if (price == null) {
            System.out.println("No prices found for productId: " + productId + " and brandId: " + brandId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(price);
    }
}
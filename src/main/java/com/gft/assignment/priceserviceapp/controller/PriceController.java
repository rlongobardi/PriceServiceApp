package com.gft.assignment.priceserviceapp.controller;

import com.gft.assignment.priceserviceapp.exceptions.PriceNotFoundException;
import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/price")
    public ResponseEntity<?> getPrice(
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam("productId") Long productId,
            @RequestParam("brandId") Integer brandId) {

        Optional<Price> price = priceService.getApplicablePrice(productId, brandId, applicationDate);

        return price.map(ResponseEntity::ok)
                .orElseThrow(() -> new PriceNotFoundException("Price not found for productId: " + productId + " and brandId: " + brandId));
    }
}

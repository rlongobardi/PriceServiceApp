package com.gft.assignment.priceserviceapp.service;

import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public Price getPrice(Integer brandId, Long productId, LocalDateTime applicationDate) {
        // Fetch all applicable prices based on product ID, brand ID, and application date
        List<Price> prices = priceRepository.findPricesByProductIdAndBrandIdAndStartDate(
                productId, brandId, applicationDate);

        // If no prices are found, return null
        if (prices.isEmpty()) {
            return null;
        }

        // Find the price with the highest priority
        return prices.stream()
                .max((p1, p2) -> p1.getPriority().compareTo(p2.getPriority()))
                .orElse(null); // This line is technically redundant since we check for empty list above
    }
}
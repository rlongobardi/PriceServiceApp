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
        final List<Price> prices = priceRepository.findPricesByProductIdAndBrandIdAndStartDate(
                productId, brandId, applicationDate);

        if (prices.isEmpty()) {
            return null;
        }

        return prices.stream()
                .max((p1, p2) -> p1.getPriority().compareTo(p2.getPriority()))
                .orElse(null);
    }
}
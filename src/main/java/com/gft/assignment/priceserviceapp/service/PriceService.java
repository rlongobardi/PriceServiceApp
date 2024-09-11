package com.gft.assignment.priceserviceapp.service;

import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceRepository priceRepository;

    public Optional<Price> getApplicablePrice(Long productId, Integer brandId, LocalDateTime applicationDate) {
        return priceRepository.findTopByProductIdAndBrandIdAndStartDate(
                productId, brandId, applicationDate);
    }
}
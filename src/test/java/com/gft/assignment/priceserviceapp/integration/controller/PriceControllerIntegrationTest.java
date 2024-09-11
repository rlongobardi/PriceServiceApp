package com.gft.assignment.priceserviceapp.integration.controller;

import com.gft.assignment.priceserviceapp.model.Price;
import com.gft.assignment.priceserviceapp.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PriceControllerIntegrationTest {

    private static final String API_V_1_PRICE = "/api/v1/prices";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        // priceRepository.deleteAll();

        // Populate the database with test data
        priceRepository.save(new Price(1L, 1, LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"), 1,
                35455L, 0, BigDecimal.valueOf(35.50), "EUR"));
        priceRepository.save(new Price(2L, 1, LocalDateTime.parse("2020-06-14T15:00:00"),
                LocalDateTime.parse("2020-06-14T18:30:00"), 2,
                35455L, 1, BigDecimal.valueOf(25.45), "EUR"));
        priceRepository.save(new Price(3L, 1, LocalDateTime.parse("2020-06-15T00:00:00"),
                LocalDateTime.parse("2020-06-15T11:00:00"), 3,
                35455L, 1, BigDecimal.valueOf(30.50), "EUR"));
        priceRepository.save(new Price(4L, 1, LocalDateTime.parse("2020-06-15T16:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"), 4,
                35455L, 1, BigDecimal.valueOf(38.95), "EUR"));
        priceRepository.flush();
    }

    @Test
    void testGetPrice_Success() throws Exception {
        mockMvc.perform(get(API_V_1_PRICE)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void testGetPrice_Success_SecondEntry() throws Exception {
        mockMvc.perform(get(API_V_1_PRICE) // Use the constant here
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    void testGetPrice_Success_ThirdEntry() throws Exception {
        mockMvc.perform(get(API_V_1_PRICE) // Use the constant here
                        .param("applicationDate", "2020-06-15T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    void testGetPrice_Success_FourthEntry() throws Exception {
        mockMvc.perform(get(API_V_1_PRICE) // Use the constant here
                        .param("applicationDate", "2020-06-15T17:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    void testGetPrice_NotFound() throws Exception {
        mockMvc.perform(get(API_V_1_PRICE) // Use the constant here
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "99999") // Non-existing product ID
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetPrice_InvalidProductId() throws Exception {
        mockMvc.perform(get(API_V_1_PRICE) // Use the constant here
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "invalid") // Invalid product ID
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPrice_InvalidBrandId() throws Exception {
        mockMvc.perform(get(API_V_1_PRICE) // Use the constant here
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "invalid") // Invalid brand ID
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPrice_InvalidDateFormat() throws Exception {
        mockMvc.perform(get(API_V_1_PRICE) // Use the constant here
                        .param("applicationDate", "invalid-date") // Invalid date format
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
package com.gft.assignment.priceserviceapp.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
        objectMapper.enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.configOverride(BigDecimal.class)
                .setFormat(JsonFormat.Value.forShape(JsonFormat.Shape.STRING));
        return objectMapper;
    }
}
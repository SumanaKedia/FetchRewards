package com.example.ReceiptProcessor.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Validate {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Static method to validate JSON
    public static boolean isInvalidJson(String json) {
        try {
            objectMapper.readTree(json);
            return false; // JSON is valid
        } catch (JsonProcessingException e) {
            return true; // JSON is invalid
        }
    }
}



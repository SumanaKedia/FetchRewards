package com.example.ReceiptProcessor.controller;

import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.ReceiptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ReceiptControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReceiptService receiptService;

    @InjectMocks
    private ReceiptController receiptController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(receiptController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testProcessReceipt() throws Exception {
        // Given
        String json = "{\"retailer\":\"Target\",\"total\":\"35.35\",\"items\":["
                + "{\"shortDescription\":\"Mountain Dew 12PK\",\"price\":\"6.49\"},"
                + "{\"shortDescription\":\"Emils Cheese Pizza\",\"price\":\"12.25\"},"
                + "{\"shortDescription\":\"Knorr Creamy Chicken\",\"price\":\"1.26\"},"
                + "{\"shortDescription\":\"Doritos Nacho Cheese\",\"price\":\"3.35\"},"
                + "{\"shortDescription\":\"Klarbrunn 12-PK 12 FL OZ\",\"price\":\"12.00\"}],"
                + "\"purchaseDate\":\"2022-01-01\","
                + "\"purchaseTime\":\"13:01\"}";

        // Expected saved receipt
        Receipt savedReceipt = new Receipt();
        savedReceipt.setId("12345");

        // Mocking the saveReceipt method to return the saved receipt
        when(receiptService.saveReceipt(any(String.class))).thenReturn(savedReceipt);

        // When & Then: Mock the POST request and verify the response
        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"12345\"}")) // Verify the response contains the correct id
                .andDo(print());  // Optional: This will print the request/response details in the console for debugging
    }

    @Test
    public void testGetPoints_Success() throws Exception {
        // Given
        String receiptId = "12345";
        Receipt receipt = new Receipt();
        receipt.setPoints(10);

        when(receiptService.getReceiptById(receiptId)).thenReturn(receipt);

        // When & Then
        mockMvc.perform(get("/receipts/{id}/points", receiptId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"points\":10}"));
    }

    @Test
    public void testGetPoints_NotFound() throws Exception {
        // Given
        String receiptId = "99999";

        when(receiptService.getReceiptById(receiptId)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/receipts/{id}/points", receiptId))
                .andExpect(status().isNotFound());
    }



    @Test
    public void testGetPoints_Error() throws Exception {
        // Given an invalid receipt ID
        String receiptId = "invalidId";

        when(receiptService.getReceiptById(receiptId)).thenThrow(new RuntimeException("Service error"));

        // When & Then
        mockMvc.perform(get("/receipts/{id}/points", receiptId))
                .andExpect(status().isInternalServerError()); // Expecting an Internal Server Error status
    }
}

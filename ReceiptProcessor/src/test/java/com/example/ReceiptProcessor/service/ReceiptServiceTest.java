package com.example.ReceiptProcessor.service;

import com.example.ReceiptProcessor.model.Item;
import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.repository.ReceiptRepository;
import com.example.ReceiptProcessor.service.pointsRuleImp.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ReceiptServiceTest {
    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        // Initialize the list of rules
        List<PointsRule> rules = Arrays.asList(
                new RetailerNameRule(),
                new MultipleOfQuarterRule(),
                new ItemCountRule(),
                new ItemDescriptionRule(),
                new OddDayRule(),
                new PurchaseTimeRule(),
                new RoundDollarAmountRule()
        );

        // Inject the list of rules into the ReceiptService
        receiptService = new ReceiptService(receiptRepository, rules);
    }

    @Test
    void testSaveReceipt_ValidJson() throws Exception {
        String json = "{\"retailer\":\"Target\",\"total\":\"35.35\",\"purchaseDate\":\"2022-01-01\",\"purchaseTime\":\"13:01\",\"items\":[{\"shortDescription\":\"Mountain Dew 12PK\",\"price\":\"6.49\"},{\"shortDescription\":\"Emils Cheese Pizza\",\"price\":\"12.25\"}]}";

        // Mock the save method
        Receipt receiptToSave = objectMapper.readValue(json, Receipt.class);
        when(receiptRepository.save(any(Receipt.class))).thenAnswer(invocation -> {
            Receipt receipt = invocation.getArgument(0);
            receipt.setPoints(28); // Simulate points calculation
            return receipt;
        });

        // Call the method under test
        Receipt savedReceipt = receiptService.saveReceipt(json);

        // Verify interactions and assert results
        verify(receiptRepository).save(any(Receipt.class));
        assertNotNull(savedReceipt);
        assertEquals("Target", savedReceipt.getRetailer());
        assertEquals(28, savedReceipt.getPoints()); // Update based on your expected points calculation
    }

    @Test
    void testSaveReceipt_InvalidJson() {
        String invalidJson = "{\"retailer\":\"Target\""; // Invalid JSON format

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            receiptService.saveReceipt(invalidJson);
        });

        assertEquals("Invalid JSON format", exception.getMessage());
    }

    @Test
    void testCalculatePoints() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setTotal("35.35");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setItems(Arrays.asList(
                new Item("Mountain Dew 12PK", "6.49"),
                new Item("Emils Cheese Pizza", "12.25"),
                new Item("Knorr Creamy Chicken", "1.26"),
                new Item("Doritos Nacho Cheese", "3.35"),
                new Item("   Klarbrunn 12-PK 12 FL OZ  ", "12.00")
        ));

        int points = receiptService.calculatePoints(receipt);
        assertEquals(28, points);  // 28 points as expected
    }

    @Test
    void testGetReceiptById() {
        String receiptId = "123";
        Receipt receipt = new Receipt();
        receipt.setId(receiptId);
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.of(receipt));

        Receipt foundReceipt = receiptService.getReceiptById(receiptId);
        assertNotNull(foundReceipt);
        assertEquals(receiptId, foundReceipt.getId());
    }

    @Test
    void testGetReceiptById_NotFound() {
        String receiptId = "non-existing-id";
        when(receiptRepository.findById(receiptId)).thenReturn(Optional.empty());

        Receipt foundReceipt = receiptService.getReceiptById(receiptId);
        assertNull(foundReceipt);
    }


}

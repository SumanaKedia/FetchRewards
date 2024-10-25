package com.example.ReceiptProcessor.service.pointsRuleImp;



import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.pointsRuleImp.RetailerNameRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetailerNameRuleTest {

    private RetailerNameRule retailerNameRule;

    @BeforeEach
    public void setUp() {
        retailerNameRule = new RetailerNameRule();
    }

    @Test
    public void testRetailerNameRuleWithAlphanumericRetailerName() {

        Receipt receipt = new Receipt();
        receipt.setRetailer("Walmart123");


        int points = retailerNameRule.apply(receipt);

        // Assert
        assertEquals(10, points, "Retailer name length should be 10 (Walmart123)");
    }

    @Test
    public void testRetailerNameRuleWithSpecialCharacters() {

        Receipt receipt = new Receipt();
        receipt.setRetailer("Walmart #1 Store!!");


        int points = retailerNameRule.apply(receipt);

        // Assert
        assertEquals(13, points, "Retailer name length should be 10 (ignoring special characters)");
    }

    @Test
    public void testRetailerNameRuleWithEmptyRetailerName() {

        Receipt receipt = new Receipt();
        receipt.setRetailer("");


        int points = retailerNameRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Retailer name length should be 0");
    }

    @Test
    public void testRetailerNameRuleWithNullRetailerName() {

        Receipt receipt = new Receipt();
        receipt.setRetailer(null);

        int points = retailerNameRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Retailer name length should be 0 for null retailer name");
    }
}

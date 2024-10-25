package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.model.Receipt;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class PurchaseTimeRuleTest {
    private final PurchaseTimeRule purchaseTimeRule = new PurchaseTimeRule();

    @Test
    public void testPointsForTimeWithinWindow() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseTime("15:00"); // Exactly at 3 PM

        // Act
        int points = purchaseTimeRule.apply(receipt);

        // Assert
        assertEquals(10, points, "Points should be 10 for a purchase at 3 PM.");
    }

    @Test
    public void testNoPointsForTimeBeforeWindow() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseTime("13:59"); // Just before 2 PM

        // Act
        int points = purchaseTimeRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for a purchase at 1:59 PM.");
    }

    @Test
    public void testNoPointsForTimeAfterWindow() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseTime("16:01"); // Just after 4 PM

        // Act
        int points = purchaseTimeRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for a purchase at 4:01 PM.");
    }

    @Test
    public void testNoPointsForTimeAtStartOfWindow() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseTime("14:00"); // Exactly at 2 PM

        // Act
        int points = purchaseTimeRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for a purchase at 2 PM.");
    }

    @Test
    public void testPointsForTimeAtEndOfWindow() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseTime("15:59"); // Just before 4 PM

        // Act
        int points = purchaseTimeRule.apply(receipt);

        // Assert
        assertEquals(10, points, "Points should be 10 for a purchase at 3:59 PM.");
    }

    @Test
    public void testInvalidTimeFormat() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseTime("invalid-time"); // Invalid time format

        // Act & Assert
        assertThrows(DateTimeParseException.class, () -> {
            purchaseTimeRule.apply(receipt);
        }, "Expected DateTimeParseException for invalid time format.");
    }
}

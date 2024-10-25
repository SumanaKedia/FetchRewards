package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.model.Receipt;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoundDollarAmountRuleTest {
    private final RoundDollarAmountRule roundDollarAmountRule = new RoundDollarAmountRule();

    @Test
    public void testValidRoundDollarAmount() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("100.00"); // Valid round dollar amount

        // Act
        int points = roundDollarAmountRule.apply(receipt);

        // Assert
        assertEquals(50, points, "Points should be 50 for a total of 100.00.");
    }

    @Test
    public void testNonRoundDollarAmount() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("99.99"); // Not a round dollar amount

        // Act
        int points = roundDollarAmountRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for a total of 99.99.");
    }


    @Test
    public void testNegativeRoundDollarAmount() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("-50.00"); // Negative round dollar amount

        // Act
        int points = roundDollarAmountRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for a total of -50.00.");
    }

    @Test
    public void testInvalidFormat() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("abc"); // Invalid format

        // Act
        int points = roundDollarAmountRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for an invalid total format.");
    }
}

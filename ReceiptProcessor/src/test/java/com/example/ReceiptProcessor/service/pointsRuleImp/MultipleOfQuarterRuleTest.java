package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.exceptions.InvalidPriceFormatException;
import com.example.ReceiptProcessor.model.Receipt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultipleOfQuarterRuleTest {

    private final MultipleOfQuarterRule multipleOfQuarterRule = new MultipleOfQuarterRule();

    @Test
    public void testTotalIsMultipleOfQuarter() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("5.00"); // 5.00 is a multiple of 0.25

        // Act
        int points = multipleOfQuarterRule.apply(receipt);

        // Assert
        assertEquals(25, points, "Points should be 25 for total of 5.00.");
    }

    @Test
    public void testTotalIsNotMultipleOfQuarter() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("5.10"); // 5.10 is not a multiple of 0.25

        // Act
        int points = multipleOfQuarterRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for total of 5.10.");
    }

    @Test
    public void testTotalIsZero() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("0.00"); // 0.00 is a multiple of 0.25

        // Act
        int points = multipleOfQuarterRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 25 for total of 0.00.");
    }

    @Test
    public void testNegativeTotalrule() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("-2.00"); // -2.00 is a multiple of 0.25


        assertThrows(InvalidPriceFormatException.class, () -> {
            multipleOfQuarterRule.apply(receipt);
        }, "Total cannot be negative: -2.00");


    }



    @Test
    public void testTotalWithDecimalPlaces() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("3.75"); // 3.75 is a multiple of 0.25

        // Act
        int points = multipleOfQuarterRule.apply(receipt);

        // Assert
        assertEquals(25, points, "Points should be 25 for total of 3.75.");
    }


    @Test
    public void testInvalidTotalFormat() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setTotal("invalid_number"); // Invalid number format

        // Act & Assert
        assertThrows(InvalidPriceFormatException.class, () -> {
            multipleOfQuarterRule.apply(receipt);
        });
    }
}

package com.example.ReceiptProcessor.service.pointsRuleImp;
import com.example.ReceiptProcessor.model.Receipt;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class OddDayRuleTest {
    private final OddDayRule oddDayRule = new OddDayRule();

    @Test
    public void testPointsForOddDay() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseDate("2024-10-25"); // Odd day

        // Act
        int points = oddDayRule.apply(receipt);

        // Assert
        assertEquals(6, points, "Points should be 6 for a purchase on an odd day.");
    }

    @Test
    public void testNoPointsForEvenDay() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseDate("2024-10-26"); // Even day

        // Act
        int points = oddDayRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for a purchase on an even day.");
    }


    @Test
    public void testInvalidDateFormat() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setPurchaseDate("invalid-date"); // Invalid date format

        // Act & Assert
        assertThrows(DateTimeParseException.class, () -> {
            oddDayRule.apply(receipt);
        }, "Expected DateTimeParseException for invalid date format.");
    }
}

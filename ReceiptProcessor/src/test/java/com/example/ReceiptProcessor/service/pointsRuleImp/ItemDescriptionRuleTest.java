package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.exceptions.InvalidPriceFormatException;
import com.example.ReceiptProcessor.model.Item;
import com.example.ReceiptProcessor.model.Receipt;
import org.junit.jupiter.api.*;


import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class ItemDescriptionRuleTest {
    private ItemDescriptionRule itemDescriptionRule;

    @BeforeEach
    public void setUp() {
        itemDescriptionRule = new ItemDescriptionRule();
    }

    @Test
    public void testPointsForItemDescriptionLengthMultipleOfThree() {
        // Arrange
        Receipt receipt = new Receipt();
        List<Item> items = new ArrayList<>();
        items.add(new Item("Test Item 1", "10.00")); // Length 10 (not multiple of 3)
        items.add(new Item("Valid Item", "15.00")); // Length 10 (not multiple of 3)
        items.add(new Item("Item Three", "20.00")); // Length 10 (not multiple of 3)
        items.add(new Item("ThreeChar", "30.00")); // Length 9 (multiple of 3)

        receipt.setItems(items);

        // Act
        int points = itemDescriptionRule.apply(receipt);

        // Assert
        assertEquals(6, points, "Points should be 6 for 'ThreeChar' item (30.00 * 0.2 = 6.0, rounded up).");
    }

    @Test
    public void testInvalidPriceFormat() {
        // Arrange
        Receipt receipt = new Receipt();
        List<Item> items = new ArrayList<>();
        items.add(new Item("Item One", "20.00"));
        items.add(new Item("Item TwoS", "invalid_price")); // Invalid price format

        receipt.setItems(items);

        assertThrows(InvalidPriceFormatException.class, () -> {
            itemDescriptionRule.apply(receipt); // This should throw the exception
        });
    }

    @Test
    public void testNoItems() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setItems(new ArrayList<>()); // Empty list

        // Act
        int points = itemDescriptionRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for no items.");
    }
}

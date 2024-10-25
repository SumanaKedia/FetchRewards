package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.model.Item;
import com.example.ReceiptProcessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemCountRuleTest{

    private ItemCountRule itemCountRule;

    @BeforeEach
    public void setUp() {
        itemCountRule = new ItemCountRule();
    }

    @Test
    public void testItemCountRuleWithNoItems() {
        // Arrange
        Receipt receipt = new Receipt();
        receipt.setItems(new ArrayList<>()); // Empty list

        // Act
        int points = itemCountRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for no items.");
    }

    @Test
    public void testItemCountRuleWithOneItem() {
        // Arrange
        Receipt receipt = new Receipt();
        List<Item> items = new ArrayList<>();
        items.add(new Item("Item 1", "10.00"));
        receipt.setItems(items); // 1 item

        // Act
        int points = itemCountRule.apply(receipt);

        // Assert
        assertEquals(0, points, "Points should be 0 for 1 item (5 points per 2 items).");
    }

    @Test
    public void testItemCountRuleWithTwoItems() {
        // Arrange
        Receipt receipt = new Receipt();
        List<Item> items = new ArrayList<>();
        items.add(new Item("Item 1", "10.00"));
        items.add(new Item("Item 2", "15.00"));
        receipt.setItems(items); // 2 items

        // Act
        int points = itemCountRule.apply(receipt);

        // Assert
        assertEquals(5, points, "Points should be 5 for 2 items (5 points per 2 items).");
    }





    @Test
    public void testItemCountRuleWithFiveItems() {
        // Arrange
        Receipt receipt = new Receipt();
        List<Item> items = new ArrayList<>();
        items.add(new Item("Item 1", "10.00"));
        items.add(new Item("Item 2", "15.00"));
        items.add(new Item("Item 3", "20.00"));
        items.add(new Item("Item 4", "25.00"));
        items.add(new Item("Item 5", "30.00"));
        receipt.setItems(items); // 5 items

        // Act
        int points = itemCountRule.apply(receipt);

        // Assert
        assertEquals(10, points, "Points should be 10 for 5 items (5 points for the first 4 items).");
    }
}

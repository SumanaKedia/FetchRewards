package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.exceptions.InvalidPriceFormatException;
import com.example.ReceiptProcessor.model.Item;
import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.PointsRule;
import org.springframework.stereotype.Component;

@Component
public class ItemDescriptionRule implements PointsRule {
    @Override
    public int apply(Receipt receipt) {
        int points = 0;
        for (Item item : receipt.getItems()) {
            int length = item.getShortDescription().trim().length();

            if (length % 3 == 0) {

                try {
                    // Parse the price and calculate points
                    double price = Double.parseDouble(item.getPrice());

                    // Calculate points as the ceiling of price * 0.2
                    points += (int) Math.ceil(price * 0.2);
                } catch (NumberFormatException e) {
                    throw new InvalidPriceFormatException("Invalid price format for item: " + item.getShortDescription());
                }


            }
        }
        return points;
    }
}
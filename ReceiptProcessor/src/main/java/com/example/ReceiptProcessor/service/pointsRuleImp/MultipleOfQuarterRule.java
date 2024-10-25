package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.exceptions.InvalidPriceFormatException;
import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.PointsRule;
import org.springframework.stereotype.Component;

@Component
public class MultipleOfQuarterRule implements PointsRule {
    @Override
    public int apply(Receipt receipt) {
        double total;
        try {
            total = Double.parseDouble(receipt.getTotal());
        } catch (NumberFormatException e) {
            throw new InvalidPriceFormatException("Invalid total format: " + receipt.getTotal());
        }

        // Check if the total is negative
        if (total < 0) {
            throw new InvalidPriceFormatException("Total cannot be negative: " + receipt.getTotal());
        }

        // Check if the total is zero
        if (total == 0.00) {
            return 0; // Return 0 points for a total of 0.00
        }

        // Check if the total is a multiple of 0.25
        if (total % 0.25 == 0) {
            return 25;
        }

        return 0; // No points awarded if not a multiple of 0.25

    }
}
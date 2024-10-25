package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.PointsRule;
import org.springframework.stereotype.Component;

@Component
public class RetailerNameRule implements PointsRule {
    @Override
    public int apply(Receipt receipt) {

        if (receipt.getRetailer() == null) {
            return 0;
        }

        return receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();
    }
}
package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.PointsRule;
import org.springframework.stereotype.Component;

@Component
public class RoundDollarAmountRule implements PointsRule {
    @Override
    public int apply(Receipt receipt) {
        if (receipt.getTotal().matches("\\d+\\.00")) {
            return 50;
        }
        return 0;
    }
}
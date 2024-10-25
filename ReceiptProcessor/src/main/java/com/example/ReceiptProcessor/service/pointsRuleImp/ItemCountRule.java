package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.PointsRule;
import org.springframework.stereotype.Component;

@Component
public class ItemCountRule implements PointsRule {
    @Override
    public int apply(Receipt receipt) {
        return (receipt.getItems().size() / 2) * 5;
    }
}
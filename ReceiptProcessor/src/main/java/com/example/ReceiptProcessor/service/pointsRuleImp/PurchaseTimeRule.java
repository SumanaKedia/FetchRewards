package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.PointsRule;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class PurchaseTimeRule implements PointsRule {
    @Override
    public int apply(Receipt receipt) {
        LocalTime time = LocalTime.parse(receipt.getPurchaseTime());
        if (time.isAfter(LocalTime.of(14, 0)) && time.isBefore(LocalTime.of(16, 0))) {
            return 10;
        }
        return 0;
    }
}
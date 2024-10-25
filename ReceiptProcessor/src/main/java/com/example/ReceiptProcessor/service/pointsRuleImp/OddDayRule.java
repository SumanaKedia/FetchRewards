package com.example.ReceiptProcessor.service.pointsRuleImp;

import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.PointsRule;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OddDayRule implements PointsRule {
    @Override
    public int apply(Receipt receipt) {
        LocalDate date = LocalDate.parse(receipt.getPurchaseDate());
        if (date.getDayOfMonth() % 2 != 0) {
            return 6;
        }
        return 0;
    }
}
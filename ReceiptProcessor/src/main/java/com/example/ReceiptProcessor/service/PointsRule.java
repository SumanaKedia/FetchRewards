package com.example.ReceiptProcessor.service;

import com.example.ReceiptProcessor.model.Receipt;

public interface PointsRule {
    int apply(Receipt receipt);
}
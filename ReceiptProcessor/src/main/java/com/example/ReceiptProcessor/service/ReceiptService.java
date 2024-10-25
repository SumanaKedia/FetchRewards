package com.example.ReceiptProcessor.service;

import com.example.ReceiptProcessor.model.Item;
import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.repository.ReceiptRepository;
import com.example.ReceiptProcessor.util.Validate;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReceiptService {


    private final ReceiptRepository receiptRepository;
    private final List<PointsRule> pointsRules;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository, List<PointsRule> pointsRules) {
        this.receiptRepository = receiptRepository;
        this.pointsRules = pointsRules;
    }

    public int calculatePoints(Receipt receipt) {
        int totalPoints = 0;
        for (PointsRule rule : pointsRules) {
            totalPoints += rule.apply(receipt);
        }
        return totalPoints;
    }

    @Transactional
    public Receipt saveReceipt(String json) throws Exception {
        // Calculate points for the receipt

        if (Validate.isInvalidJson(json)) {
            throw new IllegalArgumentException("Invalid JSON format");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Receipt receipt = objectMapper.readValue(json, Receipt.class);

        // Set the receipt reference for each item
        for (Item item : receipt.getItems()) {
            item.setReceipt(receipt);
        }
        int points = calculatePoints(receipt);
        receipt.setPoints(points);

        // Save the receipt, which will also save associated items due to CascadeType
        return receiptRepository.save(receipt);

    }

    public Receipt getReceiptById(String id) {
        return receiptRepository.findById(id).orElse(null);
    }



}



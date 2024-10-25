package com.example.ReceiptProcessor.controller;



import com.example.ReceiptProcessor.model.Receipt;
import com.example.ReceiptProcessor.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/process")
    public ResponseEntity<?> processReceipt(@RequestBody  String json) throws Exception {
        // Save the receipt and its items

        Receipt savedReceipt = receiptService.saveReceipt(json);

        // Throw exception if the savedReceipt is null
        if (savedReceipt == null) {
            throw new IllegalStateException("Receipt could not be saved");
        }

        // Return response with the saved receipt's ID
        return ResponseEntity.ok("{\"id\":\"" + savedReceipt.getId() + "\"}");


    }

    @GetMapping("/{id}/points")
    public ResponseEntity<?> getPoints(@PathVariable String id) {
        Receipt receipt = receiptService.getReceiptById(id);

        if (receipt == null || receipt.getId() == null) {
            throw new NoSuchElementException("Receipt with ID " + id + " not found");
        }else{
            return ResponseEntity.ok("{\"points\":" + receipt.getPoints() + "}");
        }

    }

}

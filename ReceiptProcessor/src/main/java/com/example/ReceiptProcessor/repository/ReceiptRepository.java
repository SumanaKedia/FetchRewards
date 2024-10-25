package com.example.ReceiptProcessor.repository;

import com.example.ReceiptProcessor.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, String> {
}

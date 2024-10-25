package com.example.ReceiptProcessor.repository;

import com.example.ReceiptProcessor.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}

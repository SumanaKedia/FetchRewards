package com.example.ReceiptProcessor.model;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
public class Receipt {
    @Id
    private String id;

    private String retailer;
    private String purchaseDate;
    private String purchaseTime;

    private String total;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    private int points;

    // Constructor to generate a UUID for the receipt
    public Receipt() {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
    }




}









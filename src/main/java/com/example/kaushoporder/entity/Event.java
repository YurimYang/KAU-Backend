package com.example.kaushoporder.entity;

import com.example.kaushoporder.model.TransactionPhase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.Transaction;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private Long orderId;
    @ColumnDefault("0")
    private TransactionPhase phase;
}

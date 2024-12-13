package com.cuong02n.aimsbackend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class TransactionInfo extends BaseEntity {
    String transactionId;
    Timestamp transactionTime;
    String transactionMessage;

    @Id
    @Column(name = "invoice_id")
    Long invoiceId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}

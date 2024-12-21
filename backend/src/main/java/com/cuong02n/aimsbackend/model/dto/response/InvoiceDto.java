package com.cuong02n.aimsbackend.model.dto.response;

import lombok.Data;
import lombok.Setter;

@Data
public class InvoiceDto {
    private Long orderId;
    private boolean isPaid;
    private long shippingFee;
    private long totalAmountWithoutVAT; // tong tien hang ko chua shipping fee;
    private long totalAmountIncludeVAT;
    private long totalAmountIncludeShippingFee;
}

package com.cuong02n.aimsbackend.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlaceRushOrderRequest extends PlaceOrderRequest{
    @NotNull(message = "Time in minute cannot be null")
    @Min(value = 1, message = "Time in minute must be greater than 0")
    int timeInMinute;
}

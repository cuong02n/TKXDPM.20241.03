package com.cuong02n.aimsbackend.constant;

public enum PaymentStatus {
    SUCCESS(1),
    FAILURE(0),
    INVALID(-1);

    private final int value;

    PaymentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PaymentStatus fromValue(int value) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        return INVALID;
    }
}

package com.poly.datn.Entity;

public enum IsDelete {
        ACTIVE(0),
        DELETED(1);

    private final int value;


    IsDelete(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }
}

package com.dima.enums;

public enum MedicineType {

    LIQUID("Liquid"),
    TABLETS("Tablets"),
    CAPSULES("Capsules"),
    INJECTIONS("Injections"),
    POWDER("Powder");

    private String name;

    MedicineType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

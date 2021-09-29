package com.epam.rd.java.basic.model;

public enum StatusOrder {
    OPEN, REGISTERED, PAID, CANCELED;

    public static StatusOrder getStatusOrder(String name) {
        if (name == null){
            return null;
        }
        switch (name) {
            case ("OPEN"): {
                return OPEN;
            }
            case ("REGISTERED"): {
                return REGISTERED;
            }
            case ("PAID"): {
                return PAID;
            }
            case ("CANCELED"): {
                return CANCELED;
            }
            default:
                return null;
        }
    }
}

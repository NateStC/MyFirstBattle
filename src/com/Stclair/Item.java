package com.Stclair;

import java.util.ArrayList;

public class Item {

    private String name;
    private double weight;
    private int value = 0;

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public Item(String name, double weight, int value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public Item(String name) {
        this.name = name;
        this.weight = 1;
    }

    public Item() {

    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }
}


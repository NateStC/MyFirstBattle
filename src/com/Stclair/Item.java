package com.Stclair;

import java.util.ArrayList;

public class Item {

    private String name;
    private double weight;
    private int value = 0;
    private boolean equipable;
    private boolean saleable = true;
    private boolean stackable = false;
    private int quantity;

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

    public static String getLvledClothName(int lvl) {
        switch (lvl / 3) {
            case 0:
                return "Frayed Cotton";
            case 1:
                return "Woven Wool";
            case 2:
                return "Embroidered";
            case 4:
                return "Silk";
            case 5:
                return "Empowered";
            case 6:
                return "Jedi";
            default:
                return "";
        }
    }

    public static String getLvledLeatherName(int lvl) {
        switch (lvl / 3) {
            case 0:
                return "Scrap";
            case 1:
                return "Hardened";
            case 2:
                return "Studded";
            case 3:
                return "Peiste";
            case 4:
                return "Naga";
            case 5:
                return "Wyvern";
            case 6:
                return "Drake";
            default:
                return "";
        }
    }

    public static String getLvledMetalName(int lvl) {
        if (lvl <= 3) {
            return "Rusty";
        }
        if (lvl <= 6) {
            return "Copper";
        }
        if (lvl <= 9) {
            return "Iron";
        }
        if (lvl <= 12) {
            return "Steel";
        }
        if (lvl <= 15) {
            return "Mithril";
        }
        if (lvl <= 18) {
            return "Adamantium";
        }
        return "";
    }

    public Item() {

    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isEquipable() {
        return equipable;
    }

    public void setEquipable(boolean equipable) {
        this.equipable = equipable;
    }

    public boolean isSaleable() {
        if (value<=0){
            return false;
        }
        return saleable;
    }

    public void setSaleable(boolean saleable) {
        this.saleable = saleable;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public boolean addCount(int count){
        if (stackable){
            this.quantity += count;
            return true;
        }
        return false;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


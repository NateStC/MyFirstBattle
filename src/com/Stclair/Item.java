package com.Stclair;

import java.util.ArrayList;

public class Item {

    private String name;
    private double weight;
    private int damgeBonus;

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public Item (String name){
        this.name = name;
        this.weight = 1;
    }

    public Item (){

    }


}


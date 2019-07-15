package com.Stclair;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> inventory = new ArrayList<Item>();

    public void addItem(Item item){
        inventory.add(item);
    }

    public void destroyItem(Item item){
        if (findItem(item) !=null){
            inventory.remove(item);
            System.out.println("You destroyed " + item);
        } else {
            System.out.println("Unable to find " + item +" in inventory");
        }
    }

    public void printInventory(){
        System.out.println("You have " + inventory.size() + "items in your inventory.");
        for (int i=0; i<inventory.size(); i++){
            System.out.println(inventory.get(i));
        }
    }

    public Item findItem(Item searchItem){
//        boolean exists = inventory.contains(searchItem);
        int position = inventory.indexOf(searchItem);
        if (position>=0){
            return inventory.get(position);
        }
        return null;
    }
}

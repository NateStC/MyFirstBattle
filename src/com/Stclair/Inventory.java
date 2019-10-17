package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class Inventory extends ArrayList<Item> { //todo find a way to make observable?

    private int maxStorage = 30;

    @Override
    public boolean add(Item item) {
        if (item == null) {
            return false;
        }
        if (this.has(item) && item.isStackable()) {
            this.findItem(item).addCount(item.getQuantity());
            return true;
        }
        if (!isFull()) {
            super.add(item);
        }
        return false;
    }

    public void addAll(List<Item> items) {
        for (Item i : items) {
            this.add(i);
        }
    }

    public boolean destroyItem(Item item) {
        boolean removed = this.remove(item);
        if (removed) {
            System.out.println("Removed " + item.getName());
            return true;
        }
        System.out.println("Item not found in inventory");
        return false;
    }

    public void printInventory() {
        System.out.println("You have " + this.size() + "items in your inventory.");
        for (int i = 0; i < this.size(); i++) {
            System.out.println(this.get(i));
        }
        for (Item i : this) {
            System.out.print(i.getName());
            if (i.isStackable()) System.out.print(" x " + i.getQuantity());
            if (i.isSaleable()) System.out.print(" worth " + i.getValue());
        }
    }

    public Item findItem(Item searchItem) {
        for (Item i : this){
            if (searchItem.getName().equals(i.getName()) && searchItem.getDescription().equals(i.getDescription()) &&
                searchItem.getValue() == i.getValue()){
                return i;
            }
        }
        return null;
    }

    public boolean isFull() {
        return !(this.size() <= maxStorage);
    }

    public int getSpace() {
        return this.maxStorage - this.size();
    }

    public boolean has(Item item) {
        for (Item i : this) {
            if (i.getName().equals(item.getName()) && i.getValue() == item.getValue() &&
                    item.getDescription().equals(i.getDescription())) {
                return true;
            }
        }
        return false;
    }
}

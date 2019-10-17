package com.Stclair;

import java.util.ArrayList;

public class Inventory extends ArrayList<Item> {

    private int maxStorage = 30;

    @Override
    public boolean add(Item item) {
        if (item == null){
            return true;
        }
        if (this.contains(item) && item.isStackable()) {
            int itemIndex = this.indexOf(item);
            this.get(itemIndex).addCount(item.getQuantity());
            return true;
        }
        if (!isFull()) {
            super.add(item);
        }
        return false;
    }

    public boolean destroyItem(Item item) {
        boolean removed = this.remove(item);
        if (removed){
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
        for (Item i : this){
            System.out.print(i.getName());
            if (i.isStackable()) System.out.print(" x " + i.getQuantity());
            if (i.isSaleable()) System.out.print(" worth " + i.getValue());
        }
    }

    public Item findItem(Item searchItem) {
//        boolean exists = inventory.contains(searchItem);
        int position = this.indexOf(searchItem);
        if (position >= 0) {
            return this.get(position);
        }
        return null;
    }

    public boolean isFull() {
        return !(this.size() <= maxStorage);
    }

    public int getSpace(){
        return this.maxStorage - this.size();
    }


}

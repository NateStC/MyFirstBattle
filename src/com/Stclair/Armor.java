package com.Stclair;

public class Armor extends Item {

    private int armorRating = 1;
    private int magicDefRating = 0;
    private int strBonus = 0;
    private int conBonus = 0;
    private int dexBonus = 0;
    private int intBonus = 0;
    private int wisBonus = 0;
    private int chaBonus = 0;
    private boolean equipable = true;

    //todo figure out a slot system for multiple armor items
    //private BodyPart slot

    //todo figure out a way to add art/graphic

    public Armor(){
        setName("Rags");
    }

    //full constructor
    public Armor(String name, double weight, int armorRating, int magicDefRating, int strBonus, int conBonus, int dexBonus, int intBonus, int wisBonus, int chaBonus) {
        super(name, weight);
        this.armorRating = armorRating;
        this.magicDefRating = magicDefRating;
        this.strBonus = strBonus;
        this.conBonus = conBonus;
        this.dexBonus = dexBonus;
        this.intBonus = intBonus;
        this.wisBonus = wisBonus;
        this.chaBonus = chaBonus;
    }

    //default constructor w/ no stat bonuses
    public Armor(String name, double weight, int armorRating) {
        super(name, weight);
        this.armorRating = armorRating;
    }

    //magic armor constructor
    public Armor(String name, double weight, int armorRating, int magicDefRating) {
        super(name, weight);
        this.armorRating = armorRating;
        this.magicDefRating = magicDefRating;
    }

    public int getArmorRating() {
        return armorRating;
    }

    public int getMagicDefRating() {
        return magicDefRating;
    }

    public int getStrBonus() {
        return strBonus;
    }

    public int getConBonus() {
        return conBonus;
    }

    public int getDexBonus() {
        return dexBonus;
    }

    public int getIntBonus() {
        return intBonus;
    }

    public int getWisBonus() {
        return wisBonus;
    }

    public int getChaBonus() {
        return chaBonus;
    }
}

package com.Stclair;

public class Armor extends Item implements Equipable { // implements Equipable interface?

    private int armorRating = 1;
    private int magicDefRating = 0;
    private int strBonus = 0;
    private int conBonus = 0;
    private int dexBonus = 0;
    private int intBonus = 0;
    private int wisBonus = 0;
    private int chaBonus = 0;

    //todo figure out a slot system for multiple armor items
    //private BodyPart slot

    //todo figure out a way to add art/graphic

    public Armor(){
        setName("Rags");
        setDropPct(0);
        setArmorRating(0);
        setValue(0);
    }


    public Armor(String name, double weight, int armorRating, int magicDefRating, int strBonus, int conBonus,
                 int dexBonus, int intBonus, int wisBonus, int chaBonus) {
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

    public Armor(String name, int value, double weight, int armorRating, int magicDefRating, int strBonus, int conBonus,
                 int dexBonus, int intBonus, int wisBonus, int chaBonus) {
        super(name, value, weight);
        this.armorRating = armorRating;
        this.magicDefRating = magicDefRating;
        this.strBonus = strBonus;
        this.conBonus = conBonus;
        this.dexBonus = dexBonus;
        this.intBonus = intBonus;
        this.wisBonus = wisBonus;
        this.chaBonus = chaBonus;
    }

    public Armor(String name, int value, double weight, double dropPct, String description, int armorRating, int magicDefRating, int strBonus, int conBonus, int dexBonus, int intBonus, int wisBonus, int chaBonus) {
        super(name, value, weight, dropPct, description);
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

    @Override
    public boolean equip(myCharacter equipper) {
        if(equipper.getArmor().unequip(equipper)){
            equipper.setArmor(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(myCharacter equipper) {
        if (equipper.getInventory().add(equipper.getArmor())){
            equipper.setWeapon(null);
            return true;
        }
        return false;
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

    public void setArmorRating(int armorRating) {
        this.armorRating = armorRating;
    }

    public void setMagicDefRating(int magicDefRating) {
        this.magicDefRating = magicDefRating;
    }

    public void setStrBonus(int strBonus) {
        this.strBonus = strBonus;
    }

    public void setConBonus(int conBonus) {
        this.conBonus = conBonus;
    }

    public void setDexBonus(int dexBonus) {
        this.dexBonus = dexBonus;
    }

    public void setIntBonus(int intBonus) {
        this.intBonus = intBonus;
    }

    public void setWisBonus(int wisBonus) {
        this.wisBonus = wisBonus;
    }

    public void setChaBonus(int chaBonus) {
        this.chaBonus = chaBonus;
    }
}

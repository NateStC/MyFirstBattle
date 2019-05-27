package com.Stclair;

public class Weapon extends Item{
    private int accuracy;
    private int speed;
    private int damage;
    private int strengthBonus;
    private int dexterityBonus;
    private int constitutionBonus;
    private int intelligenceBonus;
    private boolean equiped;


    public Weapon(String name, double weight, int accuracy, int speed, int damage, int strengthBonus, int dexterityBonus, int constitutionBonus, int intelligenceBonus) {
        super(name, weight);
        this.accuracy = accuracy;
        this.speed = speed;
        this.damage = damage;
        this.strengthBonus = strengthBonus;
        this.dexterityBonus = dexterityBonus;
        this.constitutionBonus = constitutionBonus;
        this.intelligenceBonus = intelligenceBonus;
    }

    public Weapon(String name, double weight, int accuracy, int speed, int damage) {
        super(name, weight);
        this.accuracy = accuracy;
        this.speed = speed;
        this.damage = damage;
    }


    /// **** GETTERS AND SETTERS BELOW ****


    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getStrengthBonus() {
        return strengthBonus;
    }

    public void setStrengthBonus(int strengthBonus) {
        this.strengthBonus = strengthBonus;
    }

    public int getDexterityBonus() {
        return dexterityBonus;
    }

    public void setDexterityBonus(int dexterityBonus) {
        this.dexterityBonus = dexterityBonus;
    }

    public int getConstitutionBonus() {
        return constitutionBonus;
    }

    public void setConstitutionBonus(int constitutionBonus) {
        this.constitutionBonus = constitutionBonus;
    }

    public int getIntelligenceBonus() {
        return intelligenceBonus;
    }

    public void setIntelligenceBonus(int intelligenceBonus) {
        this.intelligenceBonus = intelligenceBonus;
    }

    public boolean isEquiped() {
        return equiped;
    }

    public void setEquiped(boolean equiped) {
        this.equiped = equiped;
    }
}

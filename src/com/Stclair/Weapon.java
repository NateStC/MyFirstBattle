package com.Stclair;

import java.util.ArrayList;

public class Weapon extends Item {
    private int accuracy;
    private int speed;
    private int physDamage;
    private int spellDmg;
    private int strBonus;
    private int dexBonus;
    private int conBonus;
    private int intBonus;
    private int wisBonus;
    private int charBonus;
    private ArrayList<String> attacks = new ArrayList<>();
//    private ArrayList<Attack> attacks = new ArrayList<>();

    //todo find if there's a better arrayList than type string for listing attacks
    public Weapon(String name, double weight, int accuracy, int speed, int physDmg, int spellDmg, int strBonus, int dexBonus,
                  int conBonus, int intBonus, int wisBonus, int charBonus, ArrayList<String> attacks) {
        super(name, weight);
        this.accuracy = accuracy;
        this.speed = speed;
        this.physDamage = physDmg;
        this.spellDmg = spellDmg;
        this.strBonus = strBonus;
        this.dexBonus = dexBonus;
        this.conBonus = conBonus;
        this.intBonus = intBonus;
        this.wisBonus = wisBonus;
        this.charBonus = charBonus;
        this.attacks = attacks;
    }

    //weapon w/no bonuses
    public Weapon(String name, double weight, int accuracy, int speed, int physDamage) {
        super(name, weight);
        this.accuracy = accuracy;
        this.speed = speed;
        this.physDamage = physDamage;
    }

    //default weapon
    public static Weapon dagger() {
        ArrayList<String> attacks = new ArrayList<>();
        attacks.add("stab");
        attacks.add("bash");
        attacks.add("staticShock");

        return new Weapon("Dagger", 1.5, 5, 6, 2, 0,
                0, 0, 0, 0, 0, 0, attacks);
    }

    //basic weapons
    //balanced for spellSword
    public static Weapon swordShort() {
        ArrayList<String> attacks = new ArrayList<>();
        attacks.add("swordSlice");
        attacks.add("stab");
        attacks.add("fireball");
        attacks.add("healingHands");
        return new Weapon("Sword Short", 4.0, 5, 6, 2, 2,
                1, 1, 0, 0, 1, 1, attacks);
    }

    public static Weapon longSword() {
        ArrayList<String> attacks = new ArrayList<>();
        attacks.add("swordSlice");
        attacks.add("stab");
        attacks.add("bash");
        return new Weapon("Long Sword", 5.0, 5, 4, 3, 0,
                2, 1, 0, 0, 0, 0, attacks);
    }

    //fast, high dps, high crit
    public static Weapon daggers() {
        ArrayList<String> attacks = new ArrayList<>();
        attacks.add("daggerSlice");
        attacks.add("stab");
        attacks.add("drainLife");
        return new Weapon("Daggers", 3.5, 5, 8, 3, 1,
                0, 3, 0, 0, 0, 1, attacks);
    }

    //spellcaster
    public static Weapon staff() {
        ArrayList<String> attacks = new ArrayList<>();
        attacks.add("fireball");
        attacks.add("staticShock");
        attacks.add("healingHands");
        attacks.add("bash");
        return new Weapon("Staff", 5.0, 5, 3, 0, 5,
                -1, 0, -1, 3, 2, 2, attacks);
    }

    //tank
    public static Weapon mace() {
        ArrayList<String> attacks = new ArrayList<>();
        attacks.add("bash");
        attacks.add("smash");
        attacks.add("healingHands");
        return new Weapon("Mace", 10.0, 4, 2, 5, 0,
                3, 1, 1, -1, -1, -1, attacks);
    }

    public static Weapon bow() {
        ArrayList<String> attacks = new ArrayList<>();
        attacks.add("arrowStrike");
        attacks.add("bash");
        attacks.add("headShot");
        attacks.add("fireArrow");
        return new Weapon("Bow", 6.0, 6, 4, 4, 0,
                0, 5, 0, 1, 1, 1, attacks);
    }


    /// **** GETTERS AND SETTERS BELOW ****


    public int getAccuracy() {
        return accuracy;
    }

    public int getSpeed() {
        return speed;
    }

    public int getPhysDamage() {
        return physDamage;
    }

    public int getSpellDmg() {
        return spellDmg;
    }

    public int getStrBonus() {
        return strBonus;
    }

    public int getDexBonus() {
        return dexBonus;
    }

    public int getConBonus() {
        return conBonus;
    }

    public int getIntBonus() {
        return intBonus;
    }

    public int getWisBonus() {
        return wisBonus;
    }

    public int getCharBonus() {
        return charBonus;
    }

    public ArrayList<String> getAttacks() {
        return attacks;
    }
}

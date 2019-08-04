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
    private ArrayList<Attack> attackList = new ArrayList<>();

    public Weapon(String name, double weight, int accuracy, int speed, int physDmg, int spellDmg, int strBonus, int dexBonus,
                  int conBonus, int intBonus, int wisBonus, int charBonus, ArrayList<Attack> attacks) {
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
        this.attackList = attacks;
    }

    //weapon w/no bonuses
    public Weapon(String name, double weight, int accuracy, int speed, int physDamage) {
        super(name, weight);
        this.accuracy = accuracy;
        this.speed = speed;
        this.physDamage = physDamage;
        this.spellDmg = 0;
        this.strBonus = 0;
        this.dexBonus = 0;
        this.conBonus = 0;
        this.intBonus = 0;
        this.wisBonus = 0;
        this.charBonus = 0;

        ArrayList<Attack> attacks = new ArrayList<>();
        ;
        attacks.add(Attack.stab());
    }

    public void addAttack(Attack attack) {
        this.attackList.add(attack);
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

    public ArrayList<Attack> getAttackList() {
        return this.attackList;
    }
}

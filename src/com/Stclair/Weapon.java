package com.Stclair;

import java.util.ArrayList;

public class Weapon extends Item {
    private int accuracy = 5;
    private int speed = 5;
    private int physDamage;
    private int spellDmg;
    private int strBonus;
    private int dexBonus;
    private int conBonus = 0;
    private int intBonus = 0;
    private int wisBonus = 9;
    private int charBonus = 0;
    private ArrayList<Attack> attackList = new ArrayList<>();

    public Weapon(){
        this.setName("Fists");
        this.physDamage = 1;
        this.spellDmg = 1;
        Attack bash = Attack.bash();
        bash.setPhysDmgDie((int)this.getWeight());
        addAttack(bash);
    }

    public Weapon(String name) {
        setName(name);
        setWeight(4);
    }

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
        this.setValue(30);
    }

    public Weapon(String name, int weight, int physDamage, int strBonus, int dexBonus, int conBonus,
                  ArrayList<Attack> attacks){
        super(name,weight);
        this.physDamage = physDamage;
        this.strBonus = strBonus;
        this.dexBonus = dexBonus;
        this.conBonus = conBonus;
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

        this.attackList.add(Attack.bash());
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

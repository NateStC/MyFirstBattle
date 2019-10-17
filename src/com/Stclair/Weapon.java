package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Item implements Equipable {
    private int accuracy = 1;
    private int speed = 5;
    private int physDamage;
    private int spellDmg;
    private int strBonus;
    private int dexBonus;
    private int conBonus = 0;
    private int intBonus = 0;
    private int wisBonus = 9;
    private int charBonus = 0;
    private boolean equipable = true;
    private List<Attack> attackList = new ArrayList<>();

    public Weapon() { //todo find a way to make fists not a weapon/item
    }

    public Weapon(String name) {
        setName(name);
        setWeight(4);
    }

    public Weapon(String name, int value, double weight){
        super(name,value,weight);
    }

    public Weapon(String name, int value, double weight, int accuracy, int speed, int physDmg, int spellDmg, int strBonus, int dexBonus,
                  int conBonus, int intBonus, int wisBonus, int charBonus, ArrayList<Attack> attacks) {
        super(name, value, weight);
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

    // ranged Weapon constructor
    public Weapon(String name, double weight, int accuracy, int speed, int physDamage, int strBonus, int dexBonus,
                  ArrayList<Attack> attacks, int value) {
        super(name, value, weight);
        this.accuracy = accuracy;
        this.speed = speed;
        this.physDamage = physDamage;
        this.spellDmg = 0;
        this.strBonus = strBonus;
        this.dexBonus = dexBonus;
        this.attackList = attacks;

    }

    public Weapon(String name, int weight, int physDamage, int strBonus, int dexBonus, int conBonus,
                  ArrayList<Attack> attacks) {
        super(name, weight);
        this.physDamage = physDamage;
        this.strBonus = strBonus;
        this.dexBonus = dexBonus;
        this.conBonus = conBonus;
        this.setValue(20);
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
        this.setValue(5);

        this.attackList.add(Attacks.bash());
    }

    @Override
    public boolean equip(myCharacter equipper) {
        if (equipper.getWeapon().unequip(equipper)){
            equipper.setWeapon(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(myCharacter equipper) {
        return equipper.getInventory().add(equipper.getWeapon());
    }

    /// **** GETTERS AND SETTERS BELOW ****
    @Override
    public String getFullDescription(){
        StringBuilder desc = new StringBuilder();
        desc.append("Value: ").append(this.getValue()).append(" gold\n\n");
        if (accuracy != 0){
            desc.append("Accuracy:\t").append(this.accuracy).append("\n");
        }
        if (speed !=0){
            desc.append("Speed:\t").append(this.speed).append("\n");
        }
        if (physDamage != 0){
            desc.append("Physical Damage:\t").append(this.physDamage).append("\n");
        }
        if (spellDmg != 0){
            desc.append("Spell Damage:\t").append(this.spellDmg).append("\n");
        }
        if (strBonus != 0){
            desc.append("Strength Bonus:\t").append(this.strBonus).append("\n");
        }
        if (conBonus != 0){
            desc.append("Constitution Bonus:\t").append(this.conBonus).append("\n");
        }
        if (dexBonus != 0){
            desc.append("Dexterity Bonus:\t").append(this.dexBonus).append("\n");
        }
        if (intBonus != 0){
            desc.append("Intelligence Bonus:\t").append(this.intBonus).append("\n");
        }
        if (wisBonus != 0){
            desc.append("Wisdom Bonus:\t\t").append(this.wisBonus).append("\n");
        }
        if (charBonus != 0){
            desc.append("Charisma Bonus:\t").append(this.charBonus).append("\n");
        }

        desc.append(this.getDescription());

        return desc.toString();
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getSpeed() {
        return speed;
    }

    public void setPhysDamage(int physDamage) {
        this.physDamage = physDamage;
    }

    public void setSpellDmg(int spellDmg) {
        this.spellDmg = spellDmg;
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

    public void setStrBonus(int strBonus) {
        this.strBonus = strBonus;
    }

    public void setDexBonus(int dexBonus) {
        this.dexBonus = dexBonus;
    }

    public void setConBonus(int conBonus) {
        this.conBonus = conBonus;
    }

    public void setIntBonus(int intBonus) {
        this.intBonus = intBonus;
    }

    public void setWisBonus(int wisBonus) {
        this.wisBonus = wisBonus;
    }

    public void setCharBonus(int charBonus) {
        this.charBonus = charBonus;
    }

    public List<Attack> getAttackList() {
        return this.attackList;
    }

    public void setAttacks(ArrayList<Attack> attacks){
        this.attackList = attacks;
    }

    public void addAttack(Attack attack){
        this.attackList.add(attack);
    }

    public void addAttack(List<Attack> attacks){
        this.attackList.addAll(attacks);
    }

}

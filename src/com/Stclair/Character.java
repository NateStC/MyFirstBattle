package com.Stclair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Character {

    //    protected int strength;
//    protected int dexterity;
//    protected int constitution;
//    protected int intelligence;
//    protected int wisdom;
//    protected int charisma;
    protected String name;
    protected int level = 1;
    protected int health;
    private int experience = 0;
    private int nextLevelExperience = 100;
    private int maxHealth;
    protected int mana;
    private int maxMana;
    protected int[] attributeArray = {0, 0, 0, 0, 0, 0};
    private String[] attributeNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    Inventory inventory = new Inventory();


    public Character() {
        this("Character", 2, 2, 2, 2, 2, 2, 1);
    }

    public Character(String name) {
        this.name = name;
        this.level = 1;
    }


    public Character(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma, int level) {
        this.name = name;
        this.attributeArray[0] = strength;
        this.attributeArray[1] = dexterity;
        this.attributeArray[2] = constitution;
        this.attributeArray[3] = intelligence;
        this.attributeArray[4] = wisdom;
        this.attributeArray[5] = charisma;
        this.level = level;
        this.health = this.level * 5 + attributeArray[2] * 3;
        this.maxHealth = this.health;

//        if ((strength + dexterity + constitution + intelligence + wisdom + charisma) > 70) {
//            System.out.println("Looks like we've got a badass over here!");
//        }
    }

    public void assignStats(ArrayList<Integer> arrayRolls, Scanner scanner) {
        for (int i = 0; i < 6; i++) {
            boolean match = false;
            while (!match) {
                printStatRolls(arrayRolls);
                System.out.println("Which roll would you like to assign to " + attributeNames[i]);
                int choice = scanner.nextInt();
                if (arrayRolls.contains(choice)) {
                    attributeArray[i] = choice;
                    arrayRolls.remove(arrayRolls.indexOf(choice));
                    match = true;
                    break;
                } else {
                    System.out.println("Invalid choice");
                }
            }
        }

        setHealth();
        setMana();
    }

    private void printStatRolls(ArrayList<Integer> arrayRolls) {
        System.out.println("Your rolls:");
        for (int i = 0; i < arrayRolls.size(); i++) {
            System.out.println(arrayRolls.get(i));
        }
    }

    public boolean isDead() {
        if (this.health > 0) {
            return false;
        } else {
            return true;
        }
    }

    //this probably won't work
    public void equipWeapon(Weapon w) {
        this.attributeArray[0] += w.getStrengthBonus();
        this.attributeArray[1] += w.getDexterityBonus();
        this.attributeArray[2] += w.getStrengthBonus();
        this.attributeArray[3] += w.getIntelligenceBonus();
    }

    public void unequipWeapon(Weapon w) {
        this.attributeArray[0] -= w.getStrengthBonus();
        this.attributeArray[1] -= w.getDexterityBonus();
        this.attributeArray[2] -= w.getStrengthBonus();
        this.attributeArray[3] -= w.getIntelligenceBonus();
    }

    public boolean takeDamage(int damage) {
        this.health -= damage;
        if (this.health > 0) {
            System.out.println(this.name + " has " + this.health + " health left.");
            return true; // continue battle
        } else {
            System.out.println(this.name + " died!");
            return false;
        }
    }

    public void heal(int heal) {
        if (this.health + heal > this.maxHealth) {
            this.health = this.maxHealth;
        } else {
            this.health += heal;
        }
        System.out.println(this.name + " has " + this.health + " health left.");
    }


    // ********* ATTACKS ***********

    public int stab() {

        int roll = d6roll();

        if (roll == 1) {
            System.out.println(this.name + "'s stab missed!");
            return 0;
        }

        if (roll < 6 && roll > 1) {
            int stabDamage = roll + (((this.attributeArray[0] * 2) + this.attributeArray[1]) / 5);
            System.out.println(this.name + " hits with stab for " + stabDamage + " damage.");
            return stabDamage;
        } else {
            int stabDamage = ((this.attributeArray[0] * 4) + this.attributeArray[1] + 12) / 5;
            System.out.println("Critical hit! " + this.name + " hits with stab for " + stabDamage + " damage!");
            return stabDamage;
        }
    }


    // ********* SPELLS ***********

    public int fireball(String targetName) {
        if (this.mana >= 10) {
            this.mana -= 10;
            int spellRoll = d6roll();
            if (spellRoll > 1) {
                int damage = spellRoll + (this.getStat(3) / 2);
                if (spellRoll == 6) {
                    damage *= 2;
                }
                System.out.println(this.name + " hits " + targetName + " with Fireball for " + damage + " damage.");
                printMana();
                return damage;
            } else {
                System.out.println(this.name + " missed!");
                printMana();
                return 0;
            }
        } else {
            System.out.println("Insufficient mana");
            return 0;
        }
    }

    public int healingSpell() {
        if (this.mana >= 10) {
            int spellRoll = d6roll();
            this.mana -= 10;
            int heal = (this.getStat(3) + this.getStat(4) + this.getStat(5)) / 6 + spellRoll;
            if (spellRoll == 6) {
                heal *= 2;
                System.out.println("Healing spell was critical!");
            }
            System.out.println("Healing spell heals " + heal + " damage.");
            printMana();
            return heal;
        } else {
            System.out.println("Insufficient mana");
            return 0;
        }
    }

    public void printStats() {
        for (int i = 0; i < 6; i++) {
            System.out.println(attributeNames[i] + ": " + attributeArray[i]);
        }


//        System.out.println(this.name + "'s attributes are");
//        System.out.println("LEVEL: " + this.level);
//        System.out.println("STRENGTH: " + this.strength);
//        System.out.println("DEXTERITY: " + this.dexterity);
//        System.out.println("CONSTITUTION: " + this.constitution);
//        System.out.println("INTELLIGENCE: " + this.intelligence);
//        System.out.println("WISDOM: " + this.wisdom);
//        System.out.println("CHARISMA: " + this.charisma);
        System.out.println("HEALTH: " + this.health + " / " + this.maxHealth);
        System.out.println("MANA: " + this.mana + " / " + this.maxMana);
        System.out.println((this.nextLevelExperience - this.experience) + " experience until level " + (this.level + 1));

    }

    public void printMana() {
        System.out.println(this.name + " has " + this.mana + " / " + this.maxMana + " mana left.");
    }


    public void gainExperience(int experience) {
        this.experience += experience;
        if (this.experience > this.nextLevelExperience) {
            gainLevel();
        }
    }


    public void gainLevel() {
        Scanner scanner = new Scanner(System.in);
        this.level += 1;
        this.nextLevelExperience += this.level * (this.level * 100);
        System.out.println("Congratulations! You leveled up! " + this.name + " is now level " + this.level + ".");
        System.out.println("You have gained a skill point. Which attribute would you like to apply it to?");
        System.out.println("Strength, dexterity, constitution, intelligence, wisdom, or charisma?");
        String stat = scanner.nextLine();

        switch (stat.toLowerCase()) {
            case "strength":
                this.attributeArray[0] += 1;
                System.out.println("Your strength has been increased to " + this.attributeArray[0]);
                break;
            case "dexterity":
                this.attributeArray[1] += 1;
                System.out.println("Your dexterity has been increased to " + this.attributeArray[1]);
                break;
            case "constitution":
                this.attributeArray[2] += 1;
                System.out.println("Your constitution has been increased to " + this.attributeArray[2]);
                break;
            case "intelligence":
                this.attributeArray[3] += 1;
                System.out.println("Your intelligence has been increased to " + this.attributeArray[3]);
                break;
            case "wisdom":
                this.attributeArray[4] += 1;
                System.out.println("Your wisdom has been increased to " + this.attributeArray[4]);
                break;
            case "charisma":
                this.attributeArray[5] += 1;
                System.out.println("Your charisma has been increased to " + this.attributeArray[5]);
                break;
        }
        this.maxHealth = this.level * 5 + this.attributeArray[2] * 3;
        setHealth();
        setMana();
        System.out.println("Health is now " + this.health + " / " + this.maxHealth);
        System.out.println("Mana is now " + this.mana + " / " + this.maxMana);
    }


    // ********* DICE ROLLS **********

    public static int d6roll() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }


    /// **** GETTERS AND SETTERS BELOW ****

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setHealth() {
        this.health = this.attributeArray[2] * 3 + this.level * 5;
        this.maxHealth = this.health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setMana() {
        this.mana = this.attributeArray[3] * 3 + this.level * 5;
        this.maxMana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStat(int stat) {
        return attributeArray[stat];
    }

    public void setStats(int s, int d, int con, int i, int w, int ch) {
        attributeArray[0] = s;
        attributeArray[1] = d;
        attributeArray[2] = con;
        attributeArray[3] = i;
        attributeArray[4] = w;
        attributeArray[5] = ch;
    }

    public void setStrength(int strength){
        attributeArray[0] = strength;
    }

    public void setDexterity(int dexterity){
        attributeArray[1] = dexterity;
    }

    public void setConstitution(int constitution){
        attributeArray[2]=constitution;
    }

    public void setIntelligence(int intelligence){
        attributeArray[3] = intelligence;
    }

    public void setWisdom(int wisdom){
        attributeArray[4] = wisdom;
    }

    public void setCharisma(int charisma){
        attributeArray[5] = charisma;
    }

    public int getStrength(){
        return attributeArray[0];
    }

    public int getDexterity(){
        return attributeArray[1];
    }

    public int getConstitution(){
        return attributeArray[2];
    }

    public int getIntelligence(){
        return attributeArray[3];
    }

    public int getWisdom(){
        return attributeArray[4];
    }

    public int getCharisma(){
        return attributeArray[5];
    }
}

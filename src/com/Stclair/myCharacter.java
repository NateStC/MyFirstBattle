package com.Stclair;

import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class myCharacter {

    protected String name;
    public int health;
    public int mana;
    public int experience = 0;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private static String[] attributeNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
    private Weapon weapon;
    Inventory inventory = new Inventory();


    //empty constructor
    public myCharacter() {
        this.name = "Character";
        setDefaultStats();
        fullHealth();
        fullMana();
    }

    //simple named character w/default stats
    public myCharacter(String name) {
        super();
        this.name = name;
    }

    //construct character w/ attribute array
    public myCharacter(String name, int[] stats, int experience, int health, int mana) {
        this.name = name;
        setStats(stats);
        this.experience = experience;
        this.health = (health);
        this.mana = (mana);
    }

    //construct character w/ attributes, exp, hp, and mana in Array
    public myCharacter(String name, int[] allStats) {
        this.name = name;
        setStats(allStats);
    }

    //load character constructor
    public myCharacter(String name, int health, int mana, int experience, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.experience = experience;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;

        //todo figure a way to load inventory
    }

    //new character constructor
    public myCharacter(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom,
                       int charisma, Weapon weapon) {
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.health = getMaxHealth();
        this.mana = getMaxMana();
        this.weapon = weapon;
    }

    public void assignStats(ArrayList<Integer> arrayRolls, Scanner scanner) {
        int[] attributeArray = new int[5];
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
                } else {
                    System.out.println("Invalid choice");
                }
            }
        }
        setStats(attributeArray);
        fullHealth();
        fullMana();
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

    //returns true if still alive, false if dead
    public boolean takeDamage(int damage) {
        this.health -= (damage);
        if (this.health <= 0) {
            health = (0);
            System.out.println(this.name + " died!");
            return false;
        }
        System.out.println(this.name + " has " + this.health + " health left.");
        return true; // continue battle
    }

    public void drainMana(int mana) {
        this.mana -= mana;
    }

    public void heal(int heal) {
        this.health += heal;
        if (this.health > getMaxHealth()) {
            this.health = (getMaxHealth());
        }

        System.out.println(this.name + " has " + this.health + " health left.");
    }

    public void restoreMana(int mana) {
        if (this.mana + mana > getMaxMana()) {
            fullMana();
        } else {
            this.mana += mana;
        }
    }


    // ********* ATTACKS ***********

    public int stab() {

        int att = Dice.d20();
        int roll = d6roll();
        int damage = 0;

        if (att == 1) {
            System.out.println(this.name + "'s stab missed!");
        } else {
            damage = (roll + ((this.strength + this.dexterity) / 2));
        }
        if (roll == 20) {
            damage *= 2;
            System.out.println("Critical hit!");
        }
        System.out.println(this.name + " hits with stab for " + damage + " damage.");
        return damage;
    }

    public int smash() {
        int att = Dice.d20();
        int roll = Dice.d8();
        int dmg = 0;
        if (att == 1) {
            System.out.println(this.name + "'s smash missed!");
            return dmg;
        }
        dmg = (roll + (this.strength / 2));
        if (roll == 20) {
            dmg *= 2;
            System.out.println("Critical hit!");
        }
        System.out.println("Smash deals " + dmg + " damage");
        return dmg;
    }


    // ********* SPELLS ***********

    public int fireball() {
        if (this.mana < 10) {
            System.out.println("Insufficient mana");
            return -1;
        }
        this.mana -= 10;
        int attRoll = Dice.d20();
        int damRoll = d6roll();
        int dmg = 0;
        if (attRoll == 1) {
            System.out.println(this.name + " missed!");
            printMana();
            return dmg;
        }
        dmg = damRoll + (intelligence / 2);
        if (attRoll == 20) {
            dmg *= 2;
            System.out.println("Critical hit!");
        }
        System.out.println(this.name + " hits with Fireball for " + dmg + " damage.");
        printMana();
        return dmg;

    }


    public int[] healingSpell() {
        if (this.mana < 10) {
            System.out.println("Insufficient mana for healing spell");
            return new int[]{-1, -1};
        }
        if (healthIsFull()) {
            System.out.println("Health already full");
            return new int[]{-2, -2};
        }
        int crits = 0;
        int roll1 = d6roll();
        int roll2 = d6roll();
        if (roll1 == 6) {
            roll1 = 12;
            crits += 1;
        }
        if (roll2 == 6) {
            roll2 = 12;
            crits += 1;
        }
        this.mana -= 10;
        int heal = ((this.intelligence + this.wisdom + this.charisma) / 2) + roll1 + roll2;
        System.out.println("Healing spell heals " + heal + " damage.");
        printMana();
        return new int[]{heal, crits};
    }

    public void printStats() {
        System.out.println(this.name + "'s attributes are");
        System.out.println("LEVEL: " + getLevel());
        System.out.println("STRENGTH: " + this.strength);
        System.out.println("DEXTERITY: " + this.dexterity);
        System.out.println("CONSTITUTION: " + this.constitution);
        System.out.println("INTELLIGENCE: " + this.intelligence);
        System.out.println("WISDOM: " + this.wisdom);
        System.out.println("CHARISMA: " + this.charisma);
        System.out.println("HEALTH: " + this.health + " / " + getMaxHealth());
        System.out.println("MANA: " + this.mana + " / " + getMaxMana());
        System.out.println((getNextLvlExp() - this.experience) + " experience until level " + (getLevel() + 1));

    }

    public void printMana() {
        System.out.println(this.name + " has " + this.mana + " / " + getMaxMana() + " mana left.");
    }

    public void gainLevel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Congratulations! You leveled up! " + this.name + " is now level " + this.getLevel() + ".");
        System.out.println("You have gained a skill point. Which attribute would you like to apply it to?");
        System.out.println("Strength, dexterity, constitution, intelligence, wisdom, or charisma?");
        String stat = scanner.nextLine();

        switch (stat.toLowerCase()) {
            case "strength":
                this.strength += 1;
                System.out.println("Your strength has been increased to " + this.strength);
                break;
            case "dexterity":
                this.dexterity += 1;
                System.out.println("Your dexterity has been increased to " + this.dexterity);
                break;
            case "constitution":
                this.constitution += 1;
                System.out.println("Your constitution has been increased to " + this.constitution);
                break;
            case "intelligence":
                this.intelligence += 1;
                System.out.println("Your intelligence has been increased to " + this.intelligence);
                break;
            case "wisdom":
                this.wisdom += 1;
                System.out.println("Your wisdom has been increased to " + this.wisdom);
                break;
            case "charisma":
                this.charisma += 1;
                System.out.println("Your charisma has been increased to " + this.charisma);
                break;
        }

        fullHealth();
        fullMana();
        System.out.println("Health is now " + this.health + " / " + getMaxHealth());
        System.out.println("Mana is now " + this.mana + " / " + getMaxMana());
        scanner.close();
    }


    private void setDefaultStats() {
        this.strength = 5;
        this.dexterity = 5;
        this.constitution = 5;
        this.intelligence = 5;
        this.wisdom = 5;
        this.charisma = 5;
        fullHealth();
        fullMana();
        this.experience = 0;
    }

    public void setStats(int str, int con, int dex, int intel, int wis, int cha) {
        this.strength = str;
        this.constitution = con;
        this.dexterity = dex;
        this.intelligence = intel;
        this.wisdom = wis;
        this.charisma = cha;
        this.experience = 0;
        fullMana();
        fullHealth();
    }

    public void setStats(int str, int con, int dex, int intel, int wis, int cha, int lvl) {
        this.strength = str;
        this.constitution = con;
        this.dexterity = dex;
        this.intelligence = intel;
        this.wisdom = wis;
        this.charisma = cha;
        setLevel(lvl);
        fullMana();
        fullHealth();
    }

    public void setStats(int str, int con, int dex, int intel, int wis, int cha, int exp, int health, int mana) {
        this.strength = str;
        this.constitution = con;
        this.dexterity = dex;
        this.intelligence = intel;
        this.wisdom = wis;
        this.charisma = cha;
        this.experience = exp;
        this.health = health;
        this.mana = mana;
    }

    public void setStats(int[] stats) {
        if (stats.length == 6) {
            this.strength = stats[0];
            this.constitution = stats[1];
            this.dexterity = stats[2];
            this.intelligence = stats[3];
            this.wisdom = stats[4];
            this.charisma = stats[5];
        } else if (stats.length == 9) {
            this.strength = stats[0];
            this.constitution = stats[1];
            this.dexterity = stats[2];
            this.intelligence = stats[3];
            this.wisdom = stats[4];
            this.charisma = stats[5];
            this.experience = stats[6];
            this.health = stats[7];
            this.mana = stats[8];
        } else {
            setDefaultStats();
        }
    }

    public static int getLevel(int experience) {
        //todo figure out how to loop to getExpForLvl() method
//        if (experience<100){
//            return 1;
//        }
//        for (int lvl = 1; lvl <= 20; lvl++) {
//            if (experience < getExpForLvl(lvl)) {
//                return lvl-1;
//            }
//            System.out.println("Not lvl " + lvl);
//            lvl+=1;
//        }
//        return 1;
        if (experience < 100) {
            return 1;
        } else if (experience < 300) {
            return 2;
        } else if (experience < 500) {
            return 3;
        } else if (experience < 800) {
            return 4;
        } else if (experience < 1200) {
            return 5;
        } else if (experience < 1700) {
            return 6;
        } else if (experience < 2250) {
            return 7;
        } else if (experience < 2900) {
            return 8;
        } else if (experience < 4000) {
            return 9;
        } else if (experience < 5250) {
            return 10;
        } else if (experience < 6750) {
            return 11;
        } else if (experience < 8750) {
            return 12;
        } else if (experience < 12000) {
            return 13;
        } else if (experience < 20000) {
            return 14;
        } else return 15;
    }

    public void setLevel(int level) {
        this.experience = getExpForLvl(level);
    }

    public int getNextLvlExp() {
        System.out.println("Next lvl is level " + (this.getLevel()+1));
        return (getExpForLvl(this.getLevel() + 1));
    }

    public static int getExpForLvl(int lvl) {
        switch (lvl) {
            case 2:
                return 100;
            case 3:
                return 300;
            case 4:
                return 500;
            case 5:
                return 800;
            case 6:
                return 1200;
            case 7:
                return 1700;
            case 8:
                return 2250;
            case 9:
                return 2900;
            case 10:
                return 4000;
            case 11:
                return 5250;
            case 12:
                return 6750;
            case 13:
                return 8750;
            case 14:
                return 12000;
            case 15:
                return 20000;
            default:
                return 0;
        }
    }

    //returns true if leveled up
    public boolean gainExp(int exp) {
        System.out.println("gained " + exp + " EXP");
        int nextlvl = getNextLvlExp();
        this.experience += exp;
        System.out.println(this.name + " has " + experience + " total EXP");
        System.out.println("myCharatcter.gainExp() testing if " + this.getExperience() + " >= " + nextlvl);
        return this.getExperience() >= nextlvl;
    }

    //returns total exp per passed lvl ie 200xp for lvl 2 to 3;
    public static int getExpDifferenceForLvl(int lvl) {
        return (getExpForLvl(lvl + 1) - getExpForLvl(lvl));
    }

    public double getExpProgPct(){
        double prog = getExpProgress();
        double expForLvl = getExpDifferenceForLvl(this.getLevel());

        return prog/expForLvl;
    }

    //returns experience gained since last lvl up
    public int getExpProgress() {
        return this.getExperience() - getExpForLvl(this.getLevel());
    }

    // ********* DICE ROLLS **********

    public static int d6roll() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }


    /// **** GETTERS AND SETTERS BELOW ****

    public boolean healthIsFull() {
        return (this.getHealth() >= this.getMaxHealth());
    }

    public boolean manaIsFull() {
        return (this.getMana() >= this.getMaxMana());
    }

    //returns true
    public boolean castCheck(Spell spell){
        return (this.mana>spell.getManaCost());
    }

//    public SimpleDoubleProperty getExpObsv() {
//        return new SimpleDoubleProperty(new SimpleDoubleProperty(this.getExpProgress()).doubleValue() / ((double) this.getNextLvlExp()));
//    }

    public int getLevel() {
        return getLevel(this.experience);
    }

    public int getExperience() {
        return this.experience;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return this.constitution * 3 + (getLevel() * 5);
    }

    public void fullHealth() {
        this.health = getMaxHealth();
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return this.intelligence * 3 + getLevel() * 5;
    }

    public void fullMana() {
        this.mana = getMaxMana();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public int getConstitution() {
        return this.constitution;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public int getWisdom() {
        return this.wisdom;
    }

    public int getCharisma() {
        return this.charisma;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getStrStat(){
        return this.strength + this.weapon.getStrBonus();
    }

    public int getConStat(){
        return this.constitution + this.weapon.getConBonus();
    }

    public int getDexStat(){
        return this.dexterity + this.weapon.getDexBonus();
    }

    public int getIntStat(){
        return this.intelligence + this.weapon.getIntBonus();
    }

    public int getWisStat(){
        return this.wisdom + this.weapon.getWisBonus();
    }

    public int getChaStat(){
        return  this.charisma + this.weapon.getCharBonus();
    }
}

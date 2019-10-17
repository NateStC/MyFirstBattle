package com.Stclair;

import java.util.*;

public class myCharacter {

    protected String name;
    private int health = 50;
    private int mana = 30;
    private int experience = 0;
    private int strength = 7;
    private int dexterity = 7;
    private int constitution = 7;
    private int intelligence = 7;
    private int wisdom = 7;
    private int charisma = 7;
    private int[] attributes = new int[6];
    private int gold = 0;
    private Weapon weapon = new Weapon();
    private Armor armor = new Armor();
    private Inventory inventory = new Inventory();
    private ArrayList<Attack> attacks = new ArrayList<>();
    private static final Map<Attribute, Integer> stats = new HashMap<>();


    static {
        stats.put(Attribute.STR, 0);
        stats.put(Attribute.CON, 1);
        stats.put(Attribute.DEX, 2);
        stats.put(Attribute.INT, 3);
        stats.put(Attribute.WIS, 4);
        stats.put(Attribute.CHA, 5);
    }

    public enum Attribute { //todo finish switching attributes over to map with enum
        STR,
        CON,
        DEX,
        INT,
        WIS,
        CHA,
    }


    public static String[] attributeNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};


    //empty constructor
    public myCharacter() {
    }

    //simple named character w/default stats
    public myCharacter(String name) {
        super();
        this.name = name;
    }

    public myCharacter(String name, int lvl) {
        setName(name);
        setLevel(lvl);
        setDefaultStats();
        rollLvledStats();
        gold = Dice.goldDice(lvl, 1);
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
    public myCharacter(String name, int[] allStats, Weapon weapon, Armor armor) {
        this.name = name;
        setStats(allStats);
        equipWeapon(weapon);
        equipArmor(armor);
    }

    //load character constructor
    public myCharacter(String name, int health, int mana, int experience, int strength, int dexterity,
                       int constitution, int intelligence, int wisdom, int charisma) {
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
                       int charisma, Weapon weapon, Armor armor) {
        this.name = name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
        this.mana = getMaxMana();
        this.weapon = weapon;
        this.armor = armor;

        this.health = getMaxHealth();
    }

    public void assignStats(ArrayList<Integer> arrayRolls, Scanner scanner) {
        int[] attributeArray = new int[6];
        int i = 0;
        while (i <= 5) {
            System.out.println(arrayRolls);
            System.out.println("Which roll would you like to assign to " + attributeNames[i]);
            int choice = scanner.nextInt();
            if (arrayRolls.contains(choice)) {
                attributeArray[i] = choice;
                arrayRolls.remove((Integer) choice);
                i++;
            } else {
                System.out.println("Invalid choice");
            }
        }
        setStats(attributeArray);
        fullHealth();
        fullMana();
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    //returns true if still alive, false if dead
    public boolean takeDamage(int damage) {
        if (isDead()) {
            return false;
        }
        if (damage == 0) {
            return true;
        }
        this.health -= (damage);
        if (this.health <= 0) {
            health = (0);
            System.out.println(this.name + " died!");
            return false;
        }
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

    public void fullRestore() {
        fullMana();
        fullHealth();
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


    public void setDefaultStats() {
        this.strength = 7;
        this.dexterity = 7;
        this.constitution = 7;
        this.intelligence = 7;
        this.wisdom = 7;
        this.charisma = 7;
        fullHealth();
        fullMana();
    }

    public void rollLvledStats() {
        this.strength += Dice.die((getLevel() + 1) / 2);
        this.constitution += Dice.die((getLevel() + 1) / 2);
        this.dexterity += Dice.die((getLevel() + 1) / 2);
        this.intelligence += Dice.die((getLevel() + 1) / 2);
        this.wisdom += Dice.die((getLevel() + 1) / 2);
        this.charisma += Dice.die((getLevel() + 1) / 2);

    }

    public void setStats(int str, int con, int dex, int intel, int wis, int cha) {
        this.strength = str;
        this.constitution = con;
        this.dexterity = dex;
        this.intelligence = intel;
        this.wisdom = wis;
        this.charisma = cha;
        this.experience = 0;
    }

    public void setStats(int str, int con, int dex, int intel, int wis, int cha, int lvl) {
        this.strength = str;
        this.constitution = con;
        this.dexterity = dex;
        this.intelligence = intel;
        this.wisdom = wis;
        this.charisma = cha;
        setLevel(lvl);
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
        //todo test with debugger
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
        this.experience += exp;
        return this.getExperience() >= getNextLvlExp();
    }

    //returns total exp per passed lvl i.e. 200xp for lvl 2 to 3, primarily for experience progress bar
    public static int getExpDifferenceForLvl(int lvl) {
        return (getExpForLvl(lvl + 1) - getExpForLvl(lvl));
    }

    public double getExpProgPct() {
        double prog = getExpProgress();
        double expForLvl = getExpDifferenceForLvl(this.getLevel());
        return prog / expForLvl;
    }

    //returns experience gained since last lvl up
    public int getExpProgress() {
        return this.getExperience() - getExpForLvl(this.getLevel());
    }


    // ********* Loot **********

    public boolean receiveLoot(Item item) {
        if (item == null || item.getQuantity() < 1){
            return false;
        }
        if (!inventory.isFull()) {
            this.inventory.add(item);
            return true;
        }
        return false;
    }

    public boolean receiveLoot(List<Item> items) {
        if (items.size() > inventory.getSpace()) {
            System.out.println("Not enough room for all items");
            return false;
        }
        for (Item i : items){
            receiveLoot(i);
        }
        return true;
    }

    public List<Item> equippedItems(){
        List<Item> equipment = new ArrayList<>();
        if (armor != null){
            equipment.add(armor);
        }
        if (weapon != null && !weapon.getName().toLowerCase().contains("fists")){ //todo find a way to make fists non lootable on unarmed enemies
            equipment.add(armor);
        }
        return equipment;
    }

    public List<Item> loot() {
        List<Item> items = new ArrayList<>();
        List<Item> allLoot = new ArrayList<>(inventory);
        allLoot.addAll(equippedItems());
        Random rand = new Random();
        for (Item i : allLoot) {
            int roll = rand.nextInt(100) + 1;
            if (roll <= i.getDropPct()) {
                items.add(i);
            }
        }
        return items;
    }

    public void loseItem(int index) {
        inventory.remove(index);
    }


    /// **** GETTERS AND SETTERS BELOW ****

    public boolean healthIsFull() {
        return (this.getHealth() >= this.getMaxHealth());
    }

    public boolean manaIsFull() {
        return (this.getMana() >= this.getMaxMana());
    }

    public List<Attack> getAttacks() {
        List<Attack> allAttacks = new ArrayList<>();
        allAttacks.addAll(this.weapon.getAttackList());
        allAttacks.addAll(this.attacks);
        return allAttacks;
    }

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
        return getConStat() * 15 + (getLevel() * 20);
    }

    public void fullHealth() {
        this.health = getMaxHealth();
    }

    public double getHealthPct() {
        return (double) this.health / (double) this.getMaxHealth();
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return this.getIntStat() * 5 + getLevel() * 5;
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

    public Armor getArmor() {
        return this.armor;
    }

    public int getStrBonus() {
        return this.weapon.getStrBonus() + this.armor.getStrBonus();
    }

    public int getConBonus() {
        return this.weapon.getConBonus() + this.armor.getConBonus();
    }

    public int getDexBonus() {
        return this.weapon.getDexBonus() + this.armor.getDexBonus();
    }

    public int getIntBonus() {
        return this.weapon.getIntBonus() + this.armor.getIntBonus();
    }

    public int getWisBonus() {
        return this.weapon.getWisBonus() + this.armor.getWisBonus();
    }

    public int getChaBonus() {
        return this.weapon.getCharBonus() + this.armor.getChaBonus();
    }

    public void equipWeapon(Weapon weapon) {
        if (this.weapon == null) {
            this.weapon = weapon;
        } else if (inventory.isFull()) {
            return;
        } else {
            inventory.add(this.weapon);
            inventory.remove(weapon);
            this.weapon = weapon;
        }
    }

    public boolean equipArmor(Armor armor) {
        if (this.armor == null) {
            this.armor = armor;
            return true;
        }

        if (inventory.isFull()) {
            System.out.println("Cannot unequip current armor, inventory is full");
            return false;
        } else {
            inventory.remove(armor);
            inventory.add(this.armor);
            this.armor = armor;
            return true;
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public int getStrStat() {
        return this.strength + this.getStrBonus();
    }

    public int statMod(Attribute stat) {
        return (stats.get(stat) - 10) / 2;
    }

    public int getConStat() {
        return this.constitution + this.getConBonus();
    }

    public int getConMod() {
        return (this.getConStat() - 10) / 3;
    }

    public int getDexStat() {
        return this.dexterity + this.getDexBonus();
    }

    public int getDexMod() {
        return this.dexterity = 10 / 3;
    }

    public int getIntStat() {
        return this.intelligence + this.getIntBonus();
    }

    public int getIntMod() {
        return (this.getIntStat() - 10) / 3;
    }

    public int getWisStat() {
        return this.wisdom + this.getWisBonus();
    }

    public int getWisMod() {
        return (this.getWisStat() - 10) / 3;
    }

    public int getChaStat() {
        return this.charisma + this.getChaBonus();
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void gainGold(int gold) {
        this.gold += gold;
    }

    public void loseGold(int gold) {
        this.gold -= gold;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setAttacks(ArrayList<Attack> attacks) {
        this.attacks = attacks;
    }
}

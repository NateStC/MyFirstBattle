package com.Stclair;

import java.io.Serializable;

public class Player extends myCharacter implements Serializable {

    private int totalKills = 0;
    private int roundsCompleted = 0;
    private int totalDeaths;

    //new player constructor
    public Player(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom,
                       int charisma, Weapon weapon, Armor armor) {
        super(name,strength, dexterity, constitution, intelligence, wisdom, charisma, weapon, armor);
        this.health = getMaxHealth();
        this.mana = getMaxMana();
    }

    //full constructor/ load player
    public Player(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom,
                  int charisma, Weapon weapon, Armor armor, int experience, int health, int mana, int totalKills,
                  int roundsCompleted, int deaths, Inventory inventory) {
        super(name, strength, dexterity, constitution, intelligence, wisdom, charisma, weapon, armor);
        setExperience(experience);
        this.health = health;
        this.mana = mana;
        this.totalKills = totalKills;
        this.roundsCompleted = roundsCompleted;
        this.totalDeaths = deaths;
        this.inventory = inventory;
    }

    public void addDeath(){
        this.totalDeaths++;
    }

    public void addKill(){
        this.totalKills++;
    }

    public void roundCompleted(){
        this.roundsCompleted ++;
    }

    public int getTotalKills(){
        return this.totalKills;
    }

    public int getRoundsCompleted(){
        return this.roundsCompleted;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }
}

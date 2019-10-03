package com.Stclair;

import java.io.Serializable;

public class Player extends myCharacter implements Serializable {

    private int totalKills = 0;
    private int roundsCompleted = 0;
    private int totalDeaths = 0;
    private int killStreak = 0;
    private int roundStreak = 0;
    private int highestKillStreak=0;
    private int highestRoundStreak=0;

    //new player constructor
    public Player(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom,
                       int charisma, Weapon weapon, Armor armor) {
        super(name,strength, dexterity, constitution, intelligence, wisdom, charisma, weapon, armor);
        this.health = getMaxHealth();
        this.mana = getMaxMana();
    }

    //full constructor/ load player
    public Player(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom,
                  int charisma, Weapon weapon, Armor armor, Inventory inventory, int experience, int health, int mana, int totalKills,
                  int roundsCompleted, int deaths, int killStreak, int highestKillStreak, int roundStreak, int highestRoundStreak) {
        super(name, strength, dexterity, constitution, intelligence, wisdom, charisma, weapon, armor);
        setExperience(experience);
        this.health = health;
        this.mana = mana;
        this.totalKills = totalKills;
        this.roundsCompleted = roundsCompleted;
        this.totalDeaths = deaths;
        this.inventory = inventory;
        this.killStreak = killStreak;
        this.highestKillStreak = highestKillStreak;
        this.roundStreak = roundStreak;
        this.highestRoundStreak = highestRoundStreak;
    }

    public void addDeath(){
        this.totalDeaths++;
        this.killStreak = 0;
        this.roundStreak = 0;
    }

    public void addKill(){
        this.totalKills++;
        this.killStreak ++;
        if (this.killStreak > highestKillStreak){
            this.highestKillStreak = this.killStreak;
        }
    }

    public void roundCompleted(){
        this.roundsCompleted ++;
        this.roundStreak ++;
        if (this.roundStreak > highestRoundStreak){
            this.highestRoundStreak = this.roundStreak;
        }
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

    public int getKillStreak() {
        return killStreak;
    }

    public int getRoundStreak() {
        return roundStreak;
    }

    public int getHighestKillStreak() {
        return highestKillStreak;
    }

    public int getHighestRoundStreak() {
        return highestRoundStreak;
    }
}

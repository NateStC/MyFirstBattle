package com.Stclair;

public class Spell extends Attack {

    private int manaCost;
    private int heal;

    //full constructor
    public Spell(String name, int manaCost, int accuracy, int damage, int heal, boolean isCrit) {
        super(name, accuracy, damage, isCrit);
        this.manaCost = manaCost;
        this.heal = heal;
    }

    //full attack spell constructor
    public Spell(String name, int manaCost, int accuracy, int damage, boolean isCrit) {
        super(name, accuracy, damage, isCrit);
        this.heal = 0;
        this.manaCost = manaCost;
    }

    //healing spell
    public Spell(String name, int manaCost, int heal, boolean isCrit) {
        super(name, isCrit);
        this.manaCost = manaCost;
        this.heal = heal;
    }

    public static Spell fireball(myCharacter caster) {
        int cost = getFireBallCost(caster);
        if (cost > caster.getMana()){
            return new Spell("Fireball", 0, -1,-1, false);
        }
        int dmg = Dice.d12() + (caster.getIntStat() / 4) + caster.getLevel();
        boolean isCrit = false;
        int acc = Dice.d20();
        if (acc == 1) {
            return new Spell("Fireball", cost, acc, 0, false);
        }
        if (acc == 20) {
            isCrit = true;
            dmg *= 2;
        }
        return new Spell("Fireball", cost, acc, dmg, isCrit);
    }

    public static int getFireBallCost(myCharacter caster){
        return 15 + (caster.getLevel() * 2) - (caster.getWisStat() / 3);
    }

    // Electrocution spell that relies more on player stats than dice
    //todo test staticShock
    public static Spell staticShock(myCharacter caster) {
        int cost = getStaticShockCost(caster);
        if (cost > caster.getMana()){
            return new Spell("Static Shock", 0,-1,-1,false);
        }
        int acc = Dice.d20();
        int dmg = Dice.d8() + (caster.getIntStat() / 2) + caster.getLevel();
        boolean isCrit = false;
        if (acc > 1) {
            acc += (caster.getIntStat() + caster.getWisStat()) / 10;
        }
        if (acc >= 20) {
            isCrit = true;
            dmg *= 2;
        }
        if (acc == 1) {
            dmg = 0;
        }
        return new Spell("Static Shock", cost, acc, dmg, isCrit);
    }

    public static int getStaticShockCost(myCharacter caster){
        return 20 + (caster.getLevel() * 2) - (caster.getWisStat() / 3);
    }

    public static Spell healingHands(myCharacter caster) {
        int cost = getHealingHandsCost(caster);
        if (cost > caster.getMana()) {
            System.out.println("Not enough mana for healing hands");
            return new Spell("Healing hands", 0, -1, -1, false);
        }
        int roll1 = Dice.d12();
        int roll2 = Dice.d12();
        int heal = roll1 + roll2 + (int) ((caster.getChaStat() + caster.getIntStat()) / 1.5);
        boolean crit = false;
        if (roll1 == 6 || roll2 == 6) {
            crit = true;
            heal *= 2;
        }
        return new Spell("Healing Hands", cost, heal, crit);
    }

    public static int getHealingHandsCost(myCharacter caster){
        return 12 + (caster.getLevel() * 3) - (caster.getWisStat() / 3);
    }

    //lvlReq 3?
    //todo test drainLife spell
    public static Spell drainLife(myCharacter caster) {
        int cost = getDrainlifeCost(caster);
        if (cost > caster.getMana()) {
            System.out.println("Not enough mana for Drain Life");
            return new Spell("Drain Life", 0, -1, -1, false);
        }
        int acc = Dice.d20();
        int dmg = Dice.d12() + ((caster.getIntStat() + caster.getWisStat() + caster.getChaStat()) / 2) +
                caster.getWeapon().getPhysDamage() + caster.getWeapon().getSpellDmg();
        boolean crit = false;
        if (acc == 1) {
            return new Spell("Drain life", cost, acc, 0, false);
        }
        if (acc == 20) {
            crit = true;
            dmg *= 2;
        }
        int heal = (int) (dmg / .75);
        return new Spell("Drain Life", cost, acc, dmg, heal, crit);
    }

    public static int getDrainlifeCost(myCharacter caster){
        return 20 + (caster.getLevel() * 3) - ((caster.getWisStat() + caster.getDexStat()) / 6);
    }

    public static Spell fireArrow(myCharacter caster){
        int cost = getFireArrowCost(caster);
        if (cost > caster.getMana()){
            return new Spell("Fire Arrow",0,-1,-1,false);
        }
        int acc = Dice.d20();
        int dmg = Dice.d12() + ((caster.getDexStat() + caster.getIntelligence())/4);
        boolean crit = false;
        if (acc == 0){
            dmg = 0;
        } else {
            acc += caster.getDexStat()-caster.getLevel();
            if (acc>27){
                crit = true;
                dmg*=2;
            }
        }
        return new Spell("Fire Arrow", cost, acc,crit);
    }

    public static int getFireArrowCost(myCharacter caster){
        return 15 + (caster.getLevel()*2)- ((caster.getWisStat() + caster.getDexStat())/4);
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getHeal() {
        return heal;
    }
}

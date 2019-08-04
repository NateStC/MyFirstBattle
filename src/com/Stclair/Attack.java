package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class Attack {

    //todo move manaCost and spellDamage from Spell to attack
    // Attack/doAttack() goes off of str and dex, Spell/doAttack() goes off of int and wis
    // Ranged/doAttack() goes off of dex and factors hit chances

    private String name;
    private int dmgDie = 6;
    private double strMultiplier = 0;
    private double dexMultiplier = 0;
    private double intMultiplier = 0;
    private double wisMultiplier = 0;
    private double chaMultiplier = 0;
    private double lvlMultiplier = 1;
    private double physDmgMultiplier = 1;
    private double spellDmgMultiplier = 0;
    private int hitPoint = 1;
    private int critPoint = 20;
    private int dmgRolls = 1;
    private int numOfAttacks = 1;
    private int healDie = 0;
    private double accMultiplier = 0;
    private int manaCost = 0;
    private double hpDrainMultiplier = 0;
    private double costMultiplier = 1;

    //todo find a way to apply buffs/debuffs to attacks
    //todo add description String for attacks?
//    private String description;
    //private int lvlReq;

    //empty constructor
    public Attack() {
        this.name = "Bash";
        dexMultiplier = .33;
    }

    public Attack(String name, int dmgDie, double strMultiplier, double dexMultiplier) {
        this.name = name;
        this.dmgDie = dmgDie;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
    }

    //simple attack
    public Attack(String name, int dmgDie, double strMultiplier, double dexMultiplier,
                  double lvlMultiplier, double physDmgMultiplier) {
        this.name = name;
        this.dmgDie = dmgDie;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.lvlMultiplier = lvlMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
    }

    public Attack(String name, int dmgDie, int dmgRolls, int numOfAttacks, double strMultiplier, double dexMultiplier) {
        this.name = name;
        this.dmgDie = dmgDie;
        this.dmgRolls = dmgRolls;
        this.numOfAttacks = numOfAttacks;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
    }

    public Attack(String name, double strMultiplier, double dexMultiplier, double intMultiplier, double wisMultiplier,
                  double chaMultiplier, double lvlMultiplier, double physDmgMultiplier) {
        this.name = name;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.lvlMultiplier = lvlMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
    }

    //full constructor
    public Attack(String name, double strMultiplier, double dexMultiplier, double intMultiplier, double wisMultiplier,
                  double chaMultiplier, double lvlMultiplier, double physDmgMultiplier, double spellDmgMultiplier,
                  int hitPoint, int critPoint, int dmgDie, int dmgRolls, int numOfAttacks, int healDie,
                  double accMultiplier, int manaCost, double hpDrainMultiplier, double costMultiplier) {
        this.name = name;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.lvlMultiplier = lvlMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
        this.spellDmgMultiplier = spellDmgMultiplier;
        this.hitPoint = hitPoint;
        this.critPoint = critPoint;
        this.dmgDie = dmgDie;
        this.dmgRolls = dmgRolls;
        this.numOfAttacks = numOfAttacks;
        this.healDie = healDie;
        this.accMultiplier = accMultiplier;
        this.manaCost = manaCost;
        this.hpDrainMultiplier = hpDrainMultiplier;
        this.costMultiplier = costMultiplier;
    }

    //todo finish multiAttack
    public List<Damage> doAttack(myCharacter attacker) {
        System.out.println(attacker.getName() + " using " + this.getName());
        ArrayList<Damage> damages = new ArrayList<>();
        for (int i = 0; i < this.numOfAttacks; i++) {
            boolean crit = false;
            int cost = 0;
            if (i == 0) {
                cost = manaCost;
            }
            //check if attack costs mana
            if (cost > 0) {
                cost += (attacker.getLevel() * this.getLvlMultiplier());
                cost -= (attacker.getWisStat() * wisMultiplier);
            }
            //attack roll
            int acc = Dice.d20();
            //auto miss if rolled 1
            if (acc == 1) {
                damages.add(new Damage(this.getName(), false));
                continue;
            }
            // natural crit
            if (acc == 20) {
                crit = true;
            }
            //if attack has accuracy modifier, factor it in
            if (accMultiplier > 0) {
                acc += (int) (this.accMultiplier * attacker.getDexStat()) - (int) (attacker.getLevel() * this.lvlMultiplier);
                //missed if accuracy is not high enough
                if (acc < hitPoint + (int) (attacker.getLevel() * this.lvlMultiplier)) {
                    damages.add(new Damage(this.getName(), false));
                    continue;
                }
                //crit check if accuracy + dexBonus > critPoint + lvlBonus;
                if (acc + (attacker.getDexStat() * this.accMultiplier) > this.critPoint + (attacker.getLevel() * this.getLvlMultiplier())) {
                    crit = true;
                }
            }
            //damage rolls
            int physDmg = 0;
            if (physDmgMultiplier>0){
                physDmg += Dice.die(this.dmgDie, this.dmgRolls) +
                        (int) (attacker.getStrStat() * this.strMultiplier) +
                        (int) (attacker.getWeapon().getPhysDamage() * physDmgMultiplier);
            }
            int spellDmg = 0;
            if (spellDmgMultiplier >0){
                spellDmg += Dice.die(this.dmgDie, this.dmgRolls) +
                        (int) (attacker.getIntStat() * this.getIntMultiplier()) +
                        (int) (attacker.getWeapon().getSpellDmg() * spellDmgMultiplier);
            }
            if (crit) {
                physDmg *= 2;
                System.out.println("Critical Hit!");
            }
            damages.add(new Damage(this.name, physDmg, cost, crit));
        }
        return damages;
    }

    //basic attack
    public static Attack stab() {
        return new Attack("Stab", 6, 0.5, 0.5);
    }

    //todo test with spellcaster
    public static Attack bash() {
        return new Attack();
    }

    //dagger attack that relies on dexterity for damage and better crit change
    //todo test daggerSlice attack and find a better name, (maybe eviscerate?)
    public static Attack daggerSlice() {
        return new Attack("Dagger Slice", 6, 0.5, 1, 1, 1.5);
    }

    public static Attack daggerFlurry() {
        new Attack("Dagger Flurry", )
        return new Attack("Dagger Flurry", 0, 1.25, 1, 1,
                0, 2, 20, 4, 1, 4, 0);
    }

    public static Attack swordSlice() {
        return new Attack("Sword Slice", 8, 1, 0.5, 1, 1.5);
    }

    public static Attack smash() {
        return new Attack("Smash", 8, 1.25, 0, 1, 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLvlMultiplier(double lvlMultiplier) {
        this.lvlMultiplier = lvlMultiplier;
    }

    public double getLvlMultiplier() {
        return lvlMultiplier;
    }

    public void setCritPoint(int critPoint) {
        this.critPoint = critPoint;
    }

    public int getCritPoint() {
        return critPoint;
    }

    public int getDmgDie() {
        return dmgDie;
    }

    public void setDmgDie(int dmgDie) {
        this.dmgDie = dmgDie;
    }

    public int getDmgRolls() {
        return dmgRolls;
    }

    public void setDmgRolls(int dmgRolls) {
        this.dmgRolls = dmgRolls;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public double getStrMultiplier() {
        return strMultiplier;
    }

    public void setStrMultiplier(double strMultiplier) {
        this.strMultiplier = strMultiplier;
    }

    public double getDexMultiplier() {
        return dexMultiplier;
    }

    public void setDexMultiplier(double dexMultiplier) {
        this.dexMultiplier = dexMultiplier;
    }

    public double getPhysDmgMultiplier() {
        return physDmgMultiplier;
    }

    public void setPhysDmgMultiplier(double physDmgMultiplier) {
        this.physDmgMultiplier = physDmgMultiplier;
    }

    public double getSpellDmgMultiplier() {
        return spellDmgMultiplier;
    }

    public void setSpellDmgMultiplier(double spellDmgMultiplier) {
        this.spellDmgMultiplier = spellDmgMultiplier;
    }

    public int getNumOfAttacks() {
        return numOfAttacks;
    }

    public void setNumOfAttacks(int numOfAttacks) {
        this.numOfAttacks = numOfAttacks;
    }

    public double getAccMultiplier() {
        return accMultiplier;
    }

    public void setAccMultiplier(double accMultiplier) {
        this.accMultiplier = accMultiplier;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public double getIntMultiplier() {
        return intMultiplier;
    }

    public void setIntMultiplier(double intMultiplier) {
        this.intMultiplier = intMultiplier;
    }

    public double getWisMultiplier() {
        return wisMultiplier;
    }

    public void setWisMultiplier(double wisMultiplier) {
        this.wisMultiplier = wisMultiplier;
    }

    public double getChaMultiplier() {
        return chaMultiplier;
    }

    public void setChaMultiplier(double chaMultiplier) {
        this.chaMultiplier = chaMultiplier;
    }

    public int getHealDie() {
        return healDie;
    }

    public void setHealDie(int healDie) {
        this.healDie = healDie;
    }

    public double getHpDrainMultiplier() {
        return hpDrainMultiplier;
    }

    public void setHpDrainMultiplier(double hpDrainMultiplier) {
        this.hpDrainMultiplier = hpDrainMultiplier;
    }

    public double getCostMultiplier() {
        return costMultiplier;
    }

    public void setCostMultiplier(double costMultiplier) {
        this.costMultiplier = costMultiplier;
    }

    //    public String getDescription() {
//        return description;
//    }
}

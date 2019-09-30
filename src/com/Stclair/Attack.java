package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class Attack {

    private String name;
    private int physDmgDie = 6;
    private int spellDmgDie = 0;
    private double strMultiplier = 0;
    private double dexMultiplier = 0;
    private double intMultiplier = 0;
    private double wisMultiplier = 0;
    private double chaMultiplier = 0;
    private double lvlMultiplier = 1;
    private double physDmgMultiplier = 1;
    private double spellDmgMultiplier = 0;
    private int rolls = 1;
    private int numOfAttacks = 1;
    private int manaCost = 0;
    private double hpDrainMultiplier = 0;
    private String description = "No description available";

    //todo add cooldown for attacks
//    private int coolDown = 0;

    //todo find a way to apply buffs/debuffs to attacks
    //todo add description String for attacks?
    //private int lvlReq;

    public Attack() {
    }

    //simple attack
    public Attack(String name, int dmgDie, double strMultiplier, double dexMultiplier) {
        this.name = name;
        this.physDmgDie = dmgDie;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
    }

    public Attack(String name, int physDmgDie, double strMultiplier, double dexMultiplier, double lvlMultiplier, double physDmgMultiplier) {
        this.name = name;
        this.physDmgDie = physDmgDie;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.lvlMultiplier = lvlMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
    }

    public Attack(String name, int dmgDie, int rolls, int numOfAttacks, double strMultiplier, double dexMultiplier) {
        this.name = name;
        this.physDmgDie = dmgDie;
        this.rolls = rolls;
        this.numOfAttacks = numOfAttacks;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
    }

    //physical attack w/magic
    public Attack(String name, int physDmgDie, int spellDmgDie, int numOfAttacks, double strMultiplier, double dexMultiplier,
                  double intMultiplier, double physDmgMultiplier, double spellDmgMultiplier) {
        this.name = name;
        this.physDmgDie = physDmgDie;
        this.spellDmgDie = spellDmgDie;
        this.numOfAttacks = numOfAttacks;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.intMultiplier = intMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
        this.spellDmgMultiplier = spellDmgMultiplier;
    }

    // Physical attacks offer no accuracy bonus, just a straight d20 with 1 being miss, 20 being crit
    public List<ActionResult> action(myCharacter attacker, myCharacter target) {
        System.out.println(attacker.getName() + " using " + this.getName());
        ArrayList<ActionResult> actionResults = new ArrayList<>();
        for (int i = 0; i < this.numOfAttacks; i++) {
            int manaCost = 0;
            int heal = 0;

            if (i == 1 && this.manaCost > 0) {
                manaCost = getTotalManaCost(attacker);
            }
            //attack roll
            int acc = Dice.d20();
            //auto miss if rolled 1
            if (acc == 1) {
                actionResults.add(ActionResult.miss(this.name));
                continue;
            }

            boolean crit = critCheck(attacker, acc);

            //damage rolls
            int physDmg = 0;
            if (this.physDmgMultiplier > 0) {
                physDmg += Dice.die(this.physDmgDie, this.rolls);
                if (this.physDmgDie > 0) {
                    physDmg += (int) (attacker.getStrStat() * this.strMultiplier);
                }
                if (this.dexMultiplier > 0) {
                    physDmg += (int) (attacker.getDexStat() * this.strMultiplier);
                }
                physDmg *= physDmgMultiplier;
                physDmg -= target.getArmor().getArmorRating();
            }
            int spellDmg = 0;
            if (this.spellDmgMultiplier > 0) {
                spellDmg += Dice.die(this.physDmgDie, this.rolls);
                if (this.intMultiplier > 0){
                    spellDmg += (int) (attacker.getIntStat() * this.getIntMultiplier());
                }
                spellDmg *= spellDmgMultiplier;
                spellDmg -= target.getArmor().getMagicDefRating();
            }
            if (crit) {
                physDmg *= 2;
                spellDmg *=2;
                System.out.println("Critical Hit!");
            }
            actionResults.add(new ActionResult(this.name, physDmg, spellDmg, manaCost, heal, crit));
        }
        return actionResults;
    }

    boolean critCheck(myCharacter attacker, int roll) {
        return (roll == 20);
    }

    public int getTotalManaCost(myCharacter caster) {
        if (manaCost == 0) {
            return 0;
        }
        int cost = this.getManaCost()
                - (int) (caster.getWisStat() * wisMultiplier / 4)
                + (int) (caster.getLevel() * lvlMultiplier);
        if (cost < manaCost / 4) {
            System.out.println("Calculated mana cost below 25%");
            return manaCost / 4;
        }
        return cost;
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

    public int getPhysDmgDie() {
        return physDmgDie;
    }

    public void setPhysDmgDie(int physDmgDie) {
        this.physDmgDie = physDmgDie;
    }

    public int getRolls() {
        return rolls;
    }

    public void setRolls(int rolls) {
        this.rolls = rolls;
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

    public double getHpDrainMultiplier() {
        return hpDrainMultiplier;
    }

    public void setHpDrainMultiplier(double hpDrainMultiplier) {
        this.hpDrainMultiplier = hpDrainMultiplier;
    }

    public int getSpellDmgDie() {
        return spellDmgDie;
    }

    public void setSpellDmgDie(int spellDmgDie) {
        this.spellDmgDie = spellDmgDie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

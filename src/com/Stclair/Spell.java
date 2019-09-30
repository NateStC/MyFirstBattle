package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Attack {

    double spellDmgMultiplier = 1;
    double physDmgMultiplier = 0;
    double strMultiplier = 0;
    double dexMultiplier = 0;

    public Spell() {
    }

    //basic attack spell constructor
    public Spell(String name, int manaCost, int dmgDie, double intMultiplier) {
        this.setName(name);
        this.setManaCost(manaCost);
        this.setPhysDmgDie(dmgDie);
        setIntMultiplier(intMultiplier);
    }

    public Spell(String name, int manaCost, int dmgDie, double intMultiplier, double wisMultiplier, double lvlMultiplier) {
        this.setName(name);
        setManaCost(manaCost);
        this.setSpellDmgDie(dmgDie);
        setIntMultiplier(intMultiplier);
        setWisMultiplier(wisMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
    }

    //advanced attack spell constructor
    public Spell(String name, int manaCost, int dmgDie, int dmgRolls, int numOfAttacks, double intMultiplier, double wisMultiplier,
                 double chaMultiplier, double lvlMultiplier, double spellDmgMultiplier) {
        this.setName(name);
        this.setSpellDmgDie(dmgDie);
        this.setRolls(dmgRolls);
        this.setNumOfAttacks(numOfAttacks);
        this.setLvlMultiplier(lvlMultiplier);
        setIntMultiplier(intMultiplier);
        setWisMultiplier(wisMultiplier);
        setChaMultiplier(chaMultiplier);
        setManaCost(manaCost);
        this.setSpellDmgMultiplier(spellDmgMultiplier);
        this.setPhysDmgMultiplier(0);
    }

    //todo make a class of type buff or heal to change this method to override doAttack
    //healing spell or spells that only affect caster


    public List<ActionResult> cast(myCharacter caster, myCharacter target) {
        return action(caster, target);
    }

    @Override
    public List<ActionResult> action(myCharacter caster, myCharacter target) {
        ArrayList<ActionResult> actionResults = new ArrayList<>();
        int manaCost = this.getTotalManaCost(caster);
        for (int i = 0; i < this.getNumOfAttacks(); i++) {
            if (i > 0) {
                //Only costs mana once if there are multiple attacks
                manaCost = 0;
            } else if (manaCost > caster.getMana()) {
                // returns Out of Mana if insufficient mana to cast
                actionResults.add(ActionResult.oom(this.getName()));
                return actionResults;
            }
            int heal = 0;
            int acc = Dice.d20();
//            System.out.println("Accuracy roll is " + acc);
            if (acc == 1) {  //critical fail
                actionResults.add(ActionResult.miss(this.getName(), manaCost));
                continue;
            }
//            System.out.println(this.getName() + " accuracy is " + acc);
            boolean crit = (acc == 20);

            //dmg is intelligence and dice
            int spellDmg = Dice.die(this.getSpellDmgDie(), this.getRolls()) +
                    (int) (caster.getIntStat() * this.getIntMultiplier() / 3) +
                    (int) (caster.getWeapon().getSpellDmg() * this.getSpellDmgMultiplier());
            if (getPhysDmgDie() > 0) {

            }
            if (crit) {
                spellDmg *= 2;
            }
            //factors in hpDrain if applicable
            if (getHpDrainMultiplier() != 0) {
                heal += (int) (spellDmg * getHpDrainMultiplier());
            }
            actionResults.add(new ActionResult(this.getName(), 0, spellDmg, manaCost, heal, crit));
            System.out.println(caster.getName() + "'s " + this.getName() + " deals " + spellDmg + " dmg");
        }

        return actionResults;
    }

    public boolean critCheck(myCharacter caster, int roll) {   //wisdom lowers critical hit min
        //todo test spell hit/miss/crit balance
        if (roll == 20) {
            return true;
        }
        double rollMod = roll - (caster.getWisStat() * this.getWisMultiplier() /3);
        double critPoint = 20 + caster.getLevel()*5;
        System.out.printf("%s accuracy Roll = %d\n" +
                "Accuracy modified = %d\n" +
                "CritPoint = %d",
                this.getName(), roll,(int) rollMod, (int)critPoint);

        return rollMod>critPoint;
    }

    @Override
    public double getSpellDmgMultiplier() {
        return spellDmgMultiplier;
    }

    @Override
    public void setSpellDmgMultiplier(double spellDmgMultiplier) {
        this.spellDmgMultiplier = spellDmgMultiplier;
    }

    @Override
    public double getPhysDmgMultiplier() {
        return physDmgMultiplier;
    }

    @Override
    public void setPhysDmgMultiplier(double physDmgMultiplier) {
        this.physDmgMultiplier = physDmgMultiplier;
    }

    @Override
    public double getStrMultiplier() {
        return strMultiplier;
    }

    @Override
    public void setStrMultiplier(double strMultiplier) {
        this.strMultiplier = strMultiplier;
    }

    @Override
    public double getDexMultiplier() {
        return dexMultiplier;
    }

    @Override
    public void setDexMultiplier(double dexMultiplier) {
        this.dexMultiplier = dexMultiplier;
    }
}
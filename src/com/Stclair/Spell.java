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
    public Spell (String name, int manaCost, int dmgDie, double intMultiplier){
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

    //basic healing constructor
    public Spell(String name, int manaCost, int healDie, double intMultiplier, double wisMultiplier, double chaMultiplier, double lvlMultiplier) {
        this.setName(name);
        setManaCost(manaCost);
        this.setPhysDmgDie(0);
        this.setSpellDmgDie(0);
        setHealDie(healDie);
        setIntMultiplier(intMultiplier);
        setWisMultiplier(wisMultiplier);
        setChaMultiplier(chaMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
        this.setPhysDmgMultiplier(0);
    }

    //advanced attack spell constructor
    public Spell(String name, int manaCost, int dmgDie, int dmgRolls, int numOfAttacks, double intMultiplier, double wisMultiplier,
                 double chaMultiplier, double lvlMultiplier, double spellDmgMultiplier, double accMultiplier, double costMultiplier) {
        this.setName(name);
        this.setSpellDmgDie(dmgDie);
        this.setDmgRolls(dmgRolls);
        this.setNumOfAttacks(numOfAttacks);
        this.setLvlMultiplier(lvlMultiplier);
        setIntMultiplier(intMultiplier);
        setWisMultiplier(wisMultiplier);
        setChaMultiplier(chaMultiplier);
        setManaCost(manaCost);
        this.setSpellDmgMultiplier(spellDmgMultiplier);
        setCostMultiplier(costMultiplier);
        this.setPhysDmgMultiplier(0);
    }

    @Override
    public List<Damage> doAttack(myCharacter caster, myCharacter target) {
        ArrayList<Damage> damages = new ArrayList<>();
        int manaCost = this.getTotalManaCost(caster);
        if (manaCost > caster.getMana()) {
            // returns Out of Mana if insuficient mana to cast
            damages.add(new Damage(this.getName()));
            return damages;
        }
        for (int i = 0; i < this.getNumOfAttacks(); i++) {
            if (i > 0) {
                //Only costs mana once if there are multiple attacks
                manaCost = 0;
            }
            int heal = 0;
            int acc = Dice.d20();
//            System.out.println("Accuracy roll is " + acc);
            //critical fail
            if (acc == 1) {
                damages.add(new Damage(this.getName(), false, manaCost));
                continue;
            }
//            //wisdom lowers critical hit min
//            System.out.println(this.getName() + " accuracy is " + acc);
            boolean crit = (acc == 20);

            //todo make wisdom & lvl affect crit chance
//          if (!crit) crit = (acc > ((20 + caster.getLevel() * 5) - (int) (caster.getWisStat() / 3 * this.getWisMultiplier())));


            if (isHealingSpell()) {
                //calls healing spell because healing spell can't miss
                damages.add(healingSpell(caster, manaCost, crit));
                continue;
            }

            //dmg is intelligence and dice
            int spellDmg = Dice.die(this.getSpellDmgDie(), this.getDmgRolls()) +
                    (int) (caster.getIntStat() * this.getIntMultiplier() / 3) +
                    (int) (caster.getWeapon().getSpellDmg() * this.getSpellDmgMultiplier());
            if (getPhysDmgDie()>0){

            }
            if (crit) {
                spellDmg *= 2;
            }
            //factors in hpDrain if applicable
            if (getHpDrainMultiplier() != 0) {
                heal += (int) (spellDmg * getHpDrainMultiplier());
            }
            damages.add(new Damage(this.getName(), 0, spellDmg, manaCost, heal, crit));
            System.out.println(caster.getName() + "'s " + this.getName() + " deals " + spellDmg + " dmg");
        }

        return damages;
    }

    @Override
    public int getTotalManaCost(myCharacter caster) {
        return this.getManaCost() -
                (int) (caster.getWisStat() * this.getWisMultiplier() / 4) +
                (int) (caster.getLevel() * this.getCostMultiplier());
    }

    private Damage healingSpell(myCharacter caster, int manaCost, boolean crit) {
        int heal = Dice.die(getHealDie()) +
                (int) (caster.getIntStat() * getIntMultiplier()) +
                (int) (caster.getWisStat() * getWisMultiplier()) +
                (int) (caster.getChaStat() * getChaMultiplier());
        if (crit) {
            heal *= 2;
        }
        return new Damage(this.getName(), heal, manaCost, crit);
    }

    //basic attack spell
    public static Spell fireball() {
        return new Spell("Fireball", 15, 12, .75, 1, 1);
    }

    // Electrocution spell that relies more on player stats than dice
    //todo test staticShock
    public static Spell staticShock() {
        return new Spell("Static Shock", 15, 6, 1.5, 1.5, 1.5);
    }

    //basic healing spell
    public static Spell healingHands() {
        return new Spell("Healing Hands", 15, 12, 0, .5, 1.5, 2);
    }

    public static Spell magicMissile() {
        return new Spell("Magic Missile", 25, 6, 1, 3, .5,
                .5, .5, 1, 1, 1, 2);
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
package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Attack {

    public Spell() {
    }

    //basic attack spell constructor
    public Spell(String name, int manaCost, int dmgDie, double intMultiplier, double wisMultiplier, double lvlMultiplier) {
        this.setName(name);
        setManaCost(manaCost);
        this.setDmgDie(dmgDie);
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.setLvlMultiplier(lvlMultiplier);
        this.setChaMultiplier(chaMultiplier);
        this.setPhysDmgMultiplier(0);

    }

    //basic healing constructor
    public Spell(String name, int manaCost, int healDie, double intMultiplier, double wisMultiplier, double chaMultiplier, double lvlMultiplier) {
        this.setName(name);
        setManaCost(manaCost);
        this.setDmgDie(0);
        this.healDie = healDie;
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.setLvlMultiplier(lvlMultiplier);
        this.setPhysDmgMultiplier(0);
    }

    //advanced attack spell constructor
    public Spell(String name, int manaCost, int dmgDie, int dmgRolls, int numOfAttacks, double intMultiplier, double wisMultiplier,
                 double chaMultiplier, double lvlMultiplier, double spellDmgMultiplier, double accMultiplier, double costMultiplier) {
        this.setName(name);
        this.setDmgDie(dmgDie);
        this.setDmgRolls(dmgRolls);
        this.setNumOfAttacks(numOfAttacks);
        this.setLvlMultiplier(lvlMultiplier);
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        setManaCost(manaCost);
        this.setSpellDmgMultiplier(spellDmgMultiplier);
        this.setAccMultiplier(accMultiplier);
        this.costMultiplier = costMultiplier;
        this.setPhysDmgMultiplier(0);
    }

    public Spell(String name, int manaCost, int dmgDie, double intMultiplier, double wisMultiplier, double chaMultiplier,
                 double lvlMultiplier, double spellDmgMultiplier, double hpDrainMultiplier, double costMultiplier) {
        this.setName(name);
        this.setNumOfAttacks(1);
        setManaCost(manaCost);
        this.setDmgDie(dmgDie);
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.setLvlMultiplier(lvlMultiplier);
        this.setSpellDmgMultiplier(spellDmgMultiplier);
        this.hpDrainMultiplier = hpDrainMultiplier;
        this.costMultiplier = costMultiplier;
        this.setPhysDmgMultiplier(0);


    }

    //full constructor
    public Spell(String name, double strMultiplier, double dexMultiplier, double lvlMultiplier, double physDmgMultiplier,
                 double spellDmgMultiplier, int hitPoint, int critPoint, int dmgDie, int dmgRolls, int numOfAttacks,
                 int dexAccMultiplier, int manaCost, int healDie, double intMultiplier, double wisMultiplier,
                 double chaMultiplier, double hpDrainmultiplier) {
        super(name, strMultiplier, dexMultiplier, lvlMultiplier, physDmgMultiplier, spellDmgMultiplier, hitPoint,
                critPoint, dmgDie, dmgRolls, numOfAttacks, dexAccMultiplier);
        setManaCost(manaCost);
        this.healDie = healDie;
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.hpDrainMultiplier = hpDrainmultiplier;
    }

    public Spell(String name, double strMultiplier, double dexMultiplier, double lvlMultiplier,
                 double physDmgMultiplier, double spellDmgMultiplier, int hitPoint, int critPoint,
                 int dmgDie, int dmgRolls, int numOfAttacks, double accMultiplier, int manaCost,
                 int healDie, double intMultiplier, double wisMultiplier, double chaMultiplier,
                 double hpDrainMultiplier, double costMultiplier) {
        super(name, strMultiplier, dexMultiplier, lvlMultiplier, physDmgMultiplier, spellDmgMultiplier, hitPoint, critPoint, dmgDie, dmgRolls, numOfAttacks, accMultiplier);
        setManaCost(manaCost);
        this.healDie = healDie;
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.hpDrainMultiplier = hpDrainMultiplier;
        this.costMultiplier = costMultiplier;
        this.setPhysDmgMultiplier(0);
    }

    @Override
    public List<Damage> doAttack(myCharacter attacker) {
        System.out.println(attacker.getName() + " using " + this.getName());
        return cast(attacker);
    }

    public List<Damage> cast(myCharacter caster) {
        ArrayList<Damage> damages = new ArrayList<>();
        int manaCost = this.getManaCost() - (int) (caster.getWisStat() * this.getWisMultiplier() / 4) + (int) (caster.getLevel() * this.getCostMultiplier());
        if (manaCost > caster.getMana()) {
            damages.add(new Damage(this.getName(), 0));
            return damages;
        }
        for (int i = 0; i < this.getNumOfAttacks(); i++) {
            if (i > 0) {
                manaCost = 0;
            }
            int heal = 0;
            int acc = Dice.d20();
            System.out.println("Accuracy roll is " + acc);
            //critical fail
            if (acc == 1) {
                damages.add(new Damage(this.getName(), false, manaCost));
                continue;
            }
            if (this.getAccMultiplier() > 0) {
                //intelligence raises accuracy
                acc += (this.getAccMultiplier() * (caster.getIntStat() / 4)) + caster.getWeapon().getAccuracy();
            }
            //wisdom lowers critical hit min
            System.out.println(this.getName() + " accuracy is " + acc);
            boolean crit = (acc > ((20 + caster.getLevel() * 5) - (int) (caster.getWisStat() / 3 * this.getWisMultiplier())));
            //calls healing spell because healing spell can't miss
            if (getHealDie() > 0 && getDmgDie() == 0) {
                damages.add(healingSpell(caster, manaCost, crit));
                continue;
            }
            //returns a miss if does not meet hit minimum and is not a non-dmg spell (healing)
            if (acc <= getHitPoint() + caster.getLevel() && getDmgDie() > 0) {
                damages.add(new Damage(this.getName(), false, manaCost));
                continue;
            }
            //dmg is intelligence and dice
            int damage = Dice.die(getDmgDie(), getDmgRolls()) +
                    (int) (caster.getIntStat() * this.getIntMultiplier() / 3) +
                    (int) (caster.getWeapon().getSpellDmg() * this.getSpellDmgMultiplier());
            if (crit) {
                damage *= 2;
            }
            //factors in hpDrain if applicable
            if (getHpDrainMultiplier() != 0) {
                heal += (int) (damage * getHpDrainMultiplier());
            }
            damages.add(new Damage(this.getName(), 0, damage, heal, manaCost, crit));
        }

        return damages;
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

    //lvlReq 3?
    //todo test drainLife spell
    public static Spell drainLife() {
        return new Spell("Drain Life", 15, 12, 1.5, 1, 1,
                1, 1, .75, 1);
    }

    public static Spell magicMissile() {
        return new Spell("Magic Missile", 25, 6, 1, 3, .5,
                .5, 0, 1, 1, 1, 2);
    }
}
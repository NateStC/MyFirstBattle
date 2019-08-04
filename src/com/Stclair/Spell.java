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
        this.setPhysDmgDie(dmgDie);
        setIntMultiplier(intMultiplier);
        setWisMultiplier(wisMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
    }

    //basic healing constructor
    public Spell(String name, int manaCost, int healDie, double intMultiplier, double wisMultiplier, double chaMultiplier, double lvlMultiplier) {
        this.setName(name);
        setManaCost(manaCost);
        this.setPhysDmgDie(0);
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
        this.setPhysDmgDie(dmgDie);
        this.setDmgRolls(dmgRolls);
        this.setNumOfAttacks(numOfAttacks);
        this.setLvlMultiplier(lvlMultiplier);
        setIntMultiplier(intMultiplier);
        setWisMultiplier(wisMultiplier);
        setChaMultiplier(chaMultiplier);
        setManaCost(manaCost);
        this.setSpellDmgMultiplier(spellDmgMultiplier);
        this.setAccMultiplier(accMultiplier);
        setCostMultiplier(costMultiplier);
        this.setPhysDmgMultiplier(0);
    }

    public Spell(String name, int manaCost, int dmgDie, double intMultiplier, double wisMultiplier, double chaMultiplier,
                 double lvlMultiplier, double spellDmgMultiplier, double hpDrainMultiplier, double costMultiplier) {
        this.setName(name);
        this.setNumOfAttacks(1);
        setManaCost(manaCost);
        this.setPhysDmgDie(dmgDie);
        setIntMultiplier(intMultiplier);
        setWisMultiplier(wisMultiplier);
        setChaMultiplier(chaMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
        this.setSpellDmgMultiplier(spellDmgMultiplier);
        setHpDrainMultiplier(hpDrainMultiplier);
        setCostMultiplier(costMultiplier);
    }

    //full constructor
    public Spell(String name, double strMultiplier, double dexMultiplier, double intMultiplier, double wisMultiplier,
                 double chaMultiplier, double lvlMultiplier, double physDmgMultiplier, double spellDmgMultiplier,
                 int hitPoint, int critPoint, int dmgDie, int dmgRolls, int numOfAttacks, int healDie,
                 double accMultiplier, int manaCost, double hpDrainMultiplier, double costMultiplier,
                 double spellDmgMultiplier1, double wisMultiplier1, double physDmgMultiplier1, double strMultiplier1,
                 double dexMultiplier1, double chaMultiplier1) {
        super(name, strMultiplier, dexMultiplier, intMultiplier, wisMultiplier, chaMultiplier, lvlMultiplier,
                physDmgMultiplier, spellDmgMultiplier, hitPoint, critPoint, dmgDie, dmgRolls, numOfAttacks, healDie,
                accMultiplier, manaCost, hpDrainMultiplier, costMultiplier);
        this.spellDmgMultiplier = spellDmgMultiplier1;
        setWisMultiplier(wisMultiplier);
        this.physDmgMultiplier = physDmgMultiplier1;
        this.strMultiplier = strMultiplier1;
        this.dexMultiplier = dexMultiplier1;
        setChaMultiplier(chaMultiplier);
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
            damages.add(new Damage(this.getName()));
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
            if (getHealDie() > 0 && getPhysDmgDie() == 0) {
                damages.add(healingSpell(caster, manaCost, crit));
                continue;
            }
            //returns a miss if does not meet hit minimum and is not a non-dmg spell (healing)
            if (acc <= getHitPoint() + caster.getLevel() && getPhysDmgDie() > 0) {
                damages.add(new Damage(this.getName(), false, manaCost));
                continue;
            }
            //dmg is intelligence and dice
            int damage = Dice.die(getPhysDmgDie(), getDmgRolls()) +
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
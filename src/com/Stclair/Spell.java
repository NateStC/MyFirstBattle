package com.Stclair;

public class Spell extends Attack {

    private int manaCost;
    private int healDie = 0;
    private double intMultiplier;
    private double wisMultiplier;
    private double chaMultiplier = 0;
    private double hpDrainMultiplier = 0;

    public Spell() {
        super();
        manaCost = 10;
        intMultiplier = 1;
        wisMultiplier = 1;
    }

    //basic attack spell constructor
    public Spell(String name, int manaCost, int dmgDie, double intMultiplier, double wisMultiplier, double lvlMultiplier) {
        this.setName(name);
        this.manaCost = manaCost;
        this.setDmgDie(dmgDie);
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.setLvlMultiplier(lvlMultiplier);
        this.setChaMultiplier(chaMultiplier);

    }

    //basic healing constructor
    public Spell (String name, int manaCost, int healDie, double intMultiplier, double wisMultiplier, double chaMultiplier, double lvlMultiplier){
        this.setName(name);
        this.manaCost = manaCost;
        this.setDmgDie(0);
        this.healDie = healDie;
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.setLvlMultiplier(lvlMultiplier);
    }

    //advanced attack spell constructor
    public Spell(String name, int manaCost, int dmgDie, double intMultiplier, double wisMultiplier,
                 double chaMultiplier, double lvlMultiplier, double accMultiplier) {
        this.setName(name);
        this.setDmgDie(dmgDie);
        this.setLvlMultiplier(lvlMultiplier);
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.manaCost = manaCost;
        this.setAccMultiplier(accMultiplier);
    }

    //full constructor
    public Spell(String name, double strMultiplier, double dexMultiplier, double lvlMultiplier, double physDmgMultiplier,
                 double spellDmgMultiplier, int hitPoint, int critPoint, int dmgDie, int dmgRolls, int numOfAttacks,
                 int dexAccMultiplier, int manaCost, int healDie, double intMultiplier, double wisMultiplier,
                 double chaMultiplier, double hpDrainmultiplier) {
        super(name, strMultiplier, dexMultiplier, lvlMultiplier, physDmgMultiplier, spellDmgMultiplier, hitPoint,
                critPoint, dmgDie, dmgRolls, numOfAttacks, dexAccMultiplier);
        this.manaCost = manaCost;
        this.healDie = healDie;
        this.intMultiplier = intMultiplier;
        this.wisMultiplier = wisMultiplier;
        this.chaMultiplier = chaMultiplier;
        this.hpDrainMultiplier = hpDrainmultiplier;
    }

    @Override
    public Damage doAttack(myCharacter attacker) {
        System.out.println(attacker.getName() + " using " + this.getName());
        return cast(attacker);
    }

    public Damage cast(myCharacter caster) {
        int manaCost = this.manaCost + (int) (caster.getWisStat() * wisMultiplier) + (int) (caster.getLevel() * this.getLvlMultiplier());
        if (manaCost > caster.getMana()) {
            return new Damage(this.getName(), 0);
        }
        int heal = 0;
        int acc = Dice.d20();
        //critical fail
        if (acc == 1) {
            return new Damage(this.getName(), false, manaCost);
        }
        if (this.getAccMultiplier() > 0) {
            //intelligence raises accuracy
            acc += (this.getAccMultiplier() * caster.getIntStat()) + caster.getWeapon().getAccuracy();
        }
        //wisdom lowers critical hit min
        boolean crit = (acc > ((20 + caster.getLevel() * 5) - (int) (caster.getWisStat() * wisMultiplier)));
        //calls healing spell because healing spell can't miss
        if (getHealDie() > 0 && getDmgDie() == 0) {
            return healingSpell(caster, manaCost, crit);
        }
        //returns a miss if does not meet hit minimum and is not a non-dmg spell (healing)
        if (acc <= getHitPoint() + caster.getLevel() && getDmgDie() > 0) {
            return new Damage(this.getName(), false, manaCost);
        }
        //dmg is intelligence and dice
        int damage = Dice.die(getDmgDie(), getDmgRolls()) +
                (int) (caster.getIntStat() * this.getIntMultiplier())+
                caster.getWeapon().getSpellDmg();
        if (crit) {
            damage *= 2;
        }
        //factors in hpDrain if applicable
        if (getHpDrainMultiplier() != 0) {
            heal += (int) (damage * getHpDrainMultiplier());
        }
        return new Damage(this.getName(), damage, heal, manaCost, crit);
    }

    private Damage healingSpell(myCharacter caster, int manaCost, boolean crit) {

        int heal = Dice.die(getHealDie()) +
                (int) (caster.getIntStat() * getIntMultiplier()) +
                (int) (caster.getWisStat() * getWisMultiplier()) +
                (int) (caster.getChaStat() * getChaMultiplier());
        return new Damage(this.getName(), heal, manaCost, crit);
    }

    public boolean manaCheck(myCharacter caster) {
        int cost = this.manaCost + (int) (caster.getWisStat() * wisMultiplier) + (int) (caster.getLevel() * this.getLvlMultiplier());
        return (caster.getMana() >= cost);
    }

    //basic attack spell
    public static Spell fireball() {
        return new Spell("Fireball", 10, 12, .5, 1, 1);
    }

    // Electrocution spell that relies more on player stats than dice
    //todo test staticShock
    public static Spell staticShock() {
        return new Spell("Static Shock", 12, 6, 2, 1, 1);
    }

    //basic healing spell
    public static Spell healingHands() {
        return new Spell("Healing Hands", 10, 12, 0, .5,1,2);
    }

    //lvlReq 3?
    //todo test drainLife spell
    public static Spell drainLife() {
        return new Spell ("Drain Life", 15,12,1.5,1,2);
    }

    public static int getFireArrowCost(myCharacter caster) {
        return 12 + (caster.getLevel() * 2) - ((caster.getWisStat() + caster.getDexStat()) / 4);
    }

    public int getManaCost() {
        return manaCost;
    }

    public int getHealDie() {
        return healDie;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setHealDie(int healDie) {
        this.healDie = healDie;
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
}
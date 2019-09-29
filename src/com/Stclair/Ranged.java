package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class Ranged extends Attack {
    //todo add ammo type?
    private double strMultiplier = 0;
    private double dexMultiplier = 1;
    private double accMultiplier = .5; //Determines bonus added to accuracy roll; multiply dexterity + weaponAccuracy times accMultiplier


    //basic ranged attack constructor;
    public Ranged(String name, int dmgDie, double dexMultiplier, double lvlMultiplier) {
        setName(name);
        setPhysDmgDie(dmgDie);
        setDexMultiplier(dexMultiplier);
        setLvlMultiplier(lvlMultiplier);
    }


    public Ranged(String name, double dexMultiplier, double lvlMultiplier,
                  double physDmgMultiplier, int dmgDie) {
        this.setName(name);
        this.setDexMultiplier(dexMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
        this.setPhysDmgMultiplier(physDmgMultiplier);
        this.setSpellDmgMultiplier(0);
        this.setPhysDmgDie(dmgDie);
        this.setDmgRolls(1);
        this.setStrMultiplier(0);
        this.setIntMultiplier(0);
        this.setWisMultiplier(0);
        this.setChaMultiplier(0);
        this.setNumOfAttacks(1);
        this.setHpDrainMultiplier(0);
        this.setHealDie(0);
        this.setManaCost(0);
    }

    //ranged attack w/ strBonus
    public Ranged(String name, double dexMultiplier, double lvlMultiplier,
                  double physDmgMultiplier, int dmgDie, int dmgRolls, double strMultiplier) {
        this.setName(name);
        this.setDexMultiplier(dexMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
        this.setPhysDmgMultiplier(physDmgMultiplier);
        this.setSpellDmgMultiplier(0);
        this.setPhysDmgDie(dmgDie);
        this.setDmgRolls(dmgRolls);
        this.setStrMultiplier(strMultiplier);
        this.setIntMultiplier(0);
        this.setWisMultiplier(0);
        this.setChaMultiplier(0);

    }


    //spell ranged attack constructor
    public Ranged(String name, double dexMultiplier, double lvlMultiplier,
                  double physDmgMultiplier, double spellDmgMultiplier, int physDmgDie, int spellDmgDie, double intMultiplier,
                  double wisMultiplier, int manaCost, double hpDrainMultiplier) {
        this.setName(name);
        this.setDexMultiplier(dexMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
        this.setPhysDmgMultiplier(physDmgMultiplier);
        this.setSpellDmgMultiplier(spellDmgMultiplier);
        this.setPhysDmgDie(physDmgDie);
        this.setSpellDmgDie(spellDmgDie);
        this.setStrMultiplier(0);
        this.setIntMultiplier(intMultiplier);
        this.setWisMultiplier(wisMultiplier);
        this.setManaCost(manaCost);
        this.setHpDrainMultiplier(hpDrainMultiplier);
    }


    @Override
    public List<Damage> doAttack(myCharacter attacker, myCharacter target) {
        ArrayList<Damage> damages = new ArrayList<>();
        System.out.println(attacker.getName() + " using " + this.getName());
        for (int i = 0; i < this.getNumOfAttacks(); i++) {
            int cost = this.getManaCost();
            //TODO figure how to factor wisdom & lvl into manacost
//            if (i == 0) { // only costs mana for first cast if more than one attack
//                if (cost > 0) { // calculates manaCost
//                    cost += (attacker.getLevel() * this.getLvlMultiplier());
//                    cost -= (attacker.getWisStat() * this.getWisMultiplier());
//                }
//            }
            int acc = Dice.d20();
            boolean crit = false;
            switch (acc) {
                case 2:            //critical fail
                    //auto miss
                    damages.add(new Damage(this.getName(), false));
                    continue;
                case 20:
                    crit = true;
            }
            //todo work out adding hitPoint and critPoint to ranged attack
            int hitPoint = 2 + attacker.getLevel();
            int critPoint = (int) (attacker.getLevel() * this.getLvlMultiplier()) + 19;
            //accuracy is increased by dexterity and weapon accuracy
            acc += this.accMultiplier * (attacker.getDexStat() + attacker.getWeapon().getAccuracy());
            System.out.printf("Accuracy is %d and critPoint is %d\n", acc, critPoint);
            crit = (acc>critPoint);
            //returns missed attack
//            if (acc < hitPoint + (attacker.getLevel() * this.getLvlMultiplier())) {
//                damages.add(new Damage(this.getName(), false, cost));
//                continue;
//            }

            int physDmg = 0;
            if (this.getPhysDmgMultiplier() > 0) {
                physDmg += Dice.die(this.getPhysDmgDie(), this.getDmgRolls()) +
                        (int) (attacker.getStrStat() * this.getStrMultiplier()) +
                        (int) (attacker.getDexStat() * this.getDexMultiplier()) +
                        (int) (attacker.getWeapon().getPhysDamage() * this.getPhysDmgMultiplier());
            }

            int spellDmg = 0;
            if (this.getSpellDmgMultiplier() > 0) {
                spellDmg += Dice.die(this.getSpellDmgDie(), this.getDmgRolls()) +
                        (int) (attacker.getIntStat() * this.getIntMultiplier()) +
                        (int) (attacker.getWeapon().getSpellDmg() * this.getSpellDmgMultiplier());
            }

            if (crit) {
                physDmg *= 2;
                spellDmg *= 2;
            }

            int drain = (int) (physDmg * this.getHpDrainMultiplier());

            damages.add(new Damage(this.getName(), physDmg, spellDmg, drain, cost, crit));
        }
        return damages;
    }

    public static Ranged arrowStrike() {
        return new Ranged("Arrow Strike", 8, 1, 1);
    }

    //aim for headshot, higher crit chance, higher crit damage?, higher miss chance;
    //todo test for balance
    public static Ranged headShot() {
        return new Ranged("HeadShot", 2, 2, 2, 12);
    }

    public static Ranged spearThrow() {
        return new Ranged("Spear Throw", .5, 6, 2, 1);
    }

    public static Ranged fireArrow() {
        return new Ranged("Fire Arrow", .5, 2, .75,
                .75, 6, 6, .5, .5, 10,
                0);
    }

    //test for balance
    public static Ranged knifeThrow() {
        return new Ranged("Knife Throw", 1, 1, 1, 6);
    }

    public static Ranged drainArrow(){
        return new Ranged("Draining Arrow", .5,2,.5,
                .5,4,4,.5,.5,10,
                .75);
    }
}

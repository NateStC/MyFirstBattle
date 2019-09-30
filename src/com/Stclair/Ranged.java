package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class Ranged extends Attack {
    //todo add ammo type?
    private double strMultiplier = 0;
    private double dexMultiplier = 1;
    private double accMultiplier = .5; //Determines bonus added to accuracy roll; multiply dexterity + weaponAccuracy times accMultiplier
    private int hitPoint = 2;
    private int critPoint = 25;


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
        this.setRolls(1);
        this.setNumOfAttacks(1);
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
        this.setRolls(dmgRolls);
        this.setStrMultiplier(strMultiplier);

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
    public List<ActionResult> action(myCharacter attacker, myCharacter target) {
        ArrayList<ActionResult> actionResults = new ArrayList<>();
        System.out.println(attacker.getName() + " using " + this.getName());
        for (int i = 0; i < this.getNumOfAttacks(); i++) {
            int cost = 0;
            if (i > 0) cost = this.getManaCost();
            //TODO figure how to factor wisdom & lvl into manacost
//            if (i == 0) { // only costs mana for first cast if more than one attack
//                if (cost > 0) { // calculates manaCost
//                    cost += (attacker.getLevel() * this.getLvlMultiplier());
//                    cost -= (attacker.getWisStat() * this.getWisMultiplier());
//                }
//            }
            int acc = Dice.d20();
            System.out.println("Accuracy roll is " + acc);
            boolean crit = (acc == 20);
            if (acc <= 2) {
                //auto-fail
                actionResults.add(ActionResult.miss(this.getName(), cost));
                continue;
            }
            //todo work out adding hitPoint and critPoint to ranged attack
            if (!crit) {
                switch (hitCheck(attacker, target, acc)) {
                    case MISS:
                        actionResults.add(ActionResult.miss(this.getName(), cost));
                        continue;
                    case CRIT:
                        crit = true;
                        break;
                    case HIT:
                        break;
                }
            }

            int physDmg = 0;
            if (this.getPhysDmgMultiplier() > 0) {
                physDmg += Dice.die(this.getPhysDmgDie(), this.getRolls()) +
                        (int) (attacker.getStrStat() * this.getStrMultiplier()) +
                        (int) (attacker.getDexStat() * this.getDexMultiplier()) +
                        (int) (attacker.getWeapon().getPhysDamage() * this.getPhysDmgMultiplier());
            }

            int spellDmg = 0;
            if (this.getSpellDmgMultiplier() > 0) {
                spellDmg += Dice.die(this.getSpellDmgDie(), this.getRolls()) +
                        (int) (attacker.getIntStat() * this.getIntMultiplier()) +
                        (int) (attacker.getWeapon().getSpellDmg() * this.getSpellDmgMultiplier());
            }

            if (crit) {
                physDmg *= 2;
                spellDmg *= 2;
            }

            int drain = (int) (physDmg * this.getHpDrainMultiplier());

            actionResults.add(new ActionResult(this.getName(), physDmg, spellDmg, drain, cost, crit));
        }
        return actionResults;
    }

    private int accuracyModifier(myCharacter attacker, myCharacter target) {
        return (int)
                (this.accMultiplier * (attacker.getDexStat() + attacker.getWeapon().getAccuracy())
                        - target.getDexStat() / 3
                );
    }

    private ActionResult.hmc hitCheck(myCharacter attacker, myCharacter target, int roll) {
        //accuracy is increased by dexterity and weapon accuracy
        roll += accuracyModifier(attacker, target);

        int newHitPoint = hitPoint + (int) ((target.getLevel() * this.getLvlMultiplier()) +
                target.getDexStat() * this.dexMultiplier);

        int newCritPoint = critPoint + (int) (attacker.getLevel() * this.getLvlMultiplier());

        System.out.printf("Accuracy is %d --- hitPoint is %d and critPoint is %d\n", roll, newHitPoint, newCritPoint);

        if (roll > newCritPoint) {
            return ActionResult.hmc.CRIT;
        }
        if (roll < newHitPoint) {
            return ActionResult.hmc.MISS;
        }
        return ActionResult.hmc.HIT;
    }

    public static Ranged arrowStrike() {
        return new Ranged("Arrow Strike", 8, 1, 1);
    }

    //aim for headshot, higher crit chance, higher crit damage?, higher miss chance;
    //todo test for balance
    public static Ranged headShot() {
        return new Ranged("HeadShot", 2, 3, 1, 12);
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

    public static Ranged drainArrow() {
        return new Ranged("Draining Arrow", .5, 2, .5,
                .5, 4, 4, .5, .5, 10,
                .75);
    }
}

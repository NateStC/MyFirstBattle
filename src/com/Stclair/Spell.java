package com.Stclair;

import java.util.List;

public class Spell extends Attack {

    double spellDmgMultiplier = 1;
    double physDmgMultiplier = 0;
    double strMultiplier = 0;
    double dexMultiplier = 0;

    public Spell() {
    }

    public Spell(String name) {
        super(name);
    }

    public Spell(String name, int dmgDie) {
        super(name);
        this.setSpellDmgDie(dmgDie);
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
        this.setNumOfRolls(dmgRolls);
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

//    @Override
//    public ActionResultList action(myCharacter caster, myCharacter target) {
//        ActionResultList actionResults = new ActionResultList();
//        int manaCost = this.getTotalManaCost(caster);
//        for (int i = 0; i < this.getNumOfAttacks(); i++) {
//
//            hmcCheck(caster, target);
//
//
//            if (i > 0) {
//                //Only costs mana once if there are multiple attacks
//                manaCost = 0;
//            } else if (manaCost > caster.getMana()) {
//                // returns Out of Mana if insufficient mana to cast
//                actionResults.add(ActionResult.oom(this.getName()));
//                return actionResults;
//            }
//            int heal = 0;
//            int acc = Dice.d20();
//            if (acc == 1) {  //critical fail
//                actionResults.add(ActionResult.miss(this.getName(), manaCost));
//                continue;
//            }
//            boolean crit = (acc == 20);
//
//            //dmg is intelligence and dice
//            double spellDmg = 0;
//            if (this.getSpellDmgDie() > 0) {
//                spellDmg = Dice.die(this.getSpellDmgDie(), this.getNumOfRolls())
//                        + caster.getWeapon().getSpellDmg()
//                        - target.getArmor().getMagicDefRating();
//                if (this.getIntMultiplier() > 0) {
//                    spellDmg += caster.getIntStat() * this.getIntMultiplier() / 3;
//                }
//                spellDmg *= this.spellDmgMultiplier;
//            }
//            double physDmg = 0;
//            if (getPhysDmgDie() > 0) {
//                physDmg = Dice.die(this.getPhysDmgDie())
//                        + caster.getWeapon().getPhysDamage()
//                        - target.getArmor().getArmorRating()
//                        + caster.getStrStat() * this.getStrMultiplier();
//                if (this.physDmgMultiplier > 0) {
//                    physDmg *= this.physDmgMultiplier;
//                }
//            }
//            if (crit) {
//                spellDmg *= this.getCritMultiplier();
//                physDmg *= this.getCritMultiplier();
//            }
//            //factors in hpDrain if applicable
//            if (getHpDrainMultiplier() != 0) {
//                heal += (int) (spellDmg * getHpDrainMultiplier());
//            }
//            int spellDamage = (int) spellDmg;
//            int physicalDamage = (int) physDmg;
//            actionResults.add(new ActionResult(this.getName(), physicalDamage, spellDamage, manaCost, heal, crit));
//            System.out.println(caster.getName() + "'s " + this.getName() + " deals " + spellDmg + " dmg");
//        }
//
//        return actionResults;
//    }

//    @Override
//    public hmc hmcCheck(myCharacter caster, myCharacter target) {   //wisdom increases critical hit chance // int increases hit chance
//        //todo test spell hit/miss/crit balance
//        if (caster.getMana() < this.getTotalManaCost(caster)) {
//            return hmc.OOM;
//        }
//        int roll = Dice.d20();
//        if (roll <= getMissPoint()){
//            return hmc.MISS;
//        }
//        if (roll >= getCriticalHitMin()){
//            return hmc.HIT;
//        }
//
//        roll = accuracyRollModifier(caster, target, roll);
//        double critPoint = 20 + caster.getLevel() * 5;
//        System.out.printf("%s accuracy Roll = %d\n" +
//                        "Accuracy modified = %d\n" +
//                        "CritPoint = %d",
//                this.getName(), roll, (int) rollMod, (int) critPoint);
//        if (rollMod < (double) (target.getDexStat() / 3)) {
//            return hmc.MISS;
//        }
//        if (rollMod > (double) (caster.getLevel() / 4 + caster.getWisStat() / 4)) {
//            return hmc.CRIT;
//        }
//        return hmc.HIT;
//    }

    @Override
    public int critPoint(myCharacter caster) {
        return getCriticalHitMin() + (int) (caster.getWisStat() * this.getWisMultiplier() / 6);
    }

    @Override
    public int hitPoint(myCharacter target) {
        return this.getMissPoint() + (int)(target.getWisStat() / 6);
    }

    @Override
    public int accuracyRollModifier(myCharacter caster) {
        return caster.getWeapon().getAccuracy() +
                (int) (caster.getWisStat() * this.getWisMultiplier() * this.getAccMultiplier() / 3);
    }
}
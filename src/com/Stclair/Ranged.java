package com.Stclair;

public class Ranged extends Attack {
    //todo add ammo type?
    private double strMultiplier = 0;
    private double dexMultiplier = 1;
    private int hitPoint = 2;
    private int critPoint = 25;
    private double critMultiplier = .5;


    public Ranged(String name) {
        super(name);
    }

    public Ranged(String name, int dmgDie) {
        super(name, dmgDie);

    }

    public Ranged(String name, int dmgDie, double dexMultiplier) {
        super(name);
        setPhysDmgDie(dmgDie);
        setDexMultiplier(dexMultiplier);
    }

    //basic ranged attack constructor;
    public Ranged(String name, int dmgDie, double dexMultiplier, double strMultiplier) {
        setName(name);
        setPhysDmgDie(dmgDie);
        setDexMultiplier(dexMultiplier);
        setStrMultiplier(strMultiplier);
    }

    public Ranged(String name, int dmgDie, double dexMultiplier, double physDmgMultiplier,
                  double lvlMultiplier) {
        super(name, dmgDie);
        this.setDexMultiplier(dexMultiplier);
        this.setPhysDmgMultiplier(physDmgMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
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

//    @Override
//    public ActionResultList action(myCharacter attacker, myCharacter target) {
//        ActionResultList actionResults = new ActionResultList();
//        System.out.println(attacker.getName() + " using " + this.getName());
//        for (int i = 0; i < this.getNumOfAttacks(); i++) {
//            int cost = 0;
//            if (i == 1) { //only costs mana for 1st attack in multi-attacks
//                cost = this.getTotalManaCost(attacker);
//                if (cost > attacker.getMana()) { //checks to see if attacker has enough mana
//                    actionResults.add(ActionResult.oom(this.getName()));
//                    continue;
//                }
//            }
//            boolean crit = false;
//            //todo work out adding hitPoint and critPoint to ranged attack
//            switch (hmcCheck(attacker, target)) {
//                case MISS:
//                    actionResults.add(ActionResult.miss(this.getName(), cost));
//                    continue;
//                case CRIT:
//                    crit = true;
//                    break;
//            }
//            int physDmg = 0;
//            if (this.getPhysDmgDie() > 0) {
//                physDmg += Dice.die(this.getPhysDmgDie(), this.getNumOfRolls()) +
//                        (int) (attacker.getStrStat() * this.getStrMultiplier()) +
//                        (int) (attacker.getDexStat() * this.getDexMultiplier()) +
//                        attacker.getWeapon().getPhysDamage();
//                if (this.getPhysDmgMultiplier() > 0) {
//                    physDmg *= this.getPhysDmgMultiplier();
//                }
//            }
//            int spellDmg = 0;
//            if (this.getSpellDmgDie() > 0) {
//                spellDmg += Dice.die(this.getSpellDmgDie(), this.getNumOfRolls()) +
//                        (int) (attacker.getIntStat() * this.getIntMultiplier()) +
//                        attacker.getWeapon().getSpellDmg();
//                if (this.getSpellDmgMultiplier() > 0) {
//                    spellDmg *= this.getSpellDmgMultiplier();
//                }
//            }
//            if (crit) {
//                physDmg *= 2;
//                spellDmg *= 2;
//            }
//            int drain = (int) (physDmg * this.getHpDrainMultiplier());
//
//            actionResults.add(new ActionResult(this.getName(), physDmg, spellDmg, drain, cost, crit));
//        }
//        return actionResults;
//    }

//    public hmc hmcCheck(myCharacter attacker, myCharacter target) {
//        int roll = Dice.d20();
//        System.out.println(this.getName() + " init acc roll = " + roll);
//        if (roll <= hitPoint){
//            return hmc.MISS;
//        }
//        if (roll == 20){
//            return hmc.CRIT;
//        }
//        //todo test ranged accuracy moddifier balance
//        //accuracy is increased by dexterity and weapon accuracy
//        roll += accuracyRollModifier(attacker);
//
//        double newHitPoint = hitPoint + (target.getLevel() * this.getLvlMultiplier() * 2);
//        double newCritPoint = critPoint + (int) (attacker.getLevel() * this.getLvlMultiplier() * 2);
//
//        System.out.printf("Accuracy is %d --- hitPoint is %d and critPoint is %d\n", roll, (int) newHitPoint, (int) newCritPoint);
//
//        if (roll > newCritPoint) {
//            return hmc.CRIT;
//        }
//        if (roll < newHitPoint) {
//            return hmc.MISS;
//        }
//        return hmc.HIT;
//    }

    @Override
    public int critPoint(myCharacter attacker) {
        //todo Ranged critPoint method
        return super.critPoint(attacker);
    }

    @Override
    public int accuracyRollModifier(myCharacter attacker) {
        //todo Ranged rollModifier method
        return super.accuracyRollModifier(attacker);
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getCritPoint() {
        return critPoint;
    }

    public void setCritPoint(int critPoint) {
        this.critPoint = critPoint;
    }


    //***** RANGED ATTACKS ******

    public static Ranged arrowStrike() {
        return new Ranged("Arrow Strike", 8, 1, 1);
    }

    //aim for headshot, higher crit chance, higher crit damage?, higher miss chance;
    //todo test for balance
    public static Ranged headShot() {
        Ranged headShot = new Ranged("HeadShot", 12, 3, 1, 12);
        headShot.setHitPoint(4);
        return headShot;
    }

    public static Ranged spearThrow() {
        Ranged spear = new Ranged("Spear Throw");
        spear.setStrMultiplier(1);

        return new Ranged("Spear Throw", 6, 2, 1);
    }

    public static Ranged fireArrow() {
        Ranged fire = new Ranged("Fire Arrow");
        fire.setDexMultiplier(0.5);
        fire.setIntMultiplier(.5);
        fire.setWisMultiplier(.5);
        fire.setLvlMultiplier(2);
        fire.setPhysDmgMultiplier(.5);
        fire.setSpellDmgMultiplier(.5);
        fire.setSpellDmgDie(6);
        fire.setManaCost(10);

        return fire;
    }

    //todo test knife throw for balance
    public static Ranged knifeThrow() {
        return new Ranged("Knife Throw", 6, .75, 1, 1);
    }

    public static Ranged drainArrow() {
        Ranged drain = new Ranged("Draining Arrow", 4, .5);
        drain.setManaCost(10);
        drain.setPhysDmgMultiplier(.5);
        drain.setSpellDmgMultiplier(.5);
        drain.setIntMultiplier(.5);
        drain.setWisMultiplier(.5);
        drain.setLvlMultiplier(2);
        drain.setSpellDmgDie(4);
        drain.setHpDrainMultiplier(.75);

        return drain;
    }

    //todo test ArrowVolley balance
    public static Ranged arrowVolley() {
        Ranged volley = new Ranged("Arrow Volley", 4);
        volley.setPhysDmgDie(4);
        volley.setNumOfAttacks(6);
        volley.setDexMultiplier(.75);
        volley.setHitPoint(4);

        return volley;
    }
}

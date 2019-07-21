package com.Stclair;

public class Ranged extends Spell {

    //basic ranged attack constructor;
    public Ranged(String name, double dexMultiplier, double accMultiplier, double lvlMultiplier,
                  double physDmgMultiplier, int hitPoint, int critPoint, int dmgDie) {
        this.setName(name);
        this.setDexMultiplier(dexMultiplier);
        this.setAccMultiplier(accMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
        this.setPhysDmgMultiplier(physDmgMultiplier);
        this.setSpellDmgMultiplier(0);
        this.setHitPoint(hitPoint);
        this.setCritPoint(critPoint);
        this.setDmgDie(dmgDie);
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
    public Ranged(String name, double dexMultiplier, double accMultiplier, double lvlMultiplier,
                  double physDmgMultiplier, int hitPoint, int critPoint, int dmgDie, int dmgRolls, double strMultiplier) {
        this.setName(name);
        this.setDexMultiplier(dexMultiplier);
        this.setAccMultiplier(accMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
        this.setPhysDmgMultiplier(physDmgMultiplier);
        this.setSpellDmgMultiplier(0);
        this.setHitPoint(hitPoint);
        this.setCritPoint(critPoint);
        this.setDmgDie(dmgDie);
        this.setDmgRolls(dmgRolls);
        this.setStrMultiplier(strMultiplier);
        this.setIntMultiplier(0);
        this.setWisMultiplier(0);
        this.setChaMultiplier(0);

    }


    //spell ranged attack constructor
    public Ranged(String name, double dexMultiplier, double accMultiplier, double lvlMultiplier,
                  double physDmgMultiplier, double spellDmgMultiplier, int hitPoint,
                  int critPoint, int dmgDie, double intMultiplier, double wisMultiplier, int manaCost,
                  double hpDrainMultiplier) {
        this.setName(name);
        this.setDexMultiplier(dexMultiplier);
        this.setAccMultiplier(accMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
        this.setPhysDmgMultiplier(physDmgMultiplier);
        this.setSpellDmgMultiplier(spellDmgMultiplier);
        this.setHitPoint(hitPoint);
        this.setCritPoint(critPoint);
        this.setDmgDie(dmgDie);
        this.setStrMultiplier(0);
        this.setIntMultiplier(intMultiplier);
        this.setWisMultiplier(wisMultiplier);
        this.setManaCost(manaCost);
        this.setHpDrainMultiplier(hpDrainMultiplier);
    }


    //full constructor


    public Ranged(String name, double strMultiplier, double dexMultiplier, double lvlMultiplier,
                  double physDmgMultiplier, double spellDmgMultiplier, int hitPoint, int critPoint, int dmgDie,
                  int dmgRolls, int numOfAttacks, int dexAccMultiplier, int manaCost, int healDie, double intMultiplier,
                  double wisMultiplier, double chaMultiplier, double hpDrainmultiplier) {
        super(name, strMultiplier, dexMultiplier, lvlMultiplier, physDmgMultiplier, spellDmgMultiplier, hitPoint,
                critPoint, dmgDie, dmgRolls, numOfAttacks, dexAccMultiplier, manaCost, healDie, intMultiplier,
                wisMultiplier, chaMultiplier, hpDrainmultiplier);
    }

    @Override
    public Damage doAttack(myCharacter attacker) {
        System.out.println(attacker.getName() + " using " + this.getName());
        int cost = this.getManaCost();
        if (cost > 0) {
            cost += (attacker.getLevel() * this.getLvlMultiplier()) - (attacker.getWisStat() * this.getWisMultiplier());
        }
        int acc = Dice.d20();
        //critical fail
        if (acc == 1) {
            return new Damage(this.getName(), cost);
        }
        acc += (this.getAccMultiplier() * attacker.getDexStat()) + attacker.getWeapon().getAccuracy();
        //returns missed attack
        if (acc < this.getHitPoint() + (attacker.getLevel() * this.getLvlMultiplier())) {
            return new Damage(this.getName(), false, cost);
        }
        int dmg = Dice.die(this.getDmgDie(), this.getDmgRolls()) +
                (int) (attacker.getStrStat() * this.getStrMultiplier()) +
                (int) (attacker.getDexStat() * this.getDexMultiplier()) +
                (int) (attacker.getWeapon().getPhysDamage() * this.getPhysDmgMultiplier()) +
                (int) (attacker.getWeapon().getSpellDmg() * this.getSpellDmgMultiplier());
        boolean crit = false;
        if (acc >= this.getCritPoint() + attacker.getLevel()) {
            crit = true;
            dmg *= 2;
        }

        int drain = (int) (dmg * this.getHpDrainMultiplier());

        return new Damage(this.getName(), dmg, drain, cost, crit);
    }

    public static Ranged arrowStrike() {
        return new Ranged("Arrow Strike", 1, 1, 1, 1, 2, 20, 8);
    }

    //aim for headshot, higher crit chance, higher crit damage?, higher miss chance;
    //todo test for balance
    public static Ranged headShot() {
        return new Ranged ("HeadShot",2,0.5,2,2,7,25,12);
    }

    public static Ranged spearThrow() {
        return new Ranged ("Spear Throw", .5,1,2,1,2,20,6,2,1);
    }

    public static Ranged fireArrow() {
        return new Ranged ("Fire Arrow", .5,.5,2,.75,.75,2,20,12,.5,.5,10,0);
    }

    //test for balance
    public static Ranged knifeThrow(){
        return new Ranged ("Knife Throw", 1,1,1,1,2,20,6);
    }
}

package com.Stclair;

public class Attack {

    private String name;
    private double strMultiplier = 1;
    private double dexMultiplier = 1;
    private double lvlMultiplier = 1;
    private double physDmgMultiplier = 1;
    private double spellDmgMultiplier = 0;
    private int hitPoint = 1;
    private int critPoint = 20;
    private int dmgDie =6;
    private int dmgRolls =1;
    private int numOfAttacks = 1;
    private double accMultiplier = 0;

    //todo find a way to apply buffs/debuffs to attacks
    //todo add description String for attacks?
//    private String description;
    //private int lvlReq;

    //empty constructor
    public Attack() {
        this.name = "Bash";
    }

    public Attack(String name, int dmgDie, double strMultiplier, double dexMultiplier) {
        this.name = name;
        this.dmgDie = dmgDie;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
    }

    public Attack(String name, int dmgDie, double strMultiplier, double dexMultiplier,
                  double lvlMultiplier, double physDmgMultiplier) {
        this.name = name;
        this.dmgDie = dmgDie;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.lvlMultiplier = lvlMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
    }

    //full constructor
    public Attack(String name, double strMultiplier, double dexMultiplier, double lvlMultiplier,
                  double physDmgMultiplier, double spellDmgMultiplier, int hitPoint, int critPoint, int dmgDie,
                  int dmgRolls, int numOfAttacks, double accMultiplier) {
        this.name = name;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.lvlMultiplier = lvlMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
        this.spellDmgMultiplier = spellDmgMultiplier;
        this.hitPoint = hitPoint;
        this.critPoint = critPoint;
        this.dmgDie = dmgDie;
        this.dmgRolls = dmgRolls;
        this.numOfAttacks = numOfAttacks;
        this.accMultiplier = accMultiplier;
    }

    public Damage doAttack(myCharacter attacker) {
        System.out.println(attacker.getName() + " using " + this.getName());
        int acc = Dice.d20();
        int critRoll = critPoint;
        //auto miss if rolled 1
        if (acc == 1) {
            return new Damage(this.getName(),false);
        }
        if (accMultiplier > 0) {
            acc += (int) (this.accMultiplier * attacker.getDexStat()) - (int) (attacker.getLevel() * this.lvlMultiplier);
            critRoll += (int) (attacker.getLevel() * this.lvlMultiplier);
            //missed if accuracy is not high enough
            if (acc < hitPoint + (int)(attacker.getLevel() * this.lvlMultiplier)) {
                return new Damage(this.getName(), false);
            }
        }
        int dmg = Dice.die(this.dmgDie, this.dmgRolls) +
                (int) (attacker.getLevel() * this.getLvlMultiplier()) +
                (int) (attacker.getStrStat() * this.strMultiplier) +
                (int) (attacker.getLevel() * this.lvlMultiplier) +
                (int) (attacker.getWeapon().getPhysDamage() * physDmgMultiplier) +
                (int) (attacker.getWeapon().getSpellDmg() * spellDmgMultiplier);
        boolean crit = false;
        if (acc >= critRoll) {
            crit = true;
            System.out.println("Critical hit");
            dmg *= 2;
        }
        return new Damage(this.name, dmg, crit);
    }

    //basic attack
    public static Attack stab() {
        return new Attack("Stab", 6, 0.5, 1);
    }

    //todo test with spellcaster
    public static Attack bash() {
        return new Attack();
    }

    //dagger attack that relies on dexterity for damage and better crit change
    //todo test daggerSlice attack and find a better name, (maybe eviscerate?)
    public static Attack daggerSlice() {
        return new Attack ("Dagger Slice", 6, 0, 2);
    }

    public static Attack swordSlice() {
        return new Attack("Sword Slice", 12,1,0.5,1,1.5);
    }

    public static Attack smash() {
        return new Attack("Smash", 6, 1.5, 1, 1, 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLvlMultiplier(double lvlMultiplier) {
        this.lvlMultiplier = lvlMultiplier;
    }

    public double getLvlMultiplier() {
        return lvlMultiplier;
    }

    public void setCritPoint(int critPoint) {
        this.critPoint = critPoint;
    }

    public int getCritPoint() {
        return critPoint;
    }

    public int getDmgDie() {
        return dmgDie;
    }

    public void setDmgDie(int dmgDie) {
        this.dmgDie = dmgDie;
    }

    public int getDmgRolls() {
        return dmgRolls;
    }

    public void setDmgRolls(int dmgRolls) {
        this.dmgRolls = dmgRolls;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public double getStrMultiplier() {
        return strMultiplier;
    }

    public void setStrMultiplier(double strMultiplier) {
        this.strMultiplier = strMultiplier;
    }

    public double getDexMultiplier() {
        return dexMultiplier;
    }

    public void setDexMultiplier(double dexMultiplier) {
        this.dexMultiplier = dexMultiplier;
    }

    public double getPhysDmgMultiplier() {
        return physDmgMultiplier;
    }

    public void setPhysDmgMultiplier(double physDmgMultiplier) {
        this.physDmgMultiplier = physDmgMultiplier;
    }

    public double getSpellDmgMultiplier() {
        return spellDmgMultiplier;
    }

    public void setSpellDmgMultiplier(double spellDmgMultiplier) {
        this.spellDmgMultiplier = spellDmgMultiplier;
    }

    public int getNumOfAttacks() {
        return numOfAttacks;
    }

    public void setNumOfAttacks(int numOfAttacks) {
        this.numOfAttacks = numOfAttacks;
    }

    public double getAccMultiplier() {
        return accMultiplier;
    }

    public void setAccMultiplier(double accMultiplier) {
        this.accMultiplier = accMultiplier;
    }

    //    public String getDescription() {
//        return description;
//    }
}

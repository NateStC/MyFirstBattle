package com.Stclair;

public class Attack {

    private String name = "Attack";
    private int physDmgDie = 6;
    private int spellDmgDie = 0;
    private double strMultiplier = 0;
    private double dexMultiplier = 0;
    private double intMultiplier = 0;
    private double wisMultiplier = 0;
    private double chaMultiplier = 0;
    private double lvlMultiplier = 1; // how much lvl affects across the board on an attack
    private double physDmgMultiplier = 1;
    private double spellDmgMultiplier = 0;
    private double critMultiplier = 2;
    private int numOfRolls = 1;
    private int numOfAttacks = 1;
    private int manaCost = 0;
    private int criticalHitMin = 20;
    private int missPoint = 1;
    private double accMultiplier = .5; // how much Dex (or wisdom for spells) and weaponAccuracy affects attack roll
    private double critModifier = 0; // how much Dexterity (or wisdom for spells) affects crit chance
    private double hpDrainMultiplier = 0;
    private String description = "No description available";

    //todo add cooldown for attacks
//    private int coolDown = 0;

    //todo find a way to apply buffs/debuffs to attacks
    //todo add description String for attacks?
    //private int lvlReq;

    public Attack() {
    }

    public Attack(String name) {
        this.name = name;
    }

    public Attack(String name, int dmgDie) {
        this.name = name;
        this.physDmgDie = dmgDie;
    }

    //simple attack
    public Attack(String name, int dmgDie, double strMultiplier, double dexMultiplier) {
        this.name = name;
        this.physDmgDie = dmgDie;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
    }

    public Attack(String name, int physDmgDie, double strMultiplier, double dexMultiplier, double lvlMultiplier, double physDmgMultiplier) {
        this.name = name;
        this.physDmgDie = physDmgDie;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.lvlMultiplier = lvlMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
    }

    public Attack(String name, int dmgDie, int numOfRolls, int numOfAttacks, double strMultiplier, double dexMultiplier) {
        this.name = name;
        this.physDmgDie = dmgDie;
        this.numOfRolls = numOfRolls;
        this.numOfAttacks = numOfAttacks;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
    }

    //physical attack w/magic
    public Attack(String name, int physDmgDie, int spellDmgDie, int numOfAttacks, double strMultiplier, double dexMultiplier,
                  double intMultiplier, double physDmgMultiplier, double spellDmgMultiplier) {
        this.name = name;
        this.physDmgDie = physDmgDie;
        this.spellDmgDie = spellDmgDie;
        this.numOfAttacks = numOfAttacks;
        this.strMultiplier = strMultiplier;
        this.dexMultiplier = dexMultiplier;
        this.intMultiplier = intMultiplier;
        this.physDmgMultiplier = physDmgMultiplier;
        this.spellDmgMultiplier = spellDmgMultiplier;
    }

    // Physical attacks offer no accuracy bonus, just a straight d20 with 1 being miss, 20 being crit
    public ActionResultList action(myCharacter attacker, myCharacter target) {
        System.out.println(attacker.getName() + " using " + this.getName());
        ActionResultList actionResults = new ActionResultList();
        for (int i = 0; i < this.numOfAttacks; i++) {
            int manaCost = 0;
            int heal = 0;
            boolean crit = false;

            if (i == 1 && this.manaCost > 0) { //only costs mana for 1st strike in multi-attack
                manaCost = getTotalManaCost(attacker);
                if (manaCost > attacker.getMana()) { //returns OOM if player does not have enough mana to cast
                    actionResults.add(ActionResult.oom(this.name));
                    continue;
                }
            }
            switch (hmcCheck(attacker, target)) {
                case CRIT:
                    crit = true;
                    break;
                case MISS:
                    actionResults.add(ActionResult.miss(this.name, manaCost));
                    continue;
            }

            //damage rolls
            int physDmg = calcPhysDmg(attacker, target);
            int spellDmg = calcSpellDmg(attacker, target);

            if (crit) {
                physDmg *= critMultiplier;
                spellDmg *= critMultiplier;
                System.out.println("Critical Hit!");
            }
            actionResults.add(new ActionResult(this.name, physDmg, spellDmg, manaCost, heal, crit));
        }
        return actionResults;
    }

    public hmc hmcCheck(myCharacter attacker, myCharacter target) {
        int roll = Dice.d20();
        if (roll <= missPoint){ //auto-fail
            return hmc.MISS;
        }
        if (roll >= criticalHitMin){ //natrual crit
            System.out.println("Natural crit");
            return hmc.CRIT;
        }
        roll += accuracyRollModifier(attacker);
        if (roll > critPoint(attacker)){
            return hmc.CRIT;
        }
        if (roll < hitPoint(target)){
            return hmc.MISS;
        }
        return hmc.HIT;
    }

    public int critPoint(myCharacter attacker) {
        return criticalHitMin + (int)(attacker.getDexStat() /4 * this.critModifier);
    }

    public int hitPoint(myCharacter target){
        return missPoint + (int)(target.getDexStat() / 6);
    }

    public int accuracyRollModifier(myCharacter attacker) {
        return (int) (
                (attacker.getWeapon().getAccuracy() + attacker.getDexStat())
                        * this.accMultiplier);
    }

    public int calcPhysDmg(myCharacter attacker, myCharacter target){
        double dmg = Dice.die(this.physDmgDie, this.numOfRolls)
                + attacker.getWeapon().getPhysDamage()
                + (attacker.getDexStat() * this.strMultiplier)
                + (attacker.getStrStat() * this.strMultiplier)
                - target.getArmor().getArmorRating();
        if (physDmgMultiplier > 0) {
            dmg *= physDmgMultiplier;
        }
        return (int)dmg;
    }

    public int calcSpellDmg(myCharacter attacker, myCharacter target){
        if (this.spellDmgDie == 0) return 0;
        double dmg = Dice.die(this.spellDmgDie, this.numOfRolls)
                + attacker.getIntStat() * this.getIntMultiplier() / 2.5
                - target.getArmor().getMagicDefRating();
        if (spellDmgMultiplier > 0) {
            dmg *= spellDmgMultiplier;
        }
        return (int)dmg;
    }

    public int getTotalManaCost(myCharacter caster) {
        if (manaCost == 0) {
            return 0;
        }
        int cost = this.getManaCost()
                - (int) (caster.getWisStat() * wisMultiplier / 4)
                + (int) (caster.getLevel() * lvlMultiplier);
        if (cost < manaCost / 4) {
            System.out.println("Calculated mana cost below 25%");
            return manaCost / 4;
        }
        return cost;
    }

    public void setRolls(int physDmgDie, int spellDmgDie, int numOfRolls, int numOfAttacks) {
        this.physDmgDie = physDmgDie;
        this.spellDmgDie = spellDmgDie;
        this.numOfRolls = numOfRolls;
        this.numOfAttacks = numOfAttacks;
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

    public int getPhysDmgDie() {
        return physDmgDie;
    }

    public void setPhysDmgDie(int physDmgDie) {
        this.physDmgDie = physDmgDie;
    }

    public int getNumOfRolls() {
        return numOfRolls;
    }

    public void setNumOfRolls(int numOfRolls) {
        this.numOfRolls = numOfRolls;
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

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
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

    public int getSpellDmgDie() {
        return spellDmgDie;
    }

    public void setSpellDmgDie(int spellDmgDie) {
        this.spellDmgDie = spellDmgDie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCritMultiplier() {
        return critMultiplier;
    }

    public void setCritMultiplier(double critMultiplier) {
        this.critMultiplier = critMultiplier;
    }

    public int getCriticalHitMin() {
        return criticalHitMin;
    }

    public void setCriticalHitMin(int criticalHitMin) {
        this.criticalHitMin = criticalHitMin;
    }

    public int getMissPoint() {
        return missPoint;
    }

    public void setMissPoint(int missPoint) {
        this.missPoint = missPoint;
    }

    public double getAccMultiplier() {
        return accMultiplier;
    }

    public void setAccMultiplier(double accMultiplier) {
        this.accMultiplier = accMultiplier;
    }

    public double getCritModifier() {
        return critModifier;
    }

    public void setCritModifier(double critModifier) {
        this.critModifier = critModifier;
    }
}

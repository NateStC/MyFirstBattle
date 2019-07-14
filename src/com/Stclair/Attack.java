package com.Stclair;

public class Attack {

    private String name;
    private int accuracy;
    private int damage;
    private boolean isCrit;

    //todo find a way to apply buffs/debuffs to attacks
    //todo add description String for attacks?
//    private String description;
    //private int lvlReq;

    //empty constructor
    public Attack() {
    }

    //full constructor
    public Attack(String name, int accuracy, int damage, boolean isCrit) {
        this.name = name;
        this.accuracy = accuracy;
        this.damage = damage;
        this.isCrit = isCrit;
//        this.description = description;
    }

    //constructor for healing spells
    public Attack(String name, boolean isCrit) {
        this.name = name;
        this.damage = 0;
        this.isCrit = isCrit;
        this.accuracy = 19;
    }

    //basic attack
    public static Attack stab(myCharacter attacker) {
        int att = Dice.d20();
        int dmg = Dice.d6() + ((attacker.getStrStat() + attacker.getDexStat()) / 2) + attacker.getLevel();
        boolean crit = false;
        if (att == 1) {
            dmg = 0;
            System.out.println("Stab missed");
        }
        if (att == 20) {
            crit = true;
            System.out.println("Critical hit");
            dmg *= 2;
        }
        return new Attack("Stab", att, dmg, crit);
    }

    //todo go through and at weapon physDmg and spellDmg to attacks.

    //dagger attack that relies on dexterity for damage and better crit change
    //todo test daggerSlice attack and find a better name, (maybe eviscerate?)
    public static Attack daggerSlice(myCharacter attacker) {
        int att = Dice.d20();
        int dmg = Dice.d12() + attacker.getDexStat() + attacker.getLevel();
        boolean crit = false;
        if (att == 1) {
            dmg = 0;
        } else {
            att += attacker.getDexStat() / 5;
            if (att >= 20) {
                crit = true;
                dmg *= 2;
            }
        }
        return new Attack("Dagger Slice", att, dmg, crit);
    }

    //sword attack
    //todo test swordSlice attack and come up with a much better name
    public static Attack swordSlice(myCharacter attacker){
        int att = Dice.d20();
        int dmg = Dice.d12() + (int)(attacker.getStrStat()*1.5) + attacker.getLevel();
        boolean crit = false;
        if (att==1) {
            dmg = 0;
        }
        if (att==20){
            dmg*=2;
            crit=true;
        }
        return new Attack("Sword Slice", att, dmg, crit);
    }

    public static Attack smash(myCharacter attacker) {
        int acc = Dice.d20();
        int dmg = Dice.d8() + (attacker.getStrStat() / 2) + attacker.getLevel();
        boolean crit = false;
        if (acc == 1) {
            System.out.println("Smash missed!");
            dmg = 0;
        }
        if (acc == 20) {
            crit = true;
            dmg *= 2;
            System.out.println("Critical hit!");
        }
        System.out.println("Smash deals " + dmg + " damage");
        return new Attack("Smash", acc, dmg, crit);
    }

    //todo test with spellcaster
    public static Attack bash(myCharacter attacker) {
        int acc = Dice.d20();
        int dmg = Dice.d6() + (attacker.getStrStat() / 2) + attacker.getLevel();
        boolean crit = false;
        if (acc == 1) {
            dmg = 0;
            System.out.println("Bash missed");
        }
        if (acc == 20) {
            crit = true;
            dmg *= 1.5;
            System.out.println("Critical hit!");
        }
        System.out.println("Bash deals " + dmg + " damage");
        return new Attack("Bash", acc, dmg, crit);
    }

    public static Attack arrowStrike(myCharacter attacker) {
        int acc = Dice.d20();
        int dmg = Dice.d8() + (attacker.getDexStat() / 2);
        boolean crit = false;
        if (acc == 1) {
            System.out.println("Bow and arrowStrike missed");
            dmg = 0;
        } else {
            acc += attacker.getDexStat() / 5;
        }
        if (acc >= 20) {
            System.out.println("Critical hit!");
            dmg += 2;
            crit = true;
        }
        return new Attack("Arrow Strike", acc, dmg, crit);
    }

    //aim for headshot, higher crit chance, higher crit damage, higher miss chance;
    //todo test for balance
    public static Attack headShot(myCharacter attacker){
        int acc = Dice.d20() + attacker.getDexStat() - (attacker.getLevel()*2);
        int dmg = Dice.d12() + (attacker.getDexStat()/2);
        boolean crit = false;
        if (acc < 12){
            dmg = 0;
        }
        if (acc > 25){
            crit = true;
            dmg *= 2.25;
        }
        return new Attack("Headshot",acc,dmg,crit);
    }

    public String getName() {
        return name;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isCrit() {
        return isCrit;
    }

//    public String getDescription() {
//        return description;
//    }
}

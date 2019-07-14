package com.Stclair;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends myCharacter {

    private String entrance = "";

    // super?
    public Enemy() {  //Empty constructor calls kobold/ base enemy.
        setName("Kobold");
        setStats(5, 5, 6, 3, 3, 3);
        setLevel(1);
        fullHealth();
        fullMana();
        equipWeapon(Weapon.dagger());
        System.out.println("A " + name + " appears.");
        System.out.println("It has " + this.getHealth() + " health.");
    }

    //Simple lvl adjusted kobold
    public Enemy(int lvl) {
        setName("Kobold");
        setLevel(lvl);
        setStats(4 + lvl, 4 + lvl, 5 + lvl, 2 + lvl, 2 + lvl, 2 + lvl);
        equipWeapon(Weapon.dagger());
    }

    public Enemy(String name) {   //construct generic man.
        setName(name);
        setStats(5, 5, 5, 5, 5, 5);
        setLevel(1);
    }

    public Enemy(String name, int str, int con, int dex, int intel, int wis, int cha, int lvl, Weapon weapon) {
        setName(name);
        setStats(str, con, dex, intel, wis, cha, lvl);
        equipWeapon(weapon);
    }

    public String getEntrance() {
        if (!entrance.isBlank()) {
            return entrance;
        }
        Random rand = new Random();

        switch (rand.nextInt(6)) {
            case 0:
                return "stands before you.";
            case 1:
                return "comes forward aggressively.";
            case 2:
                return "grunts angrily, ready to attack.";
            case 3:
                return "is ready for battle.";
            case 4:
                return "digs into an attack stance";
            default:
                return "has appeared";
        }
    }

    public Attack defaultAttack() {
        // look for healing spells if health is lew enough and if enemy is smart enough
        ArrayList<String> attackArray = new ArrayList<>(this.getWeapon().getAttacks());

        for (String att : attackArray) {
            if (att.equalsIgnoreCase("healingHands") && Spell.getHealingHandsCost(this) <= this.getMana()) {
                if (this.getLevel() > 5 && this.getIntStat() > this.getLevel() * 5 && (double) this.getHealth() / (double) this.getMaxHealth() < 0.35) {
                    return Spell.healingHands(this);
                } else {
                    attackArray.remove("healingHands");
                    break;
                }
            }
            if (att.equalsIgnoreCase("drainLife") && Spell.getDrainlifeCost(this) <= this.getMana()) {
                if (this.getLevel() > 3 && (double)this.getHealth() / (double) this.getMaxHealth() <.5){
                    return Spell.drainLife(this);
                } else {
                    attackArray.remove("drainLife");
                    break;
                }
            }
        }

        //chooses random attack from list of attacks for weapon
        Random rand = new Random();
        String att = attackArray.get(rand.nextInt(this.getWeapon().getAttacks().size()));
        switch (att.toLowerCase()) {
            case "stab":
                return Attack.stab(this);
            case "bash":
                return Attack.bash(this);
            case "daggerslice":
                return Attack.daggerSlice(this);
            case "swordslice":
                return Attack.swordSlice(this);
            case "smash":
                return Attack.smash(this);
            case "arrowStrike":
                return Attack.arrowStrike(this);
            case "headShot":
                return Attack.headShot(this);

//          if there isn't enough mana for spells, it rerolls with
            case "fireball":
                if (this.getMana() >= Spell.getFireBallCost(this)) {
                    return Spell.fireball(this);
                } else {
                    return noManaAttacks();
                }
            case "staticshock":
                if (this.getMana() >= Spell.getStaticShockCost(this)) {
                    return Spell.staticShock(this);
                } else {
                    return noManaAttacks();
                }
            case "firearrow":
                if (this.getMana() >= Spell.getFireArrowCost(this)) {
                    return Spell.fireArrow(this);
                } else {
                    return noManaAttacks();
                }
        }
        return Attack.bash(this);
    }

    private Attack noManaAttacks() {
        Random rand = new Random();
        String att = this.getWeapon().getAttacks().get(rand.nextInt(this.getWeapon().getAttacks().size()));
        switch (att.toLowerCase()) {
            case "stab":
                return Attack.stab(this);
            case "bash":
                return Attack.bash(this);
            case "daggerslice":
                return Attack.daggerSlice(this);
            case "swordslice":
                return Attack.swordSlice(this);
            case "smash":
                return Attack.smash(this);
            case "arrowStrike":
                return Attack.arrowStrike(this);
            case "headShot":
                return Attack.headShot(this);
            default:
                //recalls the method until it gets a match to an attack that doesn't cost mana;
                return noManaAttacks();
        }
    }

    // returns experience gained on defeat
    // 2x level times the average of all base stats
    public int getExperienceGained() {
        int a = this.getStrength() + this.getConstitution() + this.getDexterity() + this.getWisdom() + this.getIntelligence()
                + this.getCharisma();
        double b = a / 5;
        return (int) (b * (this.getLevel() * 2));
    }
}

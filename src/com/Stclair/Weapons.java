package com.Stclair;

import java.util.ArrayList;

public class Weapons {

    //default weapon
    public static Weapon dagger() {
        ArrayList<Attack> attackList = new ArrayList<>();
        attackList.add(Attack.stab());
        attackList.add(Attack.bash());
        attackList.add(Ranged.knifeThrow());
        attackList.add(Spell.healingHands());

        return new Weapon("Rusty Dagger", 1.5, 5, 6, 1, 0,
                0, 0, 0, 0, 0, 0, attackList);
    }

    //basic weapons
    //balanced for spellSword
    public static Weapon swordShort() {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Attack.swordSlice());
        attacks.add(Attack.stab());
        attacks.add(Spell.fireball());
        attacks.add(Spell.healingHands());

        return new Weapon("Iron Sword Short", 4.0, 5, 6, 2, 2,
                1, 1, 0, 0, 1, 1, attacks);
    }

    public static Weapon longSword() {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Attack.swordSlice());
        attacks.add(Attack.stab());
        attacks.add(Attack.bash());

        return new Weapon("Iron Long Sword", 5.0, 5, 4, 3, 0,
                2, 1, 0, 0, 0, 0, attacks);
    }

    //fast, high dps, high crit
    public static Weapon daggers() {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Attack.daggerSlice());
        attacks.add(Attack.stab());
        attacks.add(Attack.daggerFlurry());
        attacks.add(Spell.drainLife());

        return new Weapon("Twin Daggers", 3.5, 5, 8, 3, 1,
                0, 3, 0, 0, 0, 1, attacks);
    }

    //spellcaster
    public static Weapon staff() {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Spell.fireball());
        attacks.add(Spell.staticShock());
        attacks.add(Spell.magicMissile());
        attacks.add(Spell.healingHands());
        attacks.add(Attack.bash());

        return new Weapon("Oak Staff", 5.0, 5, 3, 0, 5,
                -1, 0, -1, 3, 2, 2, attacks);
    }

    //tank
    public static Weapon mace() {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Attack.bash());
        attacks.add(Attack.smash());
        attacks.add(Spell.healingHands());
        return new Weapon("Dull Spiked Mace", 10.0, 4, 2, 5, 0,
                3, 1, 1, -1, -1, -1, attacks);
    }

    public static Weapon bow() {
        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Ranged.arrowStrike());
        attacks.add(Attack.bash());
        attacks.add(Ranged.headShot());
        attacks.add(Ranged.fireArrow());
        return new Weapon("Oak Bow", 6.0, 6, 4, 4, 0,
                0, 5, 0, 1, 1, 1, attacks);
    }


    ///// GOBLIN WEAPONS

    public static Weapon goblinSpear(int lvl) {
        int physDmg = 3 + lvl;
        int spellDmg = 1 + (lvl / 2);
        int dex = 3 + (int) (lvl * 1.5);
        int intel = 1 + (lvl / 3);


        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Attack.stab());
        attacks.add(Attack.bash());
        attacks.add(Ranged.spearThrow());

        return new Weapon("Goblin Spear", 5, 5, 6, physDmg, spellDmg, lvl, dex,
                0, intel, 0, 0, attacks);
    }

    public static Weapon goblinClub(int lvl) {
        int physDmg = 3 + (int) (lvl * 1.5);
        int str = 4 + (lvl * 2);
        int con = 1 + lvl;

        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Attack.bash());
        attacks.add(Attack.smash());
        attacks.add(Spell.healingHands());
        return new Weapon("Goblin Club", 9, 4, 2, physDmg, 0, str, 0, con,
                0, 0, 0, attacks);
    }

    public static Weapon goblinBow(int lvl) {
        int dex = +lvl;
        int physDmg = 3 + (lvl / 2);
        int intel = 1 + (lvl / 3);
        int wis = 1 + (lvl / 2);
        int cha = 1 + (lvl / 3);

        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Ranged.arrowStrike());
        attacks.add(Attack.bash());
        attacks.add(Ranged.headShot());
        attacks.add(Ranged.fireArrow());

        return new Weapon("Goblin Bow", 6, 6, 4, physDmg, 0, 0, dex,
                0, intel, wis, cha, attacks);
    }

    public static Weapon goblinScepter(int lvl) {
        int physDmg = 2 + (int) (lvl * 1.5);
        int spellDmg = 2 + lvl;
        int str = 1 + lvl;
        int con = 1 + lvl;
        int dex = 1 + lvl;
        int intel = 1 + lvl;
        int wis = 1 + lvl;
        int cha = 1 + lvl;

        ArrayList<Attack> attacks = new ArrayList<>();
        attacks.add(Attack.bash());
        attacks.add(Attack.smash());
        attacks.add(Spell.drainLife());

        return new Weapon("Goblin Scepter", 6, 5, 5, physDmg, spellDmg, str, con, dex,
                intel, wis, cha, attacks);
    }
}

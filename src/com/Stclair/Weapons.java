package com.Stclair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weapons {

    //default weapon
    public static Weapon dagger() {
        return new Weapon("Rusty Dagger", 1.5, 5, 6, 1, 0,
                0, 0, 0, 0, 0, 0, daggerAttacks());
    }

    //basic weapons
    //balanced for spellSword
    public static Weapon swordShort() {
        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList(
                Attack.swordSlice(),
                Attack.stab(),
                Spell.fireball(),
                Spell.healingHands()
        ));

        return new Weapon("Iron Sword Short", 4.0, 5, 6, 2, 2,
                1, 1, 0, 0, 1, 1, attacks);
    }

    public static Weapon shortSword(int lvl) {
        String name = Item.getLvledMetalName(lvl) + " Short Sword";
        int physDmg = Dice.d6() + lvl;
        int spellDmg = (Dice.d6() + lvl) / 2;
        int strBonus = Dice.die(2, lvl);
        int dexBonus = Dice.die(2, lvl);
        int wisBonus = Dice.die(2, lvl);

        return new Weapon(name, 4.0, 5, 6, physDmg, spellDmg, strBonus, dexBonus, 0,
                0, wisBonus, 0, shortSwordAttacks());
    }


    //todo add more attacks for longSword (lung, some sort of multi-attack)
    public static Weapon longSword() {
        return new Weapon("Iron Long Sword", 5.0, 5, 4, 3, 0,
                2, 1, 0, 0, 0, 0, longSwordAttacks());
    }

    //fast, high dps, high crit
    public static Weapon daggers() {
        return new Weapon("Twin Daggers", 3.5, 5, 8, 3, 1,
                0, 3, 0, 0, 0, 1, daggerAttacks());
    }

    //spellcaster
    public static Weapon staff() {
        return new Weapon("Oak Staff", 5.0, 5, 3, 0, 5,
                -1, 0, -1, 3, 2, 2, staffAttacks());
    }

    //tank
    public static Weapon mace() {
        return new Weapon("Dull Spiked Mace", 10.0, 4, 2, 5, 0,
                3, 1, 1, -1, -1, -1, maceAttacks());
    }

    public static Weapon bow() {
        return new Weapon("Oak Bow", 6.0, 6, 4, 4, 0,
                0, 5, 0, 1, 1, 1, bowAttacks());
    }


    //**** UNDEAD WEAPONS ****

    public static Weapon zombieTeeth(int lvl) {
        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList(
                Attack.bash(),
                Attack.bite(),
                Attack.scratch(),
                Attack.drainLife()
        ));

        return new Weapon("Zombie teeth", 1, 5, 5, lvl, lvl / 4, lvl,
                lvl / 2, 0, 0, 0, 0, attacks);
    }

    public static Weapon zombieFists(int lvl) {
        Weapon fists = new Weapon("Zombie Fists", 6 + lvl, 3 + lvl, 2 + lvl, 0,
                lvl * 1, maceAttacks());
        fists.addAttack(Attack.earthQuake());
        fists.setValue(5 + (int) fists.getWeight());
        Attack bash = Attack.bash();
        bash.setPhysDmgDie((int)fists.getWeight());
        fists.addAttack(bash);

        return fists;
    }

    public static Weapon boneBow(int lvl) {
        Weapon boneBow = bow();
        boneBow.setName("Bone Bow");
        bowAttacks().add(Ranged.drainArrow());

        return boneBow;
    }

    public static Weapon vampireTeeth(int lvl) {
        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList(
                Attack.bite(),
                Attack.drainLife(),
                Spell.staticShock()
        ));

        return new Weapon("Vampire teeth", 0, 5, 5, lvl * 2, lvl, 1,
                lvl / 2, lvl, lvl * 2, lvl * 2, lvl * 2, attacks);
    }

    //beast teeth and claws
    public static Weapon beast(int lvl) {
        ArrayList<Attack> atks = new ArrayList<>(Arrays.asList(
                Attack.bite(), Attack.scratch()));

        return new Weapon("Beast", 0, 5, 6, lvl * 2, 0, 1 + lvl / 2,
                1 + lvl / 2, 0, 0, 0, 0, atks);
    }


    ///// GOBLIN WEAPONS

    public static Weapon goblinSword(int lvl) {
        int phyDmg = 2 + lvl;
        int spellDmg = 1 + (lvl / 2);
        int dex = 2 + (lvl / 2);
        int con = lvl / 2 + 1;
        int str = lvl;

        return new Weapon("Goblin Sword", 3, 5, 5, phyDmg, spellDmg, str, dex, con,
                0, 0, 0, shortSwordAttacks());
    }

    public static Weapon goblinSpear(int lvl) {
        int physDmg = 3 + lvl;
        int spellDmg = 1 + (lvl / 2);
        int dex = 3 + (int) (lvl * 1.5);
        int intel = 1 + (lvl / 3);

        return new Weapon("Goblin Spear", 5, 5, 6, physDmg, spellDmg, lvl, dex,
                0, intel, 0, 0, spearAttacks());
    }

    public static Weapon goblinClub(int lvl) {
        int physDmg = 3 + (int) (lvl * 1.5);
        int str = 4 + (lvl * 2);
        int con = 1 + lvl;

        return new Weapon("Goblin Club", 9, 4, 2, physDmg, 0, str, 0, con,
                0, 0, 0, maceAttacks());
    }

    public static Weapon goblinBow(int lvl) {
        int dex = 2 + lvl;
        int physDmg = 3 + (lvl / 2);
        int intel = 1 + (lvl / 3);
        int wis = 1 + (lvl / 2);
        int cha = 1 + (lvl / 3);

        return new Weapon("Goblin Bow", 6, 6, 4, physDmg, 0, 0, dex,
                0, intel, wis, cha, bowAttacks());
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

        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList(
                Attack.bash(),
                Attack.smash(),
                Spell.drainLife()
        ));

        return new Weapon("Goblin Scepter", 6, 5, 5, physDmg, spellDmg, str, con, dex,
                intel, wis, cha, attacks);
    }

    public static Weapon woodSword() {
        Weapon wood = new Weapon("Wooden Sword", 4, 5, 5, 1);
        wood.getAttackList().add(Attack.bash());
        return wood;
    }

    public static Weapon godSword() {
        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList(
                Attack.swordSlice(),
                Attack.smash(),
                Attack.drainLife(),
                Attack.daggerFlurry()
        ));

        return new Weapon("God Sword", 7, 5, 5, 10, 10, 10,
                10, 10, 10, 10, 10, attacks);
    }

    // **** DEFAULT ATTACK LISTS ****

    public static ArrayList<Attack> daggerAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attack.daggerSlice(),
                Attack.stab(),
                Attack.daggerFlurry(),
                Ranged.knifeThrow(),
                Attack.drainLife()
        ));
    }

    public static ArrayList<Attack> shortSwordAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attack.swordSlice(),
                Attack.stab(),
                Attack.bash(),
                Attack.elementalStrike()
        ));
    }

    public static ArrayList<Attack> longSwordAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attack.swordSlice(),
                Attack.stab(),
                Attack.bash()
        ));
    }

    public static ArrayList<Attack> spearAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attack.stab(),
                Attack.bash(),
                Ranged.spearThrow()
        ));
    }

    public static ArrayList<Attack> clubAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attack.bash(),
                Attack.smash(),
                Spell.healingHands()
        ));
    }

    public static ArrayList<Attack> staffAttacks() {
        return new ArrayList<>(Arrays.asList(

                Spell.fireball(),
                Spell.staticShock(),
                Spell.magicMissile(),
                Spell.healingHands(),
                Attack.bash()
        ));
    }

    public static ArrayList<Attack> maceAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attack.bash(),
                Attack.smash(),
                Attack.earthQuake()
        ));
    }

    public static ArrayList<Attack> bowAttacks() {
        return new ArrayList<>(Arrays.asList(
                Ranged.arrowStrike(),
                Attack.bash(),
                Ranged.headShot(),
                Ranged.fireArrow()
        ));
    }
}

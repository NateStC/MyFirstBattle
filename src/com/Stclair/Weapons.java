package com.Stclair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weapons {

    //default weapon
    public static Weapon dagger() {
        Weapon dagger = new Weapon("Rusty Dagger", 3, 1.5, 5, 6, 1, 0,
                0, 0, 0, 0, 0, 0, daggerAttacks());
        dagger.setDescription("An old dagger that time has not treated well");
        dagger.setDropPct(0);

        return dagger;
    }

    //basic weapons
    //balanced for spellSword
    public static Weapon swordShort() {
        Weapon sword = new Weapon("Iron Sword Short", 10, 4.0, 5, 6, 2, 2,
                1, 1, 0, 0, 1, 1, Weapons.shortSwordAttacks());
        sword.setDescription("A simple yet reliable short sword. Great for fast attacks with a splash of some elemental magic");

        return sword;
    }

    public static Weapon shortSword(int lvl) {
        Weapon sword = new Weapon(Item.getLvledMetalName(lvl), (10 + lvl * 3), 4.0);
        sword.setPhysDamage(Dice.d6() + lvl);
        sword.setSpellDmg(Dice.d6() + lvl / 2);
        sword.setStrBonus(Dice.die(2, lvl));
        sword.setDexBonus(Dice.die(2, lvl / 2));
        sword.setWisBonus(Dice.die(2, lvl / 2));
        sword.setAttacks(shortSwordAttacks());

        return sword;
    }


    //todo add more attacks for longSword (lung, some sort of multi-attack)
    public static Weapon longSword() {
        return new Weapon("Iron Long Sword", 20, 5.0, 5, 4, 3, 0,
                2, 1, 0, 0, 0, 0, longSwordAttacks());
    }

    //fast, high dps, high crit
    public static Weapon daggers() {  //todo figure out a way to handle offhand weapons
        return new Weapon("Twin Daggers", 18, 3.5, 5, 8, 3, 1,
                0, 3, 0, 0, 0, 1, daggerAttacks());
    }

    //spellcaster
    public static Weapon staff() {
        return new Weapon("Oak Staff", 25, 5.0, 5, 3, 0, 5,
                -1, 0, -1, 3, 2, 2, staffAttacks());
    }

    //tank
    public static Weapon mace() {
        return new Weapon("Dull Spiked Mace", 18, 10.0, 4, 2, 5, 0,
                3, 1, 1, -1, -1, -1, maceAttacks());
    }

    public static Weapon bow() {
        return new Weapon("Oak Bow", 18, 6.0, 6, 4, 4, 0,
                0, 5, 0, 1, 1, 1, bowAttacks());
    }


    //**** UNDEAD WEAPONS ****

    public static Weapon zombieTeeth(int lvl) {
        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList(
                Attacks.bash(),
                Attacks.bite(),
                Attacks.scratch(),
                Attacks.drainLife()
        ));

        return new Weapon("Zombie teeth", 5, 1, 5, 5, lvl, lvl / 4, lvl,
                lvl / 2, 0, 0, 0, 0, attacks);
    }

    public static Weapon zombieFists(int lvl) { //todo make fists not an item
        Weapon fists = new Weapon("Zombie Fists", 6 + lvl, 3 + lvl, 2 + lvl, 0,
                lvl, maceAttacks());
        fists.addAttack(Attacks.earthQuake());
        fists.setValue(5 + (int) fists.getWeight());
        Attack bash = Attacks.bash();
        bash.setPhysDmgDie((int) fists.getWeight());
        fists.addAttack(bash);

        return fists;
    }

    public static Weapon boneBow(int lvl) {
        Weapon boneBow = bow();
        boneBow.setName("Bone Bow");

        boneBow.addAttack(Ranged.drainArrow());
        boneBow.addAttack(Ranged.arrowStrike());
        boneBow.addAttack(Ranged.arrowVolley());

        return boneBow;
    }

    public static Weapon vampireTeeth(int lvl) {
        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList(
                Attacks.bite(),
                Attacks.zombieDrain(),
                Spells.staticShock(),
                Spells.fireball()
        ));

        return new Weapon("Vampire teeth", 0, .25, 5, 5, lvl * 2, lvl, 1,
                lvl / 2, lvl, lvl * 2, lvl * 2, lvl * 2, attacks);
    }

    //beast teeth and claws
    public static Weapon beast(int lvl) { // todo make beast claws and teeth separate, better items
        ArrayList<Attack> atks = new ArrayList<>(Arrays.asList(
                Attacks.bite(), Attacks.scratch()));

        return new Weapon("Beast", 0, 0, 5, 6, lvl * 2, 0, 1 + lvl / 2,
                1 + lvl / 2, 0, 0, 0, 0, atks);
    }

    ///// GOBLIN WEAPONS

    public static Weapon goblinSword(int lvl) {
        int phyDmg = 1 + lvl;
        int dex = 2 + (lvl / 2);
        int con = lvl / 2 + 1;
        int str = lvl;
        int value = 5 + lvl + dex + con + str;

        return new Weapon("Goblin Sword", value, 3, 5, 5, phyDmg, 0, str, dex, con,
                0, 0, 0, shortSwordAttacks());
    }

    public static Weapon goblinSpear(int lvl) {
        int physDmg = 1 + lvl;
        int dex = 3 + (int) (lvl * 1.5);
        int intel = 1 + (lvl / 3);
        int value = dex + intel + lvl;

        return new Weapon("Goblin Spear", value, 5, 5, 6, physDmg, 0, lvl, dex,
                0, intel, 0, 0, spearAttacks());
    }

    public static Weapon goblinClub(int lvl) {
        int wt = 9 + lvl / 2;
        int physDmg = 1 + lvl;
        int str = 4 + (lvl * 2);
        int con = 1 + lvl;
        int value = wt + str + con;

        return new Weapon("Goblin Club", value, wt, 4, 2, physDmg, 0, str, 0, con,
                0, 0, 0, clubAttacks());
    }

    public static Weapon goblinBow(int lvl) {
        int physDmg = 1 + (lvl / 2);
        int dex = 2 + lvl;
        int intel = 1 + (lvl / 3);
        int wis = 1 + (lvl / 2);
        int cha = 1 + (lvl / 3);
        int value = 5 + dex + intel + wis + cha;


        return new Weapon("Goblin Bow", value, 4, 6, 4, physDmg, 0, 0, dex,
                0, intel, wis, cha, bowAttacks());
    }

    public static Weapon goblinScepter(int lvl) {
        int physDmg = 1 + (int) (lvl * 1.5);
        int spellDmg = 1 + lvl;
        int str = 1 + lvl;
        int con = 1 + lvl;
        int dex = 1 + lvl;
        int intel = 1 + lvl;
        int wis = 1 + lvl;
        int cha = 1 + lvl;
        int value = 15 + lvl * 6;

        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList( //todo add more sceptre attacks
                Attacks.bash(),
                Attacks.smash(),
                Attacks.drainLife()
        ));

        return new Weapon("Goblin Scepter", value, 7, 5, 5, physDmg, spellDmg, str, con, dex,
                intel, wis, cha, attacks);
    }

    public static Weapon woodSword() {
        Weapon wood = new Weapon("Wooden Sword", 4, 5, 5, 1);
        wood.getAttackList().add(Attacks.bash());
        wood.setDescription("A simple sword crudely carved out of wood. Practically a toy.");

        return wood;
    }

    public static Weapon godSword() {
        ArrayList<Attack> attacks = new ArrayList<>(Arrays.asList(
                Attacks.swordSlice(),
                Attacks.smash(),
                Attacks.drainLife(),
                Attacks.daggerFlurry()
        ));

        return new Weapon("God Sword", 1000, 7, 5, 5, 10, 10, 10,
                10, 10, 10, 10, 10, attacks);
    }

    // **** DEFAULT ATTACK LISTS ****

    public static ArrayList<Attack> daggerAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attacks.daggerSlice(),
                Attacks.stab(),
                Attacks.daggerFlurry(),
                Ranged.knifeThrow(),
                Attacks.drainLife()
        ));
    }

    public static ArrayList<Attack> shortSwordAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attacks.swordSlice(),
                Attacks.stab(),
                Attacks.bash(),
                Attacks.elementalStrike()
        ));
    }

    public static ArrayList<Attack> longSwordAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attacks.swordSlice(),
                Attacks.stab(),
                Attacks.bash()
        ));
    }

    public static ArrayList<Attack> spearAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attacks.stab(),
                Attacks.bash(),
                Ranged.spearThrow()
        ));
    }

    public static ArrayList<Attack> clubAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attacks.bash(),
                Attacks.smash(),
                Spells.healingHands()
        ));
    }

    public static ArrayList<Attack> staffAttacks() {
        return new ArrayList<>(Arrays.asList(

                Spells.fireball(),
                Spells.staticShock(),
                Spells.magicMissile(),
                Spells.healingHands(),
                Attacks.bash()
        ));
    }

    public static ArrayList<Attack> maceAttacks() {
        return new ArrayList<>(Arrays.asList(
                Attacks.bash(),
                Attacks.smash(),
                Attacks.earthQuake()
        ));
    }

    public static ArrayList<Attack> bowAttacks() {
        return new ArrayList<>(Arrays.asList(
                Ranged.arrowStrike(),
                Attacks.bash(),
                Ranged.headShot(),
                Ranged.arrowVolley(),
                Ranged.fireArrow()
        ));
    }
}
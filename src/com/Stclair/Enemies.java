package com.Stclair;

import com.Stclair.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Enemies {

    public static Enemy getKobld(int lvl) {
        return new Enemy(lvl);
    }

    public static List<Enemy> goblinGang(int lvl) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(lvl)); //kobold
        enemies.add(goblinGrunt(lvl));
        enemies.add(goblinShaman(lvl));
        enemies.add(goblinArcher(lvl));
        enemies.add(goblinBrute(lvl));
        enemies.add(goblinKing(lvl));

        return enemies;
    }

    public static Enemy goblinGrunt(int lvl) {
        int str = 5 + (lvl);
        int con = 5 + (lvl * 2);
        int dex = 6 + lvl;
        int intel = 3 + lvl;
        int wis = 2 + lvl;
        int cha = 2 + lvl;

        ArrayList<String> entrances = new ArrayList<>();
        entrances.add("waves a jagged spear above its head");
        entrances.add("angrily mutters gibberish that somehow sounds threatening");


        return new Enemy("Goblin Grunt", str, con, dex, intel, wis, cha, lvl, Weapons.goblinSpear(lvl), Armors.goblinmaile(lvl), entrances);
    }

    public static Enemy goblinShaman(int lvl) {
        int str = 3 + (lvl / 2);
        int con = 4 + Dice.die(8,lvl/2);
        int dex = 3 + (lvl / 2);
        int intel = 6 + (lvl * 2);
        int wis = 5 + (int) (lvl * 1.66);
        int cha = 4 + (int) (lvl * 1.75);

        ArrayList<String> entrances = new ArrayList<>();
        entrances.add("shakes its gnarled staff angrily in your direction.");
        entrances.add("summons a small fireball to swirl around its staff.");

        return new Enemy("Goblin Shaman", str, con, dex, intel, wis, cha, lvl, Weapons.staff(), Armors.goblinRobes(lvl), entrances);

    }

    public static Enemy goblinArcher(int lvl) {
        int str = Dice.die(6, lvl / 2);
        int con = 5 + Dice.d6() + Dice.die(8, lvl / 2);
        int dex = (2 * lvl) + Dice.die(6, lvl);
//        int dex = 8 + (int) (lvl * 2.5);
        int intel = Dice.d6() + Dice.die(8, lvl / 3);
        int wis = Dice.d6() + Dice.die(2, lvl);
        int cha = Dice.die(4, lvl);

        ArrayList<String> entrances = new ArrayList<>();
        entrances.add(" hefts its bow above its head, and cries out a war cry.");
        entrances.add(" has you in its sights.");

        return new Enemy("Goblin Archer", str, con, dex, intel, wis, cha, lvl, Weapons.goblinBow(lvl),
                Armors.goblinLeathers(lvl), entrances);
    }

    public static Enemy goblinBrute(int lvl) {
        int str = Dice.die(6, 2) + (lvl * 2);
        int con = (2 * lvl) + Dice.die(6, lvl);
        int dex = Dice.d6() + (lvl / 2);
        int intel = 3 + (lvl / 2);
        int wis = Dice.d6() + (lvl / 3);
        int cha = Dice.d6() + (lvl / 3);

        ArrayList<String> entrances = new ArrayList<>();
        entrances.add(" drags a bulky, gnarled club behind it as it dredges towards you.");
        entrances.add(" roars loudly, spraying its spit in your direction.");

        return new Enemy("Goblin Brute", str, con, dex, intel, wis, cha, lvl, Weapons.goblinClub(lvl),
                Armors.goblinPlate(lvl), entrances);
    }

    public static Enemy goblinKing(int lvl) {
        new Enemy("Goblin King", 8 + (lvl * 2), 9 + (lvl * 3), 8 + (lvl * 2), 7 + (lvl), 7 + lvl, 7 + lvl,
                1 + lvl, Weapons.longSword(), Armors.goblinKingArmor(lvl));
        int str = Dice.die(6, (lvl / 2) + 2);
        int con = 7 + Dice.die(8, lvl);
        int dex = Dice.die(6, lvl * 2);
        int intel = Dice.die(6, (int) (lvl * 1.5));
        int wis = Dice.die(6, lvl);
        int cha = Dice.die(6, lvl);

        ArrayList<String> ent = new ArrayList<>();
        ent.add(" stands before you, crown gleaming.");
        ent.add(" does not appear impressed with you as it prepares for combat.");


        return new Enemy("Goblin King", str, con, dex, intel, wis, cha, lvl, Weapons.goblinScepter(lvl),
                Armors.goblinPlate(lvl), ent);
    }

}

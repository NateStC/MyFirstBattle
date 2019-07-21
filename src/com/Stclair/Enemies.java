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
        enemies.add(new Enemy("Goblin Brute", 7 + (lvl * 3), 8 + (int) (lvl * 1.5), 5 + lvl,
                2 + lvl, 2 + lvl, 2 + lvl, lvl, Weapons.mace(), Armors.goblinPlate(lvl)));
        enemies.add(new Enemy("Goblin King", 8 + (lvl * 2), 9 + (lvl * 3), 8 + (lvl * 2), 7 + (lvl), 7 + lvl, 7 + lvl,
                1 + lvl, Weapons.longSword(), Armors.goblinKingArmor(lvl)));

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
        int con = 4 + (int) (lvl * 1.5);
        int dex = 3 + (lvl / 2);
        int intel = 6 + (lvl * 2);
        int wis = 5 + (int) (lvl * 1.66);
        int cha = 4 + (int) (lvl * 1.75);

        ArrayList<String> entrances = new ArrayList<>();
        entrances.add("shakes its gnarled staff angrily in your direction");
        entrances.add("summons a small fireball to swirl around its staff");

        return new Enemy("Goblin Shaman", str, con, dex, intel, wis, cha, lvl, Weapons.staff(), Armors.goblinRobes(lvl), entrances);

    }

    public static Enemy goblinArcher(int lvl){
        int str = 3 +(lvl/4);
        int con = 6 + (int)(lvl/1.5);
        int dex = 8 + (int)(lvl*2.5);
        int intel = 5 + lvl;
        int wis = 4 + lvl;
        int cha = 3 + (lvl/2);

        ArrayList<String> entrances = new ArrayList<>();
        entrances.add(" hefts its bow above its head, and cries out a war cry");
        entrances.add(" has you in its sights");

        return new Enemy("Goblin Archer", str, con, dex, intel, wis, cha, lvl, Weapons.goblinBow(lvl), Armors.goblinLeathers(lvl));

    }

}

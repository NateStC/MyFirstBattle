package com.Stclair;

import com.Stclair.Enemy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Enemies {

    public static Enemy getKobold(int lvl) {
        return new Enemy(lvl);
    }

    public static List<EnemyGroup> enemyGroups(int lvl) {
        List<EnemyGroup> groups = new ArrayList<>();
        groups.add(goblinGang(lvl));
        groups.add(undeadHorde(lvl));
        groups.add(beasts(lvl));

        return groups;
    }


    //***** GOBLINS ******
    public static EnemyGroup goblinGang(int lvl) {
        EnemyGroup goblinGang = new EnemyGroup("Goblin Gang");
        goblinGang.add(new Enemy(lvl)); //kobold
        goblinGang.add(goblinGrunt(lvl));
        goblinGang.add(goblinShaman(lvl));
        goblinGang.add(goblinSoldier(lvl));
        goblinGang.add(goblinArcher(lvl));
        goblinGang.add(goblinBrute(lvl));
        goblinGang.add(goblinKing(lvl));

        return goblinGang;
    }

    public static Enemy goblinGrunt(int lvl) {
        int str = 5 + (lvl);
        int con = 6 + (lvl * 2);
        int dex = 6 + lvl;
        int intel = 3 + lvl;
        int wis = 2 + lvl;
        int cha = 2 + (lvl / 2);

        ArrayList<String> entrances = new ArrayList<>();
        entrances.add("waves a jagged spear above its head");
        entrances.add("angrily mutters gibberish that somehow sounds threatening");

        Enemy goblin = new Enemy("Goblin Grunt", str, con, dex, intel, wis, cha, lvl, Weapons.goblinSpear(lvl),
                Armors.goblinmaile(lvl), entrances);

        goblin.setGold(lvl * 4);

        Inventory inv = new Inventory();
        inv.addAll(List.of(Weapons.goblinSpear(lvl), Armors.goblinmaile(lvl),
                Items.goblinBeads(Dice.die(3,lvl*2),70)));

        goblin.setInventory(inv);

        return goblin;

    }

    public static Enemy goblinShaman(int lvl) {
        int str = 3 + (lvl / 2);
        int con = 5 + Dice.die(8, lvl / 2 + 1);
        int dex = 3 + (lvl / 2);
        int intel = 6 + (lvl * 2);
        int wis = 5 + (int) (lvl * 1.66);
        int cha = 4 + (int) (lvl * 1.75);

        ArrayList<String> entrances = new ArrayList<>();
        entrances.add("shakes its gnarled staff angrily in your direction.");
        entrances.add("summons a small fireball to swirl around its staff.");

        Enemy goblin = new Enemy("Goblin Shaman", str, con, dex, intel, wis, cha, lvl, Weapons.staff(),
                Armors.goblinRobes(lvl), entrances);

        goblin.setGold(5*lvl);

        return goblin;
    }

    public static Enemy goblinSoldier(int lvl) {
        int str = 5 + (int) (lvl * 1.5);
        int con = 7 + (int) (lvl * 2.25);
        int dex = 6 + (lvl);
        int intel = 3 + (lvl / 2);
        int wis = 3 + (lvl / 2);
        int cha = 2 + (lvl / 2);

        ArrayList<String> entrances = new ArrayList<>(Arrays.asList(
                "points his sword in your direction.",
                "pounds his chest plate while grunting"
        ));
        return new Enemy("Goblin Soldier", str, con, dex, intel, wis, cha, lvl, Weapons.goblinSword(lvl),
                Armors.goblinPlate(lvl));
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
                Armors.goblinKingArmor(lvl), ent);
    }


    // ***** UNDEAD ******

    public static EnemyGroup undeadHorde(int lvl) {
        EnemyGroup horde = new EnemyGroup("Undead Horde");
        horde.add(skeleton(lvl));
        horde.add(zombie(lvl));
        horde.add(skeleArcher(lvl));
        horde.add(vampire(lvl));

        return horde;
    }

    public static Enemy skeleton(int lvl) {
        Enemy skele = new Enemy("Skeleton");
        int str = Dice.die(6, lvl) + 2;
        int dex = Dice.die(6, lvl) + 4;
        int con = Dice.die(6, lvl) + 3;
        int intel = 3 + lvl + Dice.d6();
        int wis = 4 + lvl + Dice.d6();
        int cha = Dice.die(6, lvl) / 2 + 3;
        skele.setStats(str,con,dex,intel,wis,cha);
        int bonesNum = lvl + Dice.die(lvl,1);
        if (bonesNum >10){
            bonesNum = 10;
        }
        skele.receiveLoot(Items.skeleBones(bonesNum,70));


        return new Enemy("Skeleton", str, con, dex, intel, wis, cha, lvl, Weapons.shortSword(lvl),
                Armors.chain(lvl));
    }

    public static Enemy zombie(int lvl) {
        int str = Dice.die(8, lvl) + 3;
        int dex = Dice.die(4, lvl) + 4;
        int con = Dice.die(8, lvl * 2) / 2 + 2;
        int intl = 4 + Dice.die(2, lvl);
        int wis = 4 + Dice.die(2, lvl);
        int cha = 4 + lvl;

        return new Enemy("Zombie", str, con, dex, intl, wis, cha, lvl, Weapons.zombieTeeth(lvl),
                Armors.scrapLeathers());
    }

    public static Enemy skeleArcher(int lvl) {
        int str = Dice.die(4, lvl) + 3;
        int dex = Dice.die(6, lvl) + 6;
        int con = Dice.die(6, lvl) + 3;
        Enemy skeleArcher = skeleton(lvl);
        skeleArcher.setName("Skeleton Archer");
        skeleArcher.setStrength(str);
        skeleArcher.setDexterity(dex);
        skeleArcher.setConstitution(con);
        skeleArcher.equipWeapon(Weapons.boneBow(lvl));
        skeleArcher.equipArmor(Armors.scrapLeathers());

        return skeleArcher;
    }

    public static Enemy bloatedZombie(int lvl) {
        int str = 4 + Dice.die(8, lvl);
        int dex = 3 + Dice.die(6, lvl);
        int con = 10 + Dice.die(8, lvl * 2);
        int intel = 4 + Dice.die(2, (int) (lvl * 1.5));
        int wis = 4 + Dice.die(2, lvl);
        int cha = 4 + lvl / 2;

        ArrayList<String> entrances = new ArrayList<>(Arrays.asList(
                "A Bloated Zombie waddles into the room",
                "A Bloated Zombie comes hulking towards you",
                "A Bloated Zombie smashes his fists on the ground"
        ));

        return new Enemy("Bloated Zombie", str, con, dex, intel, wis, cha, lvl, Weapons.zombieFists(lvl),
                Armors.scrapLeathers(), entrances);

    }

    public static Enemy vampire(int lvl) {
        int str = Dice.die(4, lvl + 2);
        int dex = Dice.die(6, lvl + 1);
        int con = Dice.die(4, lvl * 2);
        int intl = Dice.die(8, lvl + 2);
        int wis = Dice.die(6, lvl + 3);
        int cha = Dice.die(8, lvl + 2);

        return new Enemy("Vampire", str, con, dex, intl, wis, cha, lvl, Weapons.vampireTeeth(lvl),
                Armors.leathers(lvl));
    }


    //*** BEASTS ***
    //TODO add beast armor subclass as thickness of pelt
    //TODO work on beast weapons, different claws and teeth

    public static EnemyGroup beasts(int lvl) {
        EnemyGroup beasts = new EnemyGroup("Beasts");
        beasts.mobs.add(rat(lvl));
        beasts.mobs.add(wolf(lvl));
        beasts.mobs.add(bear(lvl));

        return beasts;
    }

    public static Enemy rat(int lvl) {
        int str = Dice.die(4, lvl);
        int con = Dice.die(4, lvl * 2);
        int dex = Dice.die(6, lvl + 1);
        int intl = Dice.die(2, lvl);
        int wis = Dice.die(2, lvl);
        int cha = Dice.die(2, lvl);

        return new Enemy("Wolf", str, con, dex, intl, wis, cha, lvl, Weapons.beast(lvl), Armors.beast(lvl));
    }

    public static Enemy wolf(int lvl) {
        int str = Dice.die(6, lvl + 1);
        int con = Dice.die(4, lvl * 2);
        int dex = Dice.die(6, lvl + 1);
        int intl = Dice.die(3, lvl + 1 + 2);
        int wis = Dice.die(2, lvl + 1) + 2;
        int cha = Dice.die(2, lvl + 1) + 2;

        return new Enemy("Wolf", str, con, dex, intl, wis, cha, lvl, Weapons.beast(lvl), Armors.beast(lvl));

    }

    public static Enemy bear(int lvl) {
        int str = Dice.die(4, lvl * 2) + 6;
        int con = Dice.die(4, lvl * 2) + 6;
        int dex = Dice.die(4, lvl + 1);
        int intl = Dice.die(3, lvl) + 2;
        int wis = Dice.die(3, lvl) + 2;
        int cha = Dice.die(2, lvl) + 2;

        return new Enemy("Bear", str, con, dex, intl, wis, cha, lvl, Weapons.beast(lvl), Armors.beast(lvl));
    }

    public static Enemy testDummy(int lvl) {
        return new Enemy("Dummy", 4, 20, 4, 4, 4, 4, lvl, Weapons.woodSword(), new Armor());
    }

    public static EnemyGroup dummyTest(int lvl) {
        EnemyGroup dummy = new EnemyGroup("Test Dummy");
        dummy.add(testDummy(lvl));

        return dummy;
    }

    public static class EnemyGroup {
        public List<Enemy> mobs = new ArrayList<>();
        public String name;

        public EnemyGroup(String name) {
            this.name = name;
        }

        public int size() {
            return mobs.size();
        }

        public Enemy get(int index) {
            return mobs.get(index);
        }

        public void add(Enemy enemy) {
            this.mobs.add(enemy);
        }
    }
}

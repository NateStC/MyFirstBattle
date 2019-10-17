package com.Stclair;

public class Armors {

    //beginner robes armor for mage
    public static Armor initiateRobes() {
        return new Armor("Initiate's Robes", 25, 2, 0,4,
                0,0,0,2,1,1);
    }

    //beginner plate armor for tank
    public static Armor copperPlatemail() {
        return new Armor("Copper Platemail", 40, 8, 6, 0, 2, 2, 0,
                0, 0, 0);
    }

    //beginner leather armor for rogue or archer
    public static Armor scrapLeathers() {
        return new Armor("Scrap Leathers",30, 4, 3, 2, 0, 1, 2,
                0, 0, 1);
    }

    //beginner chainmail
    public static Armor tinChainmail() {
        return new Armor("Tin Chainmail",35, 5, 4, 0, 1, 1, 1,
                0, 1, 0);
    }


    // *** lvled Armors ***

    public static Armor chain(int lvl) {
        Armor chain = new Armor();
        chain.setName(Item.getLvledMetalName(lvl) + " Chainmail");
        chain.setValue(25 + lvl * 7);
        chain.setWeight(5 + (int)(lvl / 3));
        chain.setArmorRating(3 + lvl);
        chain.setMagicDefRating(lvl / 2);
        chain.setStrBonus(Dice.die(2, lvl));
        chain.setConBonus(Dice.die(2, lvl));
        chain.setDexBonus(Dice.die(2, lvl));
        chain.setDropPct(30);
        chain.setValue(25 + chain.getStrBonus() + chain.getConBonus() + chain.getDexBonus());

        return chain;
    }

    public static Armor leathers(int lvl) {
        String name = Item.getLvledLeatherName(lvl) + " Leathers";
        int value = 20 + lvl * 5;
        int wt = lvl / 3 + 4;
        int dropPct = 30;
        String desc = "A standard set of leathers made of " + Item.getLvledLeatherName(lvl);
        int armorRating = 2 + lvl;
        int magicDef = lvl;
        int str = 0;
        int con = Dice.die(2, lvl) / 2;
        int dex = Dice.die(3, lvl) + 2;
        int intl = 0;
        int wis = 0;
        int cha = Dice.die(2, lvl) + 1;

        return new Armor(name, value, wt, dropPct, desc, armorRating, magicDef, str, con, dex, intl, wis, cha);
    }

    public static Armor plate(int lvl) {
        String name = Item.getLvledMetalName(lvl) + " Platemail";
        int wt = lvl / 3 + 9;
        String desc = "A standard set of plate armor made out of " + Item.getLvledMetalName(lvl);
        int armorRating = 5 + lvl;
        int magicDef = 0;
        int str = Dice.die(3, lvl);
        int con = Dice.die(3, lvl);
        int dex = 0;
        int intel = 0;
        int wis = 0;
        int cha = 0;
        int dropPct = 30;
        int value = 40 + lvl + str + con + dex + intel + + wis + cha;

        return new Armor(name, value, wt, dropPct, desc, armorRating, magicDef, str, con, dex, intel, wis, cha);
    }

    public static Armor beast(int lvl) {
        int armor = 3 + lvl;
        int magicDef = 1 + lvl;
        int str = Dice.die(2,lvl);
        int con = Dice.die(3,lvl);
        int dex = Dice.die(3,lvl);
        int intl = 0;
        int wis = 0;
        int cha = 0;

        return new Armor("beast", 0, armor, magicDef, str, con, dex, intl, wis, cha);
    }


    // ****   GOBLIN ARMORS **** ////

    //basic goblin armor
    public static Armor goblinLeathers(int lvl) {
        return new Armor("Goblin Leathers", 3, (1 + lvl), (1 + lvl), 0, lvl / 3, lvl / 2,
                0, 0, 0);
    }

    //slightly better goblin armor
    public static Armor goblinmaile(int lvl) {
        return new Armor("Goblinmaile", 5, (2 + lvl), (1 + (lvl / 2)), lvl / 2, lvl / 2, 0,
                0, 0, 0);
    }

    //goblin mage/shaman robes
    public static Armor goblinRobes(int lvl) {
        return new Armor("Goblin Robes", 2, lvl / 3, lvl, 0, 0, 0, lvl,
                lvl / 2, 0);
    }

    //heavy goblin armor
    public static Armor goblinPlate(int lvl) {
        return new Armor("Goblin Plate", 7, 4 + (int) (lvl * 1.5), -lvl, 3 + (lvl * 2), (lvl * 2),
                lvl / 2, 0, 0, 0);
    }

    //king goblin / boss armor
    public static Armor goblinKingArmor(int lvl) {
        return new Armor("Goblin King's Regal Plate", 8, (5 + (lvl * 2)), 2 + lvl,
                2 + (int) (lvl * 1.5), 1 + lvl, 1 + lvl, 0, 0, 0);
    }

    public static Armor rags(){
        return new Armor("Rags", 2,2);
    }

    // **** UNDEAD ARMORS ****

}

package com.Stclair;

public class Armors {

    //beginner robes armor for mage
    public static Armor initiateRobes(){
        return new Armor("Initiate's Robes", 2,0,4,0,0,0,
                2,1,1);
    }

    //beginner plate armor for tank
    public static Armor tinPlatemail(){
        return new Armor("Tin Platemail", 8, 5,0,2,2,0,
                0,0,0);
    }

    //beginner leather armor for rogue or archer
    public static Armor scrapLeathers(){
        return new Armor("Scrap Leathers", 4,3,2,0,1,2,
                0,0,1);
    }

    //beginner chainmail
    public static Armor tinChainmail(){
        return new Armor("Tin Chainmail",5,4,0,1,1,1,
                0,1,0);
    }




    // ****   GOBLIN ARMORS **** ////

    //basic goblin armor
    public static Armor goblinLeathers(int lvl){
        return new Armor("Goblin Leathers", 3,(1+lvl),(1+lvl),0,lvl/3,lvl/2,
                0,0,0);
    }

    //slightly better goblin armor
    public static Armor goblinmaile(int lvl){
        return new Armor("Goblinmaile", 5,(2+lvl),(1+(lvl/2)),lvl/2,lvl/2,0,
                0,0,0);
    }

    //goblin mage/shaman robes
    public static Armor goblinRobes(int lvl){
        return new Armor("Goblin Robes", 2,lvl/3,lvl,0,0,0,lvl,
                lvl/2,0);
    }

    //heavy goblin armor
    public static Armor goblinPlate(int lvl){
        return new Armor("Goblin Plate", 7,4+(int)(lvl*1.5),-lvl,3+(lvl*2),(lvl*2),
                lvl/2,0,0,0);
    }

    //king goblin / boss armor
    public static Armor goblinKingArmor(int lvl){
        return new Armor("Goblin King's Regal Plate", 8, (5 + (lvl*2)), 2+lvl,
                2 + (int)(lvl * 1.5), 1 + lvl, 1+lvl, 0, 0, 0);
    }

}

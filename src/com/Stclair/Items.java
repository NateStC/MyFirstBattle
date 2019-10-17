package com.Stclair;

public class Items {

    public static Item goblinBeads(int quantity, int dropPct) {
        return new Item("Goblin Beads", 5, 0.5, true, quantity, dropPct,
                "Trinkets used by goblins to adorn clothing and armor. Often used as proof for bounties placed on goblins");
    }

    public static Item skeleBones(int quantity, int dropPct){
        if (quantity == 0){
            return null;
        }
        return new Item("Bones", 2, .75,true, quantity, dropPct,
                "All the remains of a defeated skeleton");
    }
}

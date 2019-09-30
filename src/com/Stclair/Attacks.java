package com.Stclair;

public class Attacks {


    //basic attack
    //fixme stab needs more damage
    public static Attack stab() {
        Attack stab = new Attack("Stab", 6, 0.5, 0.5);
        stab.setDescription("Basic attack. Stabby McStabberface");
        return stab;
    }

    //todo test with spellcaster
    //todo find a way to make weight affect damage
    public static Attack bash() {
        Attack bash = new Attack();
        bash.setName("Bash");
        bash.setDescription("Strike the enemy with the hilt of your weapon as a last resort");
        return bash;
    }

    //todo test daggerSlice attack and find a better name, (maybe eviscerate?)
    public static Attack daggerSlice() {
        Attack slice = new Attack();
        slice.setName("Dagger Slice");
        slice.setPhysDmgDie(6);
        slice.setStrMultiplier(0.5);
        slice.setDexMultiplier(1);
        slice.setPhysDmgMultiplier(1.5);
        slice.setDescription("Quickly slice your opponent with your blade");
        return slice;
    }

    public static Attack daggerFlurry() {
        Attack flurry = new Attack();
        flurry.setName("Dagger Flurry");
        flurry.setPhysDmgMultiplier(.75);
        flurry.setNumOfAttacks(4);
        flurry.setStrMultiplier(0.5);
        flurry.setDexMultiplier(1);
        flurry.setDescription("Overwhelm your enemy with a flurry of fast attacks");

        return flurry;
    }

    //fixme swordslice is overpowered
    public static Attack swordSlice() {
        Attack ss = new Attack();
        ss.setName("Sword Slice");
        ss.setPhysDmgDie(6);
        ss.setStrMultiplier(1.5);
        ss.setDexMultiplier(1);
        ss.setDescription("Basic strike with a sword. Reliable");

        return new Attack("Sword Slice", 6, 1.5, 1);
    }

    //fixme overpowered
    public static Attack smash() {
        Attack smash = new Attack();
        smash.setStrMultiplier(1.25);
        smash.setDescription("Crush your opponent with the sheer heft of your weapon and your strength");
        return smash;
    }

    //todo test earthquake attack
    public static Attack earthQuake() {
//        return new Attack("Smash", 4, 2, 4, .75, 0);
        Attack earthQuake = new Attack();
        earthQuake.setPhysDmgDie(4);
        earthQuake.setRolls(2);
        earthQuake.setNumOfAttacks(4);
        earthQuake.setStrMultiplier(.75);
        earthQuake.setDescription("Send out shockwaves of destruction by striking the ground as hard as you can");
        return earthQuake;
    }

    //lvlReq 3?
    //fixme needs more damage
    public static Attack drainLife() {
        Attack drain = new Attack();
        drain.setName("Drain Life");
        drain.setPhysDmgDie(6);
        drain.setSpellDmgDie(6);
        drain.setStrMultiplier(.5);
        drain.setDexMultiplier(1);
        drain.setIntMultiplier(1);
        drain.setPhysDmgMultiplier(.5);
        drain.setHpDrainMultiplier(.5);
        drain.setDescription("Imbue your weapon with magic to sap the life force from your enemy");

        return new Attack("Drain Life", 5, 6, 1, .5, 1,
                1, .5, .5);
    }

    //todo add elemental damage types to attacks, weapons, damage, and resistance to armor
    //TODO finish adding descriptions to attacks and spells
    public static Attack elementalStrike() {
        return new Attack("Elemental Strike", 6, 8, 1, .5, .25,
                .5, .5, .5);
    }

    public static Attack zombieDrain() {
        return new Attack("Zombie Drain", 8, 0, 1, 1, 1,
                0, 1, 0);
    }

    public static Attack bite() {
        return new Attack("Bite", 6, .5, 0);
    }

    public static Attack scratch() {
        return new Attack("Scratch", 6, .25, .5);
    }
}

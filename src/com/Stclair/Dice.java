package com.Stclair;

import java.util.Random;

public class Dice {
    private static Random rand = new Random();

    public static int d6() {
        return rand.nextInt(6) + 1;
    }

    public static int d8() {
        return rand.nextInt(8) + 1;
    }

    public static int d12() {
        return rand.nextInt(12) + 1;
    }

    public static int d20() {
        return rand.nextInt(20) + 1;
    }

    public static int d100() {
        return rand.nextInt(100) + 1;
    }

    public static int die(int sides) {
        return rand.nextInt(sides) + 1;
    }

    public static int die(int sides, int rolls) {
        int num = 0;
        for (int i = 0; i < rolls; i++) {
            num += rand.nextInt(sides) + 1;
        }
        return num;
    }

    public static int goldDice(int lvl, double multiplier) {
        double gold = (Dice.die(lvl * 20) + lvl * 10) * multiplier;
        return (int) gold;
    }

    public static boolean coinFlip() {
        return (rand.nextInt(1) == 1);
    }

    public static int statRoll() {
        int stat1A = d6();
        int stat1B = d6();
        int stat1C = d6();
        int stat1D = d6();

        if (stat1A <= stat1B && stat1A <= stat1C && stat1A <= stat1D) {
            return stat1B + stat1C + stat1D;
        }
        if (stat1B <= stat1A && stat1B <= stat1C && stat1B <= stat1D) {
            return stat1A + stat1C + stat1D;
        }
        if (stat1C <= stat1B && stat1C <= stat1A && stat1C <= stat1D) {
            return stat1B + stat1A + stat1D;
        } else return stat1A + stat1B + stat1D;
    }
}

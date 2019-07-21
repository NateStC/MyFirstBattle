package com.Stclair;

import javafx.collections.ObservableList;

import java.util.Random;

public class Dice {

    public static int d6() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }

    public static int d8() {
        Random rand = new Random();
        return rand.nextInt(8) + 1;
    }

    public static int d12() {
        Random rand = new Random();
        return rand.nextInt(12) + 1;
    }

    public static int d20() {
        Random rand = new Random();
        return rand.nextInt(20) + 1;
    }

    public static int die(int sides) {
        Random rand = new Random();
        return rand.nextInt(sides) + 1;
    }

    public static int die(int sides, int rolls){
        Random rand = new Random();
        int num = 0;
        for (int i=0; i<rolls; i++){
            num += rand.nextInt(sides)+1;
        }
        return num;
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

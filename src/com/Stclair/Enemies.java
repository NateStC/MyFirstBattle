package com.Stclair;

import com.Stclair.Enemy;

import java.util.ArrayList;

public class Enemies {

    public static Enemy getKobld(int lvl){
        return new Enemy(lvl);
    }

    public static ArrayList<Enemy> getGoblinBrigade(int lvl){
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(getKobld(lvl));

        return enemies;
    }

}

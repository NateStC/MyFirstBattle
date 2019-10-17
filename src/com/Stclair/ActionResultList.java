package com.Stclair;

import java.util.ArrayList;

public class ActionResultList extends ArrayList<ActionResult> {
    public ActionResultList() {
    }

    public int getTotalHeals() {
        int heals = 0;
        for (ActionResult actionResult : this) {
            heals += actionResult.getHeal();
        }
        return heals;
    }

    public int getTotalDamage(){
        int dmg = 0;
        for (ActionResult actionResult : this){
            dmg+= actionResult.getDamage();
        }
        return dmg;
    }
}

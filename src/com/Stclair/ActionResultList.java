package com.Stclair;

import java.util.ArrayList;

public class ActionResultList extends ArrayList<ActionResult> {
    public ActionResultList() {
    }

    public int getTotalHeals() {
        int heals = 0;
        for (int h = 0; h < this.size(); h++) {
            heals += this.get(h).getHeal();
        }
        return heals;
    }
}

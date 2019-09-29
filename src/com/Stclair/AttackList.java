package com.Stclair;

import java.util.ArrayList;

public class AttackList extends ArrayList<Attack> {

    @Override
    public Attack get(int index) {
        if (this.isEmpty()){
            return Attack.bash();
        } else {
            return this.get(index);
        }
    }
}

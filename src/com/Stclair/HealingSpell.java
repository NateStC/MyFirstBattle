package com.Stclair;

import java.util.ArrayList;
import java.util.List;

public class HealingSpell extends Spell {

    private int healDie;
    private int dmgDie = 0;
    private int wisMultiplier = 1;
    private int intMultiplier = 0;

    public HealingSpell(String name, int manaCost, int healDie, double intMultipler, double wisMultiplier, double lvlMultiplier) {
        this.setName(name);
        this.healDie = healDie;
        this.setManaCost(manaCost);
        this.setIntMultiplier(intMultipler);
        this.setWisMultiplier(wisMultiplier);
        this.setLvlMultiplier(lvlMultiplier);
    }

    @Override
    public List<ActionResult> action(myCharacter caster, myCharacter target) {
        return this.action(caster);
    }

    public List<ActionResult> action(myCharacter caster) {
        List<ActionResult> healing = new ArrayList<>();
        int manaCost = this.getTotalManaCost(caster);
        for (int i = 0; i < this.getNumOfAttacks(); i++) {
            if (i > 0) {
                //only costs mana once for multi-cast spells
                manaCost = 0;
            } else if (manaCost > caster.getMana()) {
                //returns OOM
                healing.add(ActionResult.oom(this.getName()));
                return healing;
            }
            int heal = 0;
            boolean crit = critCheck(caster, Dice.d20());

            heal = Dice.die(this.healDie, this.getRolls());
            if (crit){
                heal*=2;
            }

            healing.add(new ActionResult(this.getName(), heal, manaCost, crit));
        }
        return healing;
    }

    public int getHealDie() {
        return healDie;
    }

    public void setHealDie(int healDie) {
        this.healDie = healDie;
    }
}

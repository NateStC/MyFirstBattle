package com.Stclair;

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
    public ActionResultList action(myCharacter caster, myCharacter target) {
        System.out.println(caster.getName() + " casting " + this.getName());
        return this.action(caster);
    }

    //fixme Healing spells aren't working
    public ActionResultList action(myCharacter caster) {
        ActionResultList healing = new ActionResultList();
        int manaCost = this.getTotalManaCost(caster);
        boolean crit = false;
        for (int i = 0; i < this.getNumOfAttacks(); i++) {
            if (i == 1 && this.getManaCost() > 0) { //only costs mana for 1st strike in multi-attack
                manaCost = getTotalManaCost(caster);
                if (manaCost > caster.getMana()) { //returns OOM if player does not have enough mana to cast
                    healing.add(ActionResult.oom(this.getName()));
                    continue;
                }
            }
            int heal = calcHeal(caster);
            if (hmcCheck(caster) == hmc.CRIT) {
                System.out.println("Natural Crit");
                crit = true;
                heal *= 2;
            }
            healing.add(new ActionResult(this.getName(), heal, manaCost, crit));
        }
        return healing;
    }

    private int calcHeal(myCharacter caster) {
        double heal = Dice.die(this.healDie, this.getNumOfRolls());
        heal += caster.getWisStat() * this.getWisMultiplier();
        heal += caster.getChaStat() * this.getChaMultiplier();
        heal += caster.getLevel() * this.getLvlMultiplier();
        System.out.println("Heal calculated at : " + heal);
        return (int) heal;
    }

    @Override
    //todo test Healing Spell hmcCheck
    public hmc hmcCheck(myCharacter caster, myCharacter target) {
        return hmcCheck(caster);
    }

    public hmc hmcCheck(myCharacter caster) {
        int roll = Dice.d20();
        if (roll >= getCriticalHitMin()) { //natural crit
            return hmc.CRIT;
        }
        roll += accuracyRollModifier(caster);
        if (roll > critPoint(caster)) {
            return hmc.CRIT;
        }
        return hmc.HIT;
    }

    @Override
    public int critPoint(myCharacter caster) {
        return super.critPoint(caster);
    }

    public int getHealDie() {
        return healDie;
    }

    public void setHealDie(int healDie) {
        this.healDie = healDie;
    }
}

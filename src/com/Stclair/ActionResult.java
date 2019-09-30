package com.Stclair;

public class ActionResult {
    private String attackName;
    private int physDamage = 0;
    private int magicDamage = 0;
    private int manaCost = 0;
    private int heal = 0;
    private boolean crit = false;
    private boolean hit = true;
    private boolean OOM = false;
    private hmc result = ActionResult.hmc.HIT;

    //todo implement enum over booleans
    public enum hmc {HIT, MISS, CRIT, OOM}
    //change from enum to subclasses?

    //basic attack damage
    public ActionResult(String attackName, int hpDamage, boolean crit) {
        this.attackName = attackName;
        this.physDamage = hpDamage;
        this.crit = crit;
    }

    public ActionResult(String attackName) {
        this.attackName = attackName;
    }

    // basic healing spell
    public ActionResult(String attackName, int heal, int manaCost, boolean crit) {
        this.attackName = attackName;
        this.manaCost = manaCost;
        if (heal>0) {
            this.heal = heal;
        }
        this.crit = crit;
        if (crit) result = hmc.CRIT;
    }

    //full constructor
    public ActionResult(String attackName, int physDamage, int magicDamage, int manaCost, int heal, boolean crit) {
        this.attackName = attackName;
        if (physDamage > 0) {
            this.physDamage = physDamage;
        }
        if (magicDamage > 0) {
            this.magicDamage = magicDamage;
        }
        this.manaCost = manaCost;
        this.heal = heal;
        this.crit = crit;
        if (crit) result = hmc.CRIT;
    }

    public static ActionResult miss(String attackName) {
        ActionResult miss = new ActionResult(attackName);
        miss.setHit(false);
        miss.setResult(hmc.MISS);
        return miss;
    }

    public static ActionResult miss(String attackName, int manaCost) {
        ActionResult miss = new ActionResult(attackName);
        miss.setHit(false);
        miss.setManaCost(manaCost);
        miss.setResult(hmc.MISS);

        return miss;
    }

    public static ActionResult oom(String attackName) {
        ActionResult oom = new ActionResult(attackName);
        oom.setOOM(true);
        oom.setHit(false);
        oom.setResult(hmc.OOM);

        return oom;
    }

    public int getDamage() {
        return this.physDamage + this.magicDamage;
    }

    public String getAttackName() {
        return attackName;
    }

    public int getHeal() {
        return heal;
    }

    public int getPhysDamage() {
        return physDamage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public boolean isCrit() {
        return crit;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isOOM() {
        return OOM;
    }

    public void setAttackName(String attackName) {
        this.attackName = attackName;
    }

    public void setPhysDamage(int physDamage) {
        this.physDamage = physDamage;
    }

    public void setMagicDamage(int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public void setCrit(boolean crit) {
        this.crit = crit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setOOM(boolean OOM) {
        this.OOM = OOM;
    }

    public int getMagicDamage() {
        return magicDamage;
    }

    public hmc getResult() {
        return result;
    }

    public void setResult(hmc result) {
        this.result = result;
    }
}

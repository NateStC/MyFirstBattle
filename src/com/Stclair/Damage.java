package com.Stclair;

public class Damage {
    private String attackName;
    private int hpDamage;
    private int manaCost = 0;
    private int heal = 0;
    private boolean crit = false;
    private boolean hit = true;
    private boolean oom = false;

    //basic attack damage
    public Damage(String attackName, int hpDamage, boolean crit) {
        this.attackName = attackName;
        this.hpDamage = hpDamage;
        this.crit = crit;
    }

    //not enough mana fot attack
    public Damage(String attackName, int manaCost) {
        this.attackName = attackName;
        this.oom = true;
        this.hit = false;
        this.hpDamage = 0;
        this.manaCost = manaCost;
    }

    //missed attack
    public Damage (String name, boolean hit){
        this.attackName = name;
        this.hit = false;
        this.hpDamage = 0;
    }

    //missed spell
    public Damage(String attackName, boolean hit, int manaCost) {
        this.attackName = attackName;
        this.hit = false;
        this.hpDamage = 0;
        this.manaCost = manaCost;
    }

    // basic healing spell
    public Damage(String attackName, int heal, int manaCost, boolean crit) {
        this.attackName = attackName;
        this.hpDamage = 0;
        this.manaCost = manaCost;
        this.heal = heal;
        this.crit = crit;
    }

    //full constructor
    public Damage(String attackName, int hpDamage, int heal, int manaCost, boolean crit) {
        this.attackName = attackName;
        this.hpDamage = hpDamage;
        this.heal = heal;
        this.manaCost = manaCost;
        this.crit = crit;
    }

    public String getAttackName() {
        return attackName;
    }

    public int getHeal() {
        return heal;
    }

    public int getHpDamage() {
        return hpDamage;
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

    public boolean isOom() {
        return oom;
    }
}

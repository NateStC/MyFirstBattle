package com.Stclair;

public class Damage {
    private String attackName;
    private int physDamage = 0;
    private int magicDamage = 0;
    private int manaCost = 0;
    private int heal = 0;
    private boolean crit = false;
    private boolean hit = true;
    private boolean OOM = false;

    //basic attack damage
    public Damage(String attackName, int hpDamage, boolean crit) {
        this.attackName = attackName;
        this.physDamage = hpDamage;
        this.crit = crit;
    }

    // OOM - Out Of Mana - not enough mana for attack
    public Damage(String attackName) {
        this.attackName = attackName;
        this.OOM = true;
        this.hit = false;
    }

    //missed attack
    public Damage (String name, boolean hit){
        this.attackName = name;
        this.hit = false;
        this.physDamage = 0;
    }

    //missed spell
    public Damage(String attackName, boolean hit, int manaCost) {
        this.attackName = attackName;
        this.hit = false;
        this.manaCost = manaCost;
    }

    // basic healing spell
    public Damage(String attackName, int heal, int manaCost, boolean crit) {
        this.attackName = attackName;
        this.physDamage = 0;
        this.manaCost = manaCost;
        this.heal = heal;
        this.crit = crit;
    }

    //full constructor
    public Damage(String attackName, int physDamage, int magicDamage, int manaCost, int heal, boolean crit) {
        this.attackName = attackName;
        this.physDamage = physDamage;
        this.magicDamage = magicDamage;
        this.manaCost = manaCost;
        this.heal = heal;
        this.crit = crit;
    }

    public int getDamage(){
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
}

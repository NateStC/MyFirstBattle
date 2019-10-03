package com.Stclair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends myCharacter {

    private List<String> entrance = new ArrayList<>();

    // super?
    public Enemy() {  //Empty constructor calls kobold/ base enemy.
        setName("Kobold");
        setStats(5, 5, 6, 3, 3, 3);
        setLevel(1);
        fullHealth();
        fullMana();
        equipWeapon(Weapons.dagger());
        System.out.println("A " + name + " appears.");
        System.out.println("It has " + this.getHealth() + " health.");
    }

    //Simple lvl adjusted kobold
    public Enemy(int lvl) {
        setName("Kobold");
        setLevel(lvl);
        setStats(4 + lvl, 4 + lvl, 5 + lvl, 2 + lvl, 2 + lvl, 2 + lvl);
        equipWeapon(Weapons.dagger());
        equipArmor(Armors.goblinLeathers(lvl));
        fullHealth();
        fullMana();
    }

    public Enemy(String name) {   //construct generic man.
        setName(name);
        setStats(5, 5, 5, 5, 5, 5);
        setLevel(1);
    }

    public Enemy(String name, int str, int con, int dex, int intel, int wis, int cha, int lvl, Weapon weapon, Armor armor) {
        setName(name);
        setStats(str, con, dex, intel, wis, cha, lvl);
        equipWeapon(weapon);
        equipArmor(armor);
    }

    public Enemy(String name, int strength, int dexterity, int constitution, int intelligence, int wisdom,
                 int charisma, int lvl, Weapon weapon, Armor armor, List<String> entrance) {
        super(name, strength, dexterity, constitution, intelligence, wisdom, charisma, weapon, armor);
        this.entrance = entrance;
        this.experience = getExpForLvl(lvl);
    }

    public Enemy(String name, int[] allStats, Weapon weapon, Armor armor, List<String> entrance) {
        super(name, allStats, weapon, armor);
        this.entrance = entrance;
    }


    public Attack defaultAttack(myCharacter target) {
        Random rand = new Random();
        ArrayList<Attack> attacks = new ArrayList<>(this.getWeapon().getAttackList());
        // remove spells from selection pool if OOM
        for (int i = attacks.size()-1; i >= 0 ; i--) {
            if (attacks.get(i).getTotalManaCost(this) > this.getMana()) {
                attacks.remove(i);
            }
        }

        if (this.getHealthPct() < .20 && this.isSmart()) {
            for (Attack a : attacks) {
                if (a instanceof HealingSpell) {
                    return a;
                }
            }
        }
        return attacks.get(rand.nextInt(attacks.size()));
    }

    public String getEntrance() {
        if (entrance.isEmpty()) {
            Random rand = new Random();

            switch (rand.nextInt(6)) {
                case 0:
                    return "stands before you.";
                case 1:
                    return "comes forward aggressively.";
                case 2:
                    return "grumbles angrily, ready to attack.";
                case 3:
                    return "is ready for battle.";
                case 4:
                    return "digs into an attack stance";
                default:
                    return "has appeared";
            }

        }
        Random rand = new Random();
        return entrance.get(rand.nextInt(entrance.size()));
    }

    // returns experience gained on defeat
    // 2x level times the average of all base stats
    public int getExperienceGained() {
        double a = this.getStrength() + this.getConstitution() + this.getDexterity() + this.getWisdom() + this.getIntelligence()
                + this.getCharisma();
        double b = a / 5;
        return (int) (b * (this.getLevel() * 2));
    }

    public boolean isSmart() {
        return (this.getIntStat() + this.getWisStat() > this.getLevel() * 7);
    }
}

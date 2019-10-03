package com.Stclair;

public class Spells {

    //basic attack spell
    public static Spell fireball() {
        return new Spell("Fireball", 15, 12, .75, 1, 1);
    }

    // Electrocution spell that relies more on player stats than dice
    //todo test staticShock
    public static Spell staticShock() {
        return new Spell("Static Shock", 15, 6, 1.5, 1.5, 1.5);
    }

    public static Spell magicMissile() {
        return new Spell("Magic Missile", 25, 6, 1, 3, .75,
                .5, .5, 1, .5);
    }

    //basic healing spell
    //todo test to make sure constructor still works
    public static HealingSpell healingHands() {
        HealingSpell heal = new HealingSpell("Healing Hands", 15, 12, 0, 1, 1.5);
        heal.setDescription("Heal your wounds with magic.");
        //todo come up with better description for healing hands

        return heal;
    }

}

//package com.Stclair;
//
//public class Spell {
//
//    private int accuracy;
//    private int damage;
//    private int manaCost;
//    private int heal;
//
//    public int fireball (int spellRoll, int charIntelligence, String attackerName, String targetName) {
//        this.mana -= 10;
//        if (spellRoll > 1) {
//            damage = spellRoll + (charIntelligence / 2);
//            this.mana -= 10;
//            if (spellRoll == 6){
//                damage *=2;
//            }
//            System.out.println(attackerName + " hits " + targetName + " with Fireball for " + damage + " damage.");
//
//            return damage;
//        } else {
//            System.out.println(attackerName + " missed!");
//            printMana();
//            return 0;
//        }
//    }
//    public int healingSpell (int spellRoll, int intelligence, int wisdom, int charisma){
//        this.mana -= 10;
//        heal = (intelligence + wisdom + charisma)/6 + spellRoll;
//        if (spellRoll == 6) {
//            heal *= 2;
//            System.out.println("Healing spell was critical!");
//        }
//        System.out.println("Healing spell heals " + heal + " damage.");
//        printMana();
//        return heal;
//    }
//
//
//}

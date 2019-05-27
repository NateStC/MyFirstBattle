package com.Stclair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Character player;

    public static void main(String[] args) {

        boolean gameOver = false;

        System.out.println("Please enter your name.");
        String name = scanner.nextLine();
        player = new Character(name);

        System.out.println("\nGreat!\n");
        ArrayList<Integer> arrayRolls = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            arrayRolls.add(Dice.statRoll());
        }

        player.assignStats(arrayRolls,scanner);
//        player.setHealth();
//        player.setMana();
//        player.updateAttributeArray();
//        Spell spell = new Spell();
        player.printStats();
        System.out.println();

    Inventory inventory = new Inventory();
    Weapon dagger = new Weapon("copper dagger", 0.5, 90, 75, 2);

        inventory.addItem(dagger);

    Enemy goblin = new Enemy();

        System.out.println("Press enter to start battle!");
        scanner.nextLine();

        System.out.println();

    //FIRST BATTLE BEGINS

    boolean battle = true;
        while(battle)

    {

        battle = player.takeDamage(goblin.stab());
        if (player.isDead()) {
            battle = false;
            System.out.println("You have died!");
            gameOver = true;
        }
        System.out.println("Choose an action:" +
                "\n1. Stab" +
                "\n2. Fireball" +
                "\n3. Healing spell");
        switch (scanner.nextInt()) {
            case 1:
                goblin.takeDamage(player.stab());
                break;
            case 2:
                goblin.takeDamage(player.fireball(goblin.name));
                break;
            case 3:
                player.heal(player.healingSpell());
                break;
        }
        if (goblin.isDead()) {
            battle = false;
            System.out.println("You have slain the goblin!\n");
            player.gainExperience(100);
        }
    }
        scanner.nextLine();

        System.out.println("Congratulations! You have won your first battle!"+
                "\nWhat would you like to do? Enter 'help' to view options");
        while(!gameOver)

    {
        switch (scanner.nextLine().toLowerCase()) {
            case "help":
                help();
                continue;
            case "stats":
                player.printStats();
                continue;
            case "quit":
                gameOver = true;
                break;
            default:
                System.out.println("Invalid input. Try again");
                help();

        }
    }

}

//        System.out.println("Now it is time for battle. You approach the lair of the dragon.");
//        System.out.print("You feel the heat through your " + armor + " as your enter the maw of the cave. ");
//        if (weapon == 1) {
//            System.out.println("The light of the torches on the walls glimmer off of your sword. ");
//        }
//        if (weapon == 2) {
//            System.out.println("Your staff illuminates the tunnel.");
//        }
//        System.out.println("You round the corner and find yourself in the large belly of the cave. Towering over you is the dragon.");
//        System.out.println();
//        //System.out.println("What would you like to do?  Enter 'HELP' to view options.");
//
//
//        String action;
//        boolean endGame = false;
//        while (!endGame) {
//            scanner.nextLine();
//            System.out.println("What would you like to do? Enter 'help' to view options");
//            action = scanner.nextLine();
//            switch (action.toLowerCase()) {
//                case "help":
//                    help();
//                    break;
//                case "stats":
//                    printStats(strength, dexterity, constitution, intelligence, wisdom, charisma);
//                    System.out.println("You have " + health + " health left.");
//                    break;
//                case "attack":
//                    if (health <= 0) {
//                        endGame = true;
//                    }
//                    break;
//                case "quit":
//                    endGame = true;
//
//            }
//        }
//
//    }

    public static void help() {
        System.out.println(" ATTACK - basic attack with your weapon.");
        System.out.println(" STATS - review your current stats.");
        System.out.println(" QUIT - ends the game");
    }

    //        if (strength <= 7) {
//            System.out.println("You have noodles for arms!");
//        }
//        if (strength > 13) {
//            System.out.println("Save some muscles for the rest of us!");
//        }

//        if (constitution < 7) {
//            System.out.println("Oof, you're a bleeder.");
//        }
//        if (constitution > 15) {
//            System.out.println("Looks like we found our tank.");

//        if (intelligence < 7) {
//            System.out.println("Not the sharpest sword in the armory are we?");
//        }
//        if (intelligence > 15) {
//            System.out.println("You must have read all of the tomes in the library!");

//        if (wisdom < 7) {
//            System.out.println("Are you sure you're not a rock?");
//        }
//        if (wisdom > 15) {
//            System.out.println("You sure know what you're doing!");
//        }
//        if (charisma < 7) {
//            System.out.println("You couldn't talk your way out of a paper bag!");
//        }
//        if (charisma > 15) {
//            System.out.println("Speech! Speech! Speech!");
//


//        System.out.println("Please enter your " + statName + ".");
//
//        boolean hasNextInt = scanner.hasNextInt();
//        int stat = 0;
//
//        while (!hasNextInt) {
//            String y = scanner.nextLine();
//            System.out.println("Invalid value. " + statName + " must be between 3 and 18. Please try again.");
//            hasNextInt = scanner.hasNextInt();
//        }
//        stat = scanner.nextInt();
//
//
//        while (stat < 3 || stat > 18) {
//            System.out.println("Invalid value. " + statName + " must be between 3 and 18. Please try again.");
//            stat = scanner.nextInt();
//        }
//
//
//        return stat;
//    }

//    public int chooseWeapon() {
//        System.out.println();
//        System.out.println("Which weapon do you wield? Enter the corresponding number");
//        System.out.println("1. SWORD");
//        System.out.println("2. STAFF");
//        System.out.println("3. DAGGERS");
//        System.out.println("4. MACE");
//        System.out.println("5. BOW AND ARROW");
//        int weapon = scanner.nextInt();
//        this.armor = "armor";
//        while (weapon > 5 || weapon < 1) {
//            System.out.println("Invalid choice. Please choose again.");
//            System.out.println("1. SWORD");
//            System.out.println("2. STAFF");
//            System.out.println("3. DAGGERS");
//            System.out.println("4. MACE");
//            System.out.println("5. BOW AND ARROW");
//            weapon = scanner.nextInt();
//        }
//        if (weapon == 1) {  // SWORD
//            this.strength += 5;
//            this.dexterity += 2;
//            constitution += 3;
//            intelligence -= 5;
//            System.out.println("A classic warrior.");
//        }
//        if (weapon == 2) {  // STAFF
//            armor = "robes";
//            strength -= 5;
//            constitution -= 3;
//            intelligence += 10;
//            wisdom += 5;
//            System.out.println("Yer a wizard " + name + "!");
//        }
//
//        if (weapon == 3) {  // DAGGERS
//            armor = "leathers";
//            dexterity += 7;
//            constitution -= 1;
//            intelligence -= 2;
//            wisdom += 2;
//            System.out.println("Fast and deadly!");
//        }
//        if (weapon == 4) { //MACE
//            strength += 10;
//            dexterity -= 2;
//            constitution += 5;
//            intelligence -= 5;
//            wisdom -= 3;
//            System.out.println("Swing away, you brute!");
//        }
//        if (weapon == 5) {  // BOW AND ARROW
//            armor = "leathers";
//            strength -= 3;
//            dexterity += 7;
//            constitution -= 1;
//            intelligence -= 2;
//            wisdom += 3;
//            System.out.println("Aim true, ranger!");
//        }
//        System.out.println();
//        System.out.println("Your stats have adjusted accordingly.");
//        return weapon;
//    }

//    public static int[] getPlayerStats(){
//        return player.getPlayerStats();
//    }
}



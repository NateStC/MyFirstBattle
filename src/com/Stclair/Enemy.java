package com.Stclair;

public class Enemy extends Character{

// super?
    public Enemy (){  //Empty constructor calls goblin/ base enemy.
        setName ("Goblin");
        setStats (5,5,6,3,3,3);
        setLevel (1);
        setHealth(this.getStat(2)*3 + this.level *5);
        System.out.println("A " + name + " appears.");
        System.out.println("It has " + this.health + " health.");
        setMaxHealth(this.health);
    }

    public Enemy (String name){   //construct generic man.
        setName (name);
        setStats (5,5,5,5,5,5);

        setLevel(1);
        setHealth(this.getStat(2)*3 + level*5);
        setMaxHealth(this.health);
    }



    public void healthRemaining(){
        System.out.println("The " + name + " has " + this.health + " health left.");
}



}

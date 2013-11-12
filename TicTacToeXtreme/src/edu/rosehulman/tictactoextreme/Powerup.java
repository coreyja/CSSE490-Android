package edu.rosehulman.tictactoextreme;

import java.io.Serializable;

/**
 * Created by coreyja on 10/28/13.
 */
public abstract class Powerup implements Serializable{

    // Reference to the game object
    protected Game game;

    // Reference to the Player the has this powerup
    protected Player player;

    public Powerup(Game game, Player p){
        this.game = game;
        this.player = p;
    }

    // Will do something if the powerup doesn't need a position
    // If it does need a position, will tell the Player that and wait for position from it. usePowerup(row,col) will be called when it has the selected position.
    // Must also remove the powerup from the user once it is actually used.
    public abstract void usePowerup();

    // This defaults to the same as the above, so only powerups that need a position need to specify this method.
    public void usePowerup(int row, int col){ this.usePowerup();}

    // Will return a string name for each Powerup type
    public abstract String getStringType();

}

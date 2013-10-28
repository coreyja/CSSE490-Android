package edu.rosehulman.tictactoextreme;

import android.util.Log;

/**
 * Created by coreyja on 10/28/13.
 */
public class TwoTurnsPowerup extends Powerup {

    public TwoTurnsPowerup(Game g, Player p){ super(g,p); }

    @Override
    public void usePowerup() {
        Log.d("TEMP", "Used powerup");

        // Add this player to the beginning of the Queue
        this.game.addPlayer(this.player, 0);

        // Remove this powerup from the player
        this.player.removePowerup(this);
    }

    @Override
    public String getStringType() {
        return "Two-Turns";
    }
}

package edu.rosehulman.tictactoextreme;

/**
 * Created by coreyja on 10/28/13.
 */
public class BombPowerup extends Powerup {

    public BombPowerup(Game g, Player p){ super(g,p); }

    @Override
    public void usePowerup() {
        // Tell player that this powerup needs a position to use.
        this.player.getPowerupPosition(this);
    }

    @Override
    public void usePowerup(int row, int col){
        //Player has told us where to use the powerup

        row--; col--; //Decrement both by so I can use a loop to delete the symbols easier.

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (game.isValidRow(row+i) && game.isValidColumn(col+j)){

                    // Delete each symbol in the bombs radius.
                    // Game takes care of the other symbols falling down to fill openings
                    this.game.deleteSymbolAtPosition(row+i, col+j);
                }
            }
        }

        // Check for winner since symbols falling could cause someone to win
        this.game.checkForWinner();

    }

    @Override
    public String getStringType() {
        return "Bomb";
    }

    @Override
    public String getName() {
        return "Bomb Powerup";
    }
}

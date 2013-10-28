package edu.rosehulman.tictactoextreme;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by coreyja on 10/28/13.
 */
public class AIPlayer extends Player{

    // Define constructors. Nothing special needed for now so just call super
    public AIPlayer(Game g){ super(g); }
    public AIPlayer(Game g, String name){ super(g,name); }
    public AIPlayer(Game g, String name, char sym){ super(g,name,sym); }

    @Override
    public void takeTurn() {
        // Currently just takes a random turn somewhere legal.

        int[] heights = this.game.getColHeights();

        ArrayList<Integer> options = new ArrayList<Integer>();
        for (int i = 0; i < 9; i++){
            if (heights[i] < 9){
                options.add(i);
            }
        }

        Random r = new Random();

        int index = r.nextInt(options.size());

        try {
            this.game.currentPlayerPlayInColumn(options.get(index).intValue());
        } catch (NoPlayersInGameException e){
            // Shouldn't happen. If we are running on this player they should be in the game.
            return;
        }

    }

    @Override
    public void getPowerupPosition(Powerup powerup) {
        // TODO Add random powerup position picking
    }
}

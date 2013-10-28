package edu.rosehulman.tictactoextreme;

/**
 * Created by coreyja on 10/28/13.
 */
public class HumanPlayer extends Player {

    // Need to define a constructor or else HumanPlayer will have a default constructor and I don't want one
    public HumanPlayer(Game g){
        super(g);
    }

    @Override
    public void takeTurn() {
        // A human player doesn't actually need to do anything here to take their turn
        // So empty method it is
        // User interaction and choosing of plays will be determined by MainActivity.
    }
}

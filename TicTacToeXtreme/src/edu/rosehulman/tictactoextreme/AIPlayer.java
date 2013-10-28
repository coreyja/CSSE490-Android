package edu.rosehulman.tictactoextreme;

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
        // TODO Add random turn picking for now
    }

    @Override
    public void getPowerupPosition(Powerup powerup) {
        // TODO Add random powerup position picking
    }
}

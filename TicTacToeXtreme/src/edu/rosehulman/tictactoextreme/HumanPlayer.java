package edu.rosehulman.tictactoextreme;

/**
 * Created by coreyja on 10/28/13.
 */
public class HumanPlayer extends Player {

    public static final String TYPE = "Human";

    // Define constructors. Nothing special needed for humans so call super
    public HumanPlayer(Game g){ super(g); }
    public HumanPlayer(Game g, String name){ super(g,name); }
    public HumanPlayer(Game g, String name, char sym){ super(g,name,sym); }

    @Override
    public String getType() {
        return this.TYPE;
    }

    @Override
    public void takeTurn() {
        // A human player doesn't actually need to do anything here to take their turn
        // So empty method it is
        // User interaction and choosing of plays will be determined by MainActivity.
    }

    @Override
    public void getPowerupPosition(Powerup powerup) {
        // Need a position from the user. Tell the listener we are waiting for one and let it handle the rest.
        this.game.getGameChangeListener().onPowerupWaitingForPosition(powerup);
    }
}

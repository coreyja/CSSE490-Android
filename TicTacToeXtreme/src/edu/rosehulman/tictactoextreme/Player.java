package edu.rosehulman.tictactoextreme;

import java.util.ArrayList;

/**
 * Abstract class that HumanPlayer and AIPlayer will extend
 */
public abstract class Player {

    protected Game game;

    protected String name;

    protected char symbol;

    protected ArrayList<Powerup> powerups;

    public Player(Game g, String name, char symbol){
        this.game = g;
        this.setName(name);
        this.setSymbol(symbol);

        this.powerups = new ArrayList<Powerup>();
    }

    public Player(Game g){
        this(g, "Player");
        // Use Player as the default name is none is specified
    }

    public Player(Game g, String name){
        this(g,name, 'X');
        // Use X as the default symbol if none is defined
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public boolean hasPowerups(){
        return !this.powerups.isEmpty();
    }

    public Powerup[] getPowerups(){
        return this.powerups.toArray(new Powerup[0]);
    }

    public void addPowerup(Powerup powerup) {
        this.powerups.add(powerup);
    }

    public void removePowerup(Powerup powerup){
        // Remove the powerup from your ArrayList if it exists
        this.powerups.remove(powerup);
    }

    // Will make a move for an AI player
    // Won't do anything and wait for user input for a human player
    // Will call playerPlayInColumn from this.game
    public abstract void takeTurn();

    // Will be called when a Powerup needs to know where to be used. Will call powerup.usePowerup(row,col) when position is determined.
    // For humans will wait for user input
    // For AI will make the decision
    public abstract void getPowerupPosition(Powerup powerup);


}

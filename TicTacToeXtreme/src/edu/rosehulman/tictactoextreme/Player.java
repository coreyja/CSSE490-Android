package edu.rosehulman.tictactoextreme;

/**
 * Abstract class that HumanPlayer and AIPlayer will extend
 */
public abstract class Player {

    private Game game;

    private String name;

    private char symbol;

    public Player(Game g, String name, char symbol){
        this.game = g;
        this.setName(name);
        this.setSymbol(symbol);
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

    public abstract void takeTurn();
    // Will make a move for an AI player
    // Won't do anything and wait for user input for a human player
    // Will call playerPlayInColumn from this.game


}

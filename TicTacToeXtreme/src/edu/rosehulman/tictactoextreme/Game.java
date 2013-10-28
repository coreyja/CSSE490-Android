package edu.rosehulman.tictactoextreme;

import java.util.Arrays;
import java.util.Queue;

/**
 * Created by coreyja on 10/27/13.
 */
public class Game {

    // Use a queue to keep track of players, and whos turn is next.
    // When a player takes their turn, pop them off the queue and insert them at the end.
    // Current player is on top of the queue
    private Queue<Player> players;

    // Char matrix representing the board.
    // Symbols will depend on the Player's symbol attr
    // Will be used as [row][col]
    private char[][] grid;

    // The number of spaces in each col that have been used
    // 0 means the col is empty
    private int[] colHeight;

    public Game(Player[] playerArray){
        //Add all the players to the Queue
        for (Player p: playerArray){
            this.players.add(p);
        }

        // Init the grid
        this.grid = new char[9][9];

        // Init height counters
        this.colHeight = new int[9];
        Arrays.fill(colHeight, 0); // Most likely inited to 0, but to make sure. Also, most likely not anymore efficient than a loop, but could be optimized somehow
    }

    public Game() {
        //Default constructor should only be used for testing and development

        //Need to add 1 or 2 Humans once I get that written so I can dev easier
    }

    public Player getCurrentPlayer(){
        // Return the head of the queue, as this is the current Player
        return this.players.peek();
    }

    public void endCurrentPlayerTurn() {
        // Pop the next Player off the queue and re-add them so they go to the end
        this.players.add(this.players.poll());
    }


    // Simple getter for entire character grid.
    public char[][] getCharacterGrid(){
        return this.grid;
    }

    // Will return null if symbol does not exist if grid
    public char getSymbolFromGrid(int row, int col){
        if (!isValidColumn(col)){
            throw new IndexOutOfBoundsException(String.format("Column %d does not exists", col));
        }
        if (!isValidRow(row)){
            throw new IndexOutOfBoundsException(String.format("Row %d does not exists", row));
        }

        return this.grid[row][col];
    }

    private int getColumnHeight(int col) throws IndexOutOfBoundsException{
        // Error checking
        if (!isValidColumn(col)){
            // For now throw an exception.
            // Might change this later though if using try/catches for it gets annoying as shit
            throw new IndexOutOfBoundsException(String.format("Column %d does not exists", col));
        }

        // Simply return the number stored in the array
        return this.colHeight[col];
    }

    private void incrementColumnHeight(int col) throws IndexOutOfBoundsException{
        // Error checking
        if (!isValidColumn(col)){
            throw new IndexOutOfBoundsException(String.format("Column %d does not exists", col));
        }

        this.colHeight[col]++;
    }

    // Will most likely be used for human players clicking buttons to pick move
    public void currentPlayerPlayInColumn(int col) throws NoPlayersInGameException, IndexOutOfBoundsException{

        //Wrapping this is a try catch so that it doesn't have to throw PlayerNotInGameException since we are grabbing the current player
        try {
            this.playerPlayInColumn(this.getCurrentPlayer(), col);
        } catch (PlayerNotInGameException e){
            // Will never be able to get here since currentPlayer will always be in the game if the queue isn't empty
            // If queue is empty, NoPlayersInGameException will be thrown instead
        }

    }

    public void playerPlayInColumn(Player p, int col) throws NoPlayersInGameException, IndexOutOfBoundsException, PlayerNotInGameException{
        if (this.players.isEmpty()){
            // This should never happen but if the queue is empty there are no players so throw an exception
            throw new NoPlayersInGameException();
        }
        if (!this.isPlayerInGame(p)){
            //Again, should never happen but in case weird things happen. Will make debugging easier.
            throw new PlayerNotInGameException();
        }

        char symbol = p.getSymbol();

        grid[this.getColumnHeight(col)][col] = symbol;

        this.incrementColumnHeight(col);
    }

    /* Simple Util Methods */

    private boolean isValidColumn(int col){
        //Return true if col is (0,9). False otherwise

        return !(col < 0 || col > 9);
    }
    private boolean isValidRow(int row){
        // Just call isValidColumn since the grid is square
        return this.isValidColumn(row);
    }

    // Should never be needed, but here for good error checking
    private boolean isPlayerInGame(Player player){
        for (Player p : this.players){
            if (p == player){
                return true;
            }
        }

        return false;
    }
}

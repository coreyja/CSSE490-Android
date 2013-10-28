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
    }

    public Player getCurrentPlayer(){
        // Return the head of the queue, as this is the current Player
        return this.players.peek();
    }

    public void endCurrentPlayerTurn() {
        // Pop the next Player off the queue and re-add them so they go to the end
        this.players.add(this.players.poll());
    }


    public int getColumnHeight(int col) throws IndexOutOfBoundsException{
        // Error checking
        if (col < 0 || col > 9){
            // For now throw an exception.
            // Might change this later though if using try/catches for it gets annoying as shit
            throw new IndexOutOfBoundsException();
        }

        // Simply return the number stored in the array
        return this.colHeight[col];
    }

    
}

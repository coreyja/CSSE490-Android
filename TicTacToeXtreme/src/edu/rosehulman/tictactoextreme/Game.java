package edu.rosehulman.tictactoextreme;

import java.util.Arrays;
import java.util.LinkedList;
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

    // Boolean for keeping track of whether the game is won without have to check every time
    private boolean isGameWon = false;

    // Will be MainActivity. Used to pass information needed to update display
    private OnGameChangeListener gameChangeListener;


    // Players will not be added at construct time since Players need Game to be constructed
    public Game(OnGameChangeListener listener){
        // Save the listener
        this.gameChangeListener = listener;

        // Init the grid
        this.grid = new char[9][9];

        // Init height counters
        this.colHeight = new int[9];

        // Init the players queue. Use LinkedList as the implementation of a Queue.
        this.players = new LinkedList<Player>();

        // Most likely inited to 0, but to make sure.
        // Also, Arrays.fill is most likely not anymore efficient than a loop, but could be optimized somehow internally
        Arrays.fill(colHeight, 0);
    }

    // Add a player to the game.
    public void addPlayer(Player p){
        this.players.add(p);
    }

    public Player getCurrentPlayer(){
        // Return the head of the queue, as this is the current Player
        return this.players.peek();
    }

    public boolean isCurrentPlayerHuman(){
        // Returns true if the current player is a human
        return (this.getCurrentPlayer() instanceof HumanPlayer);
    }

    // Pop the next Player off the queue and re-add them so they go to the end
    // Call the needed OnGameChangeListener method
    public void endCurrentPlayerTurn() {

        // If the game is over don't do anything
        if (isGameWon()) return;

        // Pop off the queue
        Player old = this.players.poll();

        // Call game change listener for turn end
        this.gameChangeListener.onPlayerTurnEnd(old);

        // Add back onto the queue for their next turn
        this.players.add(old);

        // Call game change listener for turn start
        this.gameChangeListener.onPlayerTurnStart(this.getCurrentPlayer());
    }

    // Simple getter for entire character grid.
    public char[][] getCharacterGrid(){
        return this.grid;
    }

    // Will return '\u0000' if symbol does not exist in grid
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
            // But if we get here somehow, return don't try to end the current players turn
            return;

        }

        //End this players turn
        this.endCurrentPlayerTurn();

    }

    // This method CAN be used for players who aren't the current player.
    // As such doesn't change the order of the players queue. Must change player order manually if using this method and turns should change.
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

        // Set the row based off the column height and the max columns
        int row = this.grid.length - this.getColumnHeight(col) -1;

        grid[row][col] = symbol;

        // Call GameChangeListener for this cell being updated
        this.gameChangeListener.onCellChange(row, col, symbol);

        // Increment height pointer
        this.incrementColumnHeight(col);

        // Check if someone has won and call the related listener method
        Player winner = this.getWinner();
        if (winner != null){
            this.isGameWon = true;
            this.gameChangeListener.onGameWon(winner);
        }


    }

    // Returns which Player won the game
    // Returns null if no-one won
    public Player getWinner(){
        // TODO: Make this more efficient. This is really bad right now

        // Check for 3 in same row
        for (int i = 0; i < grid.length-2; i++){
            for (int j = 0; j < grid[0].length; j++){
                // '\u0000' is the default value for a java char. If the value is this, there is no real symbol there
                if (this.grid[i][j] != '\u0000' && this.grid[i][j] == this.grid[i+1][j] && this.grid[i][j] == this.grid[i+2][j]){
                    return this.getPlayerFromSymbol(this.grid[i][j]);
                }
            }
        }

        // Check for 3 in same column
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length-2; j++){
                if (this.grid[i][j] != '\u0000' && this.grid[i][j] == this.grid[i][j+1] && this.grid[i][j] == this.grid[i][j+2]){
                    return this.getPlayerFromSymbol(this.grid[i][j]);
                }
            }
        }

        // Check upper left to lower right diagonals
        for (int i = 0; i < grid.length-2; i++){
            for (int j = 0; j < grid[0].length-2; j++){
                if (this.grid[i][j] != '\u0000' && this.grid[i][j] == this.grid[i+1][j+1] && this.grid[i][j] == this.grid[i+2][j+2]){
                    return this.getPlayerFromSymbol(this.grid[i][j]);
                }
            }
        }

        //Check lower left to upper right diagonals
        // Start at lower left corner and go up and to the right
        for (int i = grid.length-1; i >= 2; i--){
            for (int j = 0; j < grid[0].length-2; j++){
                if (this.grid[i][j] != '\u0000' && this.grid[i][j] == this.grid[i-1][j+1] && this.grid[i][j] == this.grid[i-2][j+2]){
                    return this.getPlayerFromSymbol(this.grid[i][j]);
                }
            }
        }

        return null;
    }

    public boolean isGameWon(){
        //Return the boolean that we are storing
        return this.isGameWon;
    }

    /****** Simple Util Methods ******/

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

    // Return the player that matches the symbol given
    // Returns null if the symbol doesn't match any players.
    private Player getPlayerFromSymbol(char symbol){
        for (Player p: this.players){
            if (symbol == p.getSymbol()){
                return p;
            }
        }

        return null;
    }
}

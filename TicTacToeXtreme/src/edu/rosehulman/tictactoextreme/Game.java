package edu.rosehulman.tictactoextreme;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by coreyja on 10/27/13.
 */
public class Game implements Serializable {

    public static final char DEFAULT_CHARACTER = '\u0000';
    public static final char MYSTERY_CHARACTER = '?';

    // Use a queue to keep track of players, and whos turn is next.
    // When a player takes their turn, pop them off the queue and insert them at the end.
    // Current player is on top of the queue
    private LinkedList<Player> players;

    // Char matrix representing the board.
    // Symbols will depend on the Player's symbol attr
    // Will be used as [row][col]
    // [0][0] is the UPPER right corner
    private char[][] grid;

    // The number of spaces in each col that have been used
    // 0 means the col is empty
    private int[] colHeight;

    // Boolean for keeping track of whether the game is won without have to check every time
    private boolean isGameWon = false;

    private Point[] winningCells;

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

        //Creates the mystery buttons in random sections on the board
        for (int i = 0; i < 20; i++){
            Random mystery_button_randomize = new Random();
            int j = mystery_button_randomize.nextInt(9);
            int k = mystery_button_randomize.nextInt(9);

            this.grid[j][k] = MYSTERY_CHARACTER;
        }
    }

    // Add a player to the game.
    public void addPlayer(Player p){
        this.players.add(p);
    }

    // Add a player to the queue at a specific index
    public void addPlayer(Player p, int index){
        this.players.add(index,p);
    }

    public Player getCurrentPlayer(){
        // Return the head of the queue, as this is the current Player
        return this.players.peek();
    }

    public Player[] getPlayers() {
        // Returns an array of all the players
        return this.players.toArray(new Player[0]);
    }

    public boolean isCurrentPlayerHuman(){
        // Returns true if the current player is a human
        return (this.getCurrentPlayer() instanceof HumanPlayer);
    }

    public OnGameChangeListener getGameChangeListener() {
        return gameChangeListener;
    }

    public void setGameChangeListener(OnGameChangeListener gameChangeListener) {
        this.gameChangeListener = gameChangeListener;
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

        // Check if the current player is the same as the run we just removed
        // This will happen when using Two Turn Powerup.
        // If they are the same, don't add the old one to the queue again. If we did this player would always get two turns
        if (old != this.getCurrentPlayer()) {
            // Add back onto the queue for their next turn
            this.players.add(old);
        }

        // Call game change listener for turn start
        this.gameChangeListener.onPlayerTurnStart(this.getCurrentPlayer());

        // Tell the player to take their turn. Mostly for AI players
        this.getCurrentPlayer().takeTurn();
    }

    // Simple getter for entire character grid.
    public char[][] getCharacterGrid(){
        return this.grid;
    }

    // Getter for colHeight. Used by AI to determine moves.
    public int[] getColHeights() {
        return colHeight;
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

    public void setSymbolAtPosition(int row, int col, char sym){
        // Make sure the row and col are both valid
        if (!isValidColumn(col)){
            throw new IndexOutOfBoundsException(String.format("Column %d does not exists", col));
        }
        if (!isValidRow(row)){
            throw new IndexOutOfBoundsException(String.format("Row %d does not exists", row));
        }

        // Set the symbol
        this.grid[row][col] = sym;

        // Tell the GameChangeListener that this cell changed
        this.gameChangeListener.onCellChange(row, col, sym);
    }

    public void deleteSymbolAtPosition(int row, int col){
        char old = this.getSymbolFromGrid(row,col);

        //If the old character matches the default character nothing to do so done.
        if (old == Game.DEFAULT_CHARACTER) return;

        // Set this cell to the default
        this.setSymbolAtPosition(row, col, Game.DEFAULT_CHARACTER);

        // Removed a symbol so decrease the height of the row
        this.colHeight[col]--;

        // Move all the symbols down the column if possible
        this.moveSymbolsDownColumn(row, col);
    }

    private void moveSymbolsDownColumn(int row, int col){
        //Error Checking
        if (!isValidColumn(col)) throw new IndexOutOfBoundsException(String.format("Column %d does not exists", col));

        // Base Case: row < 0. Should never happen
        if (row < 0) return;

        //Base Case: row == 0. Set it to default and be done.
        if (row == 0) { this.setSymbolAtPosition(row, col, Game.DEFAULT_CHARACTER); return; }

        // Base Case: If the character above is the default then we reached the top of the col so set this one then return
        if (this.getSymbolFromGrid(row-1, col) == Game.DEFAULT_CHARACTER){
            this.setSymbolAtPosition(row, col, Game.DEFAULT_CHARACTER);
            return;
        }

        // Set this symbol to the one above it.
        this.setSymbolAtPosition(row, col, this.getSymbolFromGrid(row-1, col));

        // Recurse to the next row up the column
        this.moveSymbolsDownColumn(row-1, col);
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

        // Increment height pointer
        this.incrementColumnHeight(col);

        // If the space we are taking is a Mystery cell, get a random powerup
        if (isMysteryCell(row, col)){
            Random r = new Random();

            Powerup powerup = null;

            int rand = r.nextInt(3); // 3 different powerups
            switch (rand){
                case 0:
                    powerup = new BombPowerup(this, p);
                    break;
                case 1:
                    powerup = new SwapPowerUp(this, p);
                    break;
                case 2:
                    powerup = new TwoTurnsPowerup(this, p);
                    break;
            }

            p.addPowerup(powerup);

            this.gameChangeListener.onGameNotification(R.string.powerup_toast, p.getName(), powerup.getName());
        }

        // Set the symbol to that of this player
        this.setSymbolAtPosition(row, col, symbol);


        //Check if there is a winner from this play
        this.checkForWinner();

    }

    public boolean isMysteryCell(int row, int col){
        // Make sure the row and col are both valid
        if (!isValidColumn(col)){
            throw new IndexOutOfBoundsException(String.format("Column %d does not exists", col));
        }
        if (!isValidRow(row)){
            throw new IndexOutOfBoundsException(String.format("Row %d does not exists", row));
        }

        return this.grid[row][col] == MYSTERY_CHARACTER;
    }

    public void checkForWinner() {
        // Check if someone has won and call the related listener method. Also set the boolean for faster lookup later
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

        winningCells = new Point[3];

        // Check for 3 in same row
        for (int i = 0; i < grid.length-2; i++){
            for (int j = 0; j < grid[0].length; j++){
                // '\u0000' is the default value for a java char. If the value is this, there is no real symbol there.
                // Same idea applies to the Mystery character '?'. It isn't a player
                if (this.grid[i][j] == DEFAULT_CHARACTER || this.grid[i][j] == MYSTERY_CHARACTER){
                    continue;
                }

                if (this.grid[i][j] == this.grid[i+1][j] && this.grid[i][j] == this.grid[i+2][j]){
                    winningCells[0] = new Point(i,j);
                    winningCells[1] = new Point(i+1,j);
                    winningCells[2] = new Point(i+2,j);
                    return this.getPlayerFromSymbol(this.grid[i][j]);
                }
            }
        }

        // Check for 3 in same column
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length-2; j++){
                // '\u0000' is the default value for a java char. If the value is this, there is no real symbol there.
                // Same idea applies to the Mystery character '?'. It isn't a player
                if (this.grid[i][j] == DEFAULT_CHARACTER || this.grid[i][j] == MYSTERY_CHARACTER){
                    continue;
                }

                if (this.grid[i][j] == this.grid[i][j+1] && this.grid[i][j] == this.grid[i][j+2]){
                    winningCells[0] = new Point(i,j);
                    winningCells[1] = new Point(i,j+1);
                    winningCells[2] = new Point(i,j+2);
                    return this.getPlayerFromSymbol(this.grid[i][j]);
                }
            }
        }

        // Check upper left to lower right diagonals
        for (int i = 0; i < grid.length-2; i++){
            for (int j = 0; j < grid[0].length-2; j++){
                if (this.grid[i][j] == this.grid[i+1][j+1] && this.grid[i][j] == this.grid[i+2][j+2]){
                    winningCells[0] = new Point(i,j);
                    winningCells[1] = new Point(i+1,j+1);
                    winningCells[2] = new Point(i+2,j+2);
                    return this.getPlayerFromSymbol(this.grid[i][j]);
                }
            }
        }

        //Check lower left to upper right diagonals
        // Start at lower left corner and go up and to the right
        for (int i = grid.length-1; i >= 2; i--){
            for (int j = 0; j < grid[0].length-2; j++){
                // '\u0000' is the default value for a java char. If the value is this, there is no real symbol there.
                // Same idea applies to the Mystery character '?'. It isn't a player
                if (this.grid[i][j] == DEFAULT_CHARACTER || this.grid[i][j] == MYSTERY_CHARACTER){
                    continue;
                }

                if (this.grid[i][j] == this.grid[i-1][j+1] && this.grid[i][j] == this.grid[i-2][j+2]){
                    winningCells[0] = new Point(i,j);
                    winningCells[1] = new Point(i-1,j+1);
                    winningCells[2] = new Point(i-2,j+2);
                    return this.getPlayerFromSymbol(this.grid[i][j]);
                }
            }
        }

        winningCells = null;
        return null;
    }

    public boolean isGameWon(){
        //Return the boolean that we are storing
        return this.isGameWon;
    }

    public Point[] getWinningCells() {
        return this.winningCells;
    }

    /****** Simple Util Methods ******/

    public boolean isValidColumn(int col){
        //Return true if col is (0,9). False otherwise

        return !(col < 0 || col >= 9);
    }
    public boolean isValidRow(int row){
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
    public boolean isOccupied(int row, int col){
    	if(this.grid[row][col] =='\u0000'){
    		return false;
    	} else {
    		return true;
    	}
    }

    /*** Serialization ***/

    // When you serialize Game, gameChangeListener must be null so it doesn't attempt to Serialize MainActivity
    // So save the listener first then reset it after the serialization.
    public static byte[] serialize(Game g) throws IOException {
        OnGameChangeListener listen = g.gameChangeListener;
        g.gameChangeListener = null;

        byte[] data = Serializer.serialize(g);

        g.gameChangeListener = listen;

        return data;
    }

    // Deserializing is easy, just do it.
    public static Game deserialize(byte[] data) throws IOException, ClassNotFoundException {

        return (Game)Serializer.deserialize(data);
    }

}

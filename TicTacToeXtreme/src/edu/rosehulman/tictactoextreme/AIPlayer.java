package edu.rosehulman.tictactoextreme;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by coreyja on 10/28/13.
 */
public class AIPlayer extends Player{

    public static final String TYPE = "AI";

    // Use this as a probability for randomizing things for the AI.
    // Default to 0.5 for now. Will decide later what is should be.
    // TODO: Figure out what the difficulty levels should be (set final static doubles for them), and set default to medium
    private double difficulty = 0.5;

    // Define constructors. Nothing special needed for now so just call super
    public AIPlayer(Game g){ super(g); }
    public AIPlayer(Game g, String name){ super(g,name); }
    public AIPlayer(Game g, String name, char sym){ super(g,name,sym); }
    public AIPlayer(Game g, String name, char sym, double difficulty){
        super(g,name,sym);

        // Save the difficulty for the AI
        this.difficulty = difficulty;
    }

    @Override
    public String getType() {
        return this.TYPE;
    }

    @Override
    public void takeTurn() {
        // Create a Random object for random number generation below
        Random r = new Random();

        // Get and store the char grid and colHeights so we can use it later without asking for it every time
        char[][] grid = this.game.getCharacterGrid();
        int[] colHeights = this.game.getColHeights();

        // Get an array of all possible columns you can play in.
        int[] possCols = this.getPossibleColumns(colHeights);

        // Random chance based on difficulty of just playing a random move
        if (r.nextDouble() > difficulty) {
            int colToPlay = Utils.chooseRandomIntArrayValue(possCols);

            try {
                game.currentPlayerPlayInColumn(colToPlay);
            } catch (NoPlayersInGameException e){
                // How is an AI taking a turn if there are no players in the game??
                return;
            }

            // The AI took a turn so return
            return;
        }

        // Score all the possible plays, using a scoring algorithm to decide what the score is
        int[] colScores = new int[possCols.length];

        int maxScore = 0; //Setting max to 0 initially is fine, cause all scores are positive
        for (int i = 0; i < possCols.length; i++){
            // The col is whatever the column we are at is. And the row is calculated based on the height of the col
            int col = possCols[i];
            int row = grid.length-colHeights[col]-1;
            colScores[i] = this.getScoreForMoveAtPosition(grid, row, col);

            maxScore = Math.max(maxScore, colScores[i]);
        }

        // Get an array of all the best possible moves
        ArrayList<Integer> bestScoredCols = new ArrayList<Integer>();
        for (int i = 0; i < colScores.length; i++){
            if (colScores[i] == maxScore){
                bestScoredCols.add(possCols[i]);
            }
        }


        // Pick a random column from the best ones and use it
        int colToUse = Utils.chooseRandomArrayListValue(bestScoredCols).intValue();


        try {
            game.currentPlayerPlayInColumn(colToUse);
        } catch (NoPlayersInGameException e){
            // How is an AI taking a turn if there are no players in the game??
            return;
        }


    }



    @Override
    public void getPowerupPosition(Powerup powerup) {
        // TODO Add random powerup position picking
    }

    /* Private helper methods that the AI uses to calculate what to do */

    private int[] getPossibleColumns(int[] colHeights){
        ArrayList<Integer> possible = new ArrayList<Integer>();

        // If the height is less than 9 we can add to that column
        for (int i = 0; i < colHeights.length; i++){
            if (colHeights[i] < 9){
                possible.add(i);
            }
        }

        //Convert ArrayList<Integer> to int[]
        int[] toReturn = new int[possible.size()];
        for (int i = 0; i < toReturn.length; i++){
            toReturn[i] = possible.get(i).intValue();
        }

        return toReturn;
    }


    /*
    This is a scoring algorithm that the AI will use to judge what is and what isn't a good move.
    Higher scores are better, and these moves will be preferred.

    Right now, this just looks at the number of symbols that forms lines with it.
    And does it really inefficiently, now that I think about what it is actually doing...
    TODO: Make more efficient or replace altogether
     */
    private int getScoreForMoveAtPosition(char[][] grid, int row, int col){
        int score = 0;

        Player[] players = game.getPlayers();

        //Loop over the 8 directions and find the number of symbols in a line, for each player, in that direction.
        for (int dir = 0; dir < 8; dir++){

            // Loop over all the players
            for (Player player: players){
                int num = this.numberInLineInDirection(grid, player.getSymbol(), row, col, dir);

                if (num == 2 && player == this){
                    // This move is a winning move, so score it really really high.
                    return 999999;
                }

                if (num == 2){
                    return 9999; // If there are two in a row, it's important to block an opponent.
                }

                if (player == this){
                    score += num;
                }

            }

        }

        return score;
    }

    // Returns the number of the given symbol that are in a line in the given direction.
    // Doesn't count or check the position passed in, just uses this to know where to move.
    private int numberInLineInDirection(char[][] grid, char sym, int row, int col, int direction) {
        // Direction: 0=North, 1=North-East, 2=East, 3=South-East, 4=South, 5=South-West, 6=West, 7=North-West
        // Mod direction by 8 to standardize it to one of our directions
        direction %= 8;

        // Set to -1 initially so the do while resets it to one on the first loop
        int numInLine = -1;

        // Use do-while, to move first then check
        do {
            numInLine++;

            // Move in whatever direction to check that symbol.
            switch (direction){

                case 0:
                    row--;
                    break;
                case 1:
                    row--;
                    col++;
                    break;
                case 2:
                    col++;
                    break;
                case 3:
                    row++;
                    col++;
                    break;
                case 4:
                    row++;
                    break;
                case 5:
                    row++;
                    col--;
                    break;
                case 6:
                    col--;
                    break;
                case 7:
                    row--;
                    col--;
                    break;
            }

        } while (game.isValidColumn(col) && game.isValidRow(row) && grid[row][col] == sym);


        return numInLine;

    }


}

package edu.rosehulman.tictactoextreme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnGameChangeListener {


    public static final String TAG = "TTTX"; //TTTX for Tic Tac Toe Xtreme


    private GridLayout gridContainer;
    private LinearLayout columnButtonContainer;

    //Holds the dynamically generated TextViews for displaying game
    private TextView[][] textViewGrid;

    // Holds the dynamically generated buttons for Player choosing move
    private Button[] columnButtons;

    // Game object
    private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        // Find the Grid Container
        gridContainer = (GridLayout)findViewById(R.id.container_grid);

        // Find the Column Button's Container
        columnButtonContainer = (LinearLayout) findViewById(R.id.buttonLinearLayout);

        // Convert 20 dpi to pixels to use for sizing the TextViews and Buttons
        int dimensions = (int) Utils.convertDPItoPX(20, getResources().getDisplayMetrics());

        // Init the TextView matrix
        textViewGrid = new TextView[9][9];
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                TextView temp = new TextView(this);

                // Set styles for text views
                temp.setBackgroundColor(getResources().getColor(R.color.gridTextViewBackground));

                // Set the width and the height. Width then height.
                temp.setLayoutParams(new ViewGroup.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, dimensions));

                //Center the TextField in it's cell
                temp.setGravity(Gravity.CENTER);

                temp.setText("X");

                textViewGrid[i][j] = temp;
                gridContainer.addView(temp);
            }
        }

        //Init the column buttons
        columnButtons = new Button[9];
        for (int i = 0; i < 9; i++){
            Button temp = new Button(this);

            // Set styles for the Buttons
            temp.setWidth(dimensions);
            temp.setHeight(dimensions);

            //Temporary set text to something
            temp.setText("G");

            temp.setOnClickListener(this);

            columnButtons[i] = temp;
            gridContainer.addView(temp);
        }

        // TODO: Change below to add a way to ask user for number of players
        game = new Game(this);
        game.addPlayer(new HumanPlayer(game, "Player 1", 'X'));
        game.addPlayer(new HumanPlayer(game, "Player 2", 'O'));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
        /** Return as soon as you find the view and perform necessary actions **/

        // Switch over all the views that have ids
        switch (v.getId()){
            //None exist yet
        }

        // Check if it was one of the column buttons. But only if the current player is human
        // If current player isn't human, we can ignore any clicks on these buttons
        if (game.currentPlayerIsHuman()){
            for (int i = 0; i < columnButtons.length; i++){
                if ( ((Button)v) == columnButtons[i] ){
                    try {
                        // If the current player is human, and we clicked the button make the move.
                        // This will end the players turn.
                        this.game.currentPlayerPlayInColumn(i);

                    } catch (NoPlayersInGameException e) {
                        // TODO: Add better error handling
                        // Print a log error message
                        Log.e(TAG, String.format("Click columnButton with index = %d", i), e);
                    }

                    // Regardless of whether an exception was thrown return since this is the button that was clicked
                    return;
                }
            }
        }



	}


    @Override
    public void onCellChange(int row, int col, char newSymbol) {
        // Set the referenced cell to the new symbol
        this.textViewGrid[row][col].setText(newSymbol);

    }

    @Override
    public void onPlayerTurnStart(Player p) {
        // TODO: Add player indicator to view and update it here

    }

    @Override
    public void onPlayerTurnEnd(Player p) {
        //Nothing needs to be updated so leave empty.
    }

    @Override
    public void onGameWon(Player winner) {
        // TODO: Implement something here for telling when a game is won
    }
}

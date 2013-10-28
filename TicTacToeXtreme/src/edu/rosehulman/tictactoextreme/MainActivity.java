package edu.rosehulman.tictactoextreme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnGameChangeListener {


    public static final String TAG = "TTTX"; //TTTX for Tic Tac Toe Xtreme

    private TableLayout tableLayout;

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

        // Find the table
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        // Init the TextView matrix
        textViewGrid = new TextView[9][9];
        for (int i = 0; i < 9; i++){

            // Create a new TableRow and add it to the table
            TableRow curRow = new TableRow(this);
            tableLayout.addView(curRow);

            for (int j = 0; j < 9; j++){
                TextView temp = new TextView(this);

                // Set styles for text views
                temp.setBackgroundColor(getResources().getColor(R.color.gridTextViewBackground));

                //Center the TextField in it's cell
                temp.setGravity(Gravity.CENTER);

                textViewGrid[i][j] = temp;
                curRow.addView(temp);
            }
        }

        //Init the column buttons
        columnButtons = new Button[9];

        TableRow buttonRow = new TableRow(this);
        tableLayout.addView(buttonRow);
        for (int i = 0; i < 9; i++){
            Button temp = new Button(this);

            //Temporary set text to something
            temp.setText("G");

            temp.setOnClickListener(this);

            columnButtons[i] = temp;
            buttonRow.addView(temp);
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
                        // TODO: Do something more intelligent on error
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
        this.textViewGrid[row][col].setText(String.valueOf(newSymbol));
    }

    @Override
    public void onPlayerTurnStart(Player p) {
        // TODO: Add player indicator to view and update it here

        String message = String.format("%s\'s Turn!", p.getName());
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayerTurnEnd(Player p) {
        //Nothing needs to be updated so leave empty.
    }

    @Override
    public void onGameWon(Player winner) {
        // TODO: Actually do something. For now just log and toast and disable buttons
        String winnerName = winner.getName();
        String result = String.format("Player %s won!", winnerName);
        Log.d(TAG, result);

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();

        for (Button b : columnButtons){
            b.setEnabled(false);
        }
    }
}

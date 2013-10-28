package edu.rosehulman.tictactoextreme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener, OnGameChangeListener {


    public static final String TAG = "TTTX"; //TTTX for Tic Tac Toe Xtreme

    private TableLayout tableLayout;

    //Holds the dynamically generated TextViews for displaying game
    private TextView[][] textViewGrid;

    // Holds the dynamically generated buttons for Player choosing move
    private ImageButton[] columnButtons;

    private TextView playerStatusText;
    private LinearLayout powerupContainer;

    private Powerup powerupWaitingForPosition = null;

    // Game object
    private Game game;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        // TODO: Change below to add a way to ask user for number of players
        game = new Game(this);
        Player a = new HumanPlayer(game, "Player 1", 'X');
        Player b = new HumanPlayer(game, "Player 2", 'O');
        game.addPlayer(a);
        game.addPlayer(b);

        a.addPowerup(new TwoTurnsPowerup(game, a));
        b.addPowerup(new TwoTurnsPowerup(game, b));

        a.addPowerup(new BombPowerup(game, a));
        b.addPowerup(new BombPowerup(game, b));

        // Find the table
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        // Find powerupContainer
        powerupContainer = (LinearLayout) findViewById(R.id.powerup_container);

        // Find player status text view and update it to be what it should start as
        playerStatusText = (TextView) findViewById(R.id.player_status_text);
        this.updatePlayerStatus();



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

                // Set this as the onClickListener. Used for picking powerup position
                temp.setOnClickListener(this);

                textViewGrid[i][j] = temp;
                curRow.addView(temp);
            }
        }

        //Init the column buttons
        columnButtons = new ImageButton[9];

        TableRow buttonRow = new TableRow(this);
        tableLayout.addView(buttonRow);
        for (int i = 0; i < 9; i++){
            ImageButton temp = new ImageButton(this);

            // Set the image for the buttons
            temp.setImageResource(R.drawable.column_button);
            // Set the button background to transparent so you don't see the button under the image
            temp.setBackgroundColor(getResources().getColor(R.color.transparent));

            temp.setOnClickListener(this);

            columnButtons[i] = temp;
            buttonRow.addView(temp);
        }


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_new_game:
                //TODO Implement new game functionality
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onClick(View v) {
        /** Return as soon as you find the view and perform necessary actions **/

        // Switch over all the views that have ids
        switch (v.getId()){

        }

        // Check if it was one of the column buttons. But only if the current player is human and the clicked view is an ImageButton
        // If current player isn't human, we can ignore any clicks on these buttons
        if (game.isCurrentPlayerHuman() && v instanceof ImageButton){
            for (int i = 0; i < columnButtons.length; i++){
                if ( ((ImageButton)v) == columnButtons[i] ){
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

        // Only care about clicking textViews if a powerup is waiting for a position
        if (this.powerupWaitingForPosition != null){
            for (int i = 0; i < 9; i++){
                for (int j = 0; j < 9; j++){
                    if ( ((TextView) v) == this.textViewGrid[i][j]){
                        // If this TextView was clicked on, tell the power up it's location
                        this.powerupWaitingForPosition.usePowerup(i,j);

                        // Set to null because no powerup is currently waiting for a position
                        this.powerupWaitingForPosition = null;
                        return;
                    }
                }
            }
        }

	}


    @Override
    public void onCellChange(int row, int col, char newSymbol) {
        // TODO: Add animation of symbol falling down col here.
        // Note: May not be able to add this animtion here since this is called on each update.
        // For example when using a bomb, symbols are deleted and this is called multiple times

        // Set the referenced cell to the new symbol
        this.textViewGrid[row][col].setText(String.valueOf(newSymbol));
    }

    @Override
    public void onPlayerTurnStart(Player p) {
        // Just call the method to update the Player Status text
        this.updatePlayerStatus();
    }

    @Override
    public void onPlayerTurnEnd(Player p) {
        //Nothing needs to be updated so leave empty.
    }

    @Override
    public void onGameWon(Player winner) {
        // TODO: Actually do something more helpful. Gradually getting better. Probably good right now
        String winnerName = winner.getName();
        String result = String.format("%s won!", winnerName);
        Log.d(TAG, result);

        // Disable all the buttons
        for (ImageButton b : columnButtons){
            b.setEnabled(false);
        }

        // Update the player status text since the game is won
        this.updatePlayerStatus();
    }

    @Override
    public void onPowerupWaitingForPosition(Powerup powerup) {
        // Set powerup to the one waiting for a position.
        // Will clear it once a position is received
        this.powerupWaitingForPosition = powerup;
    }

    private void updatePlayerStatus(){
        String message;
        Player cur = game.getCurrentPlayer();

        if (game.isGameWon()){
            Player winner = this.game.getWinner();
            message = String.format("%s won!", winner.getName());
        } else {
            message = String.format("%s\'s Turn!", cur.getName());
        }

        this.playerStatusText.setText(message);

        // Empty the layout so we can fill it with the powerups of the current player
        powerupContainer.removeAllViewsInLayout();

        for (Powerup p:cur.getPowerups()){
            // Create a PowerupButton for each powerup and add them to the view
            PowerupButton temp = new PowerupButton(this);

            temp.setText(p.getStringType());
            temp.setPowerup(p);

            // OnClick just uses the powerup associated with the button and disables it's self.
            temp.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PowerupButton) v).getPowerup().usePowerup();
                    v.setEnabled(false);
                }
            });

            powerupContainer.addView(temp);

        }
    }
}

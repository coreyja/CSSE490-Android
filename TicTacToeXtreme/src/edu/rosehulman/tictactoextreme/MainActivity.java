package edu.rosehulman.tictactoextreme;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

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
                // Open the new game dialog to select num and type of players
                this.openNewGameDialog();

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
        // Note: May not be able to add this animation here since this is called on each update.
        // For example when using a bomb, symbols are deleted and this is called multiple times

        // Set the referenced cell to the new symbol
        this.textViewGrid[row][col].setText(String.valueOf(newSymbol));

        //After any cell changes update the column buttons
        this.updateColumnButtons();
    }

    @Override
    public void onPlayerTurnStart(Player p) {
        // Just call the method to update the Player Status text
        this.updatePlayerStatus();
    }

    @Override
    public void onPlayerTurnEnd(Player p) {
        // Nothing needs to be done here yet
    }

    @Override
    public void onGameWon(Player winner) {
        // TODO: Actually do something more helpful. Gradually getting better. Probably good right now
        String winnerName = winner.getName();
        String result = String.format("%s won!", winnerName);

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

    private void refreshGrid() {
        char[][] charGrid = this.game.getCharacterGrid();

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                this.textViewGrid[i][j].setText(String.valueOf(charGrid[i][j]));
            }
        }
    }

    private void updateColumnButtons(){
        int[] heights = this.game.getColHeights();

        for (int i = 0; i < 9; i++){
            if (heights[i] < 9){
                columnButtons[i].setEnabled(true);
            } else {
                columnButtons[i].setEnabled(false);
            }
        }
    }

    private void openNewGameDialog() {
        new NewGameDialog().show(getFragmentManager(), TAG);
    }

    private void initNewGame() {
        //Do all the things needed when we create a new game

        this.refreshGrid();
        this.updatePlayerStatus();
        this.updateColumnButtons();
        this.game.getCurrentPlayer().takeTurn();
    }

    private class NewGameDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();

            // Inflate my custom dialog layout
            final View new_game_dialog_view = inflater.inflate(R.layout.dialog_new_game, null);

            builder.setView(new_game_dialog_view);

            builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // On cancel just dismiss the dialog
                    dialog.dismiss();
                }
            });

            // This is long, creates new Game from player info that exists
            builder.setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    LinearLayout playerContainer = (LinearLayout) new_game_dialog_view.findViewById(R.id.new_players_container);

                    // Create a new game with MainActivity as the listener
                    MainActivity.this.game = new Game(MainActivity.this);

                    // Loop over all the children of playerContainer
                    for (int i = 0; i < playerContainer.getChildCount(); i++){
                        View player = playerContainer.getChildAt(i);

                        // Get player name and symbol
                        String name = ((EditText)player.findViewById(R.id.player_name_field)).getText().toString();
                        String symbolString = ((EditText)player.findViewById(R.id.player_symbol_field)).getText().toString();


                        char symbol;
                        if (symbolString.length() > 0){
                            symbol = symbolString.charAt(0);
                        } else {
                            // Default to X
                            symbol = 'X';
                        }

                        // Default to Player # if name not provided
                        if (name.length() == 0){
                            name = String.format("Player %d", i+1);
                        }

                        boolean isComputer = ((ToggleButton)player.findViewById(R.id.player_human_toggle)).isChecked();

                        // Create player based on whether they are human or not
                        if (!isComputer){
                            // Not a computer so create the human player
                            game.addPlayer(new HumanPlayer(game, name, symbol));
                        } else {
                            // Is a computer, so create an AI Player
                            game.addPlayer(new AIPlayer(game, name, symbol));
                        }
                    }

                    // After creating new Game with new players refresh the Grid and the player status. All in one func now
                    MainActivity.this.initNewGame();

                    // At the end dismiss the dialog
                    dialog.dismiss();
                }
            });

            // Create On Click Listener for the Add Player Button
            ((Button) new_game_dialog_view.findViewById(R.id.add_new_player_button)).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final LinearLayout playerContainer = (LinearLayout) new_game_dialog_view.findViewById(R.id.new_players_container);

                    // Can only add 3 players
                    if (playerContainer.getChildCount() < 3){
                        // Inflate the view with the player info fields and add that to the playerContainer
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View newPlayer = inflater.inflate(R.layout.new_player_info, null);

                        // Set onClickListener for delete button of each player
                        ((Button) newPlayer.findViewById(R.id.delete_player_button)).setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Remove this players info section from the container
                                playerContainer.removeView((View)v.getParent());

                                // Since we removed a player, there must be less than 3 so another can be added
                                ((Button) new_game_dialog_view.findViewById(R.id.add_new_player_button)).setEnabled(true);
                            }
                        });

                        // Add the new player view to the container
                        playerContainer.addView(newPlayer);
                    }

                    if (playerContainer.getChildCount() == 3) {
                        // If there are now 3 players disable this button
                        v.setEnabled(false);
                    }

                }
            });

            return builder.create();
        }
    }
}



package edu.rosehulman.tictactoextreme;

import android.content.Context;

public class ReversalPowerUp extends Powerup {

	public ReversalPowerUp(Game game, Player p) {
		super(game, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void usePowerup() {
		// Tell player that this powerup needs a position to use.
		this.player.getPowerupPosition(this);

	}

	@Override
	public void usePowerup(int row, int col) {
		// Player has told us where to use the powerup

		// Makes the reversal of the buttons
		if (!this.game.isOccupied(row, col)) {
			// do nothing, but save power-up
		} else if (!this.game.isOccupied(row, col + 1)
				&& this.game.isValidColumn(col + 1)) {
			this.game.setSymbolAtPosition(row, col + 1,
					this.game.getSymbolFromGrid(row, col));
			this.game.setSymbolAtPosition(row, col,
					this.game.getCurrentPlayer().symbol);
		} else if (this.game.isOccupied(row, col + 1) && this.game.isValidColumn(col + 1)){
			char saved_symbol = this.game.getSymbolFromGrid(row, col);
			this.game.setSymbolAtPosition(row, col, this.game.getSymbolFromGrid(row, col + 1));
			this.game.setSymbolAtPosition(row, col + 1, saved_symbol);
		}

		// Check for winner since symbols falling could cause someone to win
		this.game.checkForWinner();

	}

	@Override
	public String getStringType() {
		// TODO Auto-generated method stub
		return "Reversal";
	}

}

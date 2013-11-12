package edu.rosehulman.tictactoextreme;

public class SwapPowerUp extends Powerup {

	public SwapPowerUp(Game game, Player p) {
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

        if (!this.game.isOccupied(row, col) || !this.game.isValidRow(row-1) || !this.game.isOccupied(row-1, col)) {
            // 3 Cases where it isn't used, and wastes the powerup
            // - Cell picked is empty
            // - Cell above doesn't exists
            // - Cell above is empty
            return;
        }


        // If the cell is occupied as is the one above, simply switch the symbols.
        char temp = this.game.getSymbolFromGrid(row, col);
        this.game.setSymbolAtPosition(row, col, this.game.getSymbolFromGrid(row-1, col));
        this.game.setSymbolAtPosition(row - 1, col, temp);

        // Remove this powerup from the player
        this.player.removePowerup(this);


		// Check for winner since symbols falling could cause someone to win
		this.game.checkForWinner();

	}

	@Override
	public String getStringType() {
		// TODO Auto-generated method stub
		return "Swap";
	}

    @Override
    public String getName() {
        return "Swap Powerup";
    }

}

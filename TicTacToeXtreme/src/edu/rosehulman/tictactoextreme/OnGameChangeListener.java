package edu.rosehulman.tictactoextreme;

/**
 * MainActivity will implement OnGameChageListener
 * Game will call methods from this to update display when things happen
 *
 * Comments will be provided when the function name isn't specific enough.
 */
public interface OnGameChangeListener {


    public void onCellChange(int row, int col, char newSymbol);

    // May not both do something but never know so have both exist
    public void onPlayerTurnStart(Player p);
    public void onPlayerTurnEnd(Player p);

    public void onGameWon(Player winner);
}

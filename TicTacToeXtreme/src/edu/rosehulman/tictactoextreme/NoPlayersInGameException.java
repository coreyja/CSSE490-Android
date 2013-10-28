package edu.rosehulman.tictactoextreme;

/**
 * I can't imagine a time when this exception will actually be thrown, but just in case and for good code style
 */
public class NoPlayersInGameException extends Exception {

    public NoPlayersInGameException(String details){
        super(details);
    }

    public NoPlayersInGameException(){
        super();
    }
}

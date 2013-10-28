package edu.rosehulman.tictactoextreme;

/**
 * Created by coreyja on 10/28/13.
 */
public class PlayerNotInGameException extends Exception {

    public PlayerNotInGameException(){
        super();
    }

    public PlayerNotInGameException(String s){
        super(s);
    }
}

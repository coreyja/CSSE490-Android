package edu.rosehulman.tictactoextreme;

import android.nfc.NdefRecord;
import android.util.Log;

import org.ndeftools.externaltype.ExternalTypeRecord;

/**
 * Created by coreyja on 11/12/13.
 */
public class GameRecord extends ExternalTypeRecord {

    // The game object we are trying to save
    private Game game;

    public GameRecord(Game g) {
        this.game = g;
    }

    @Override
    public String getDomain() {
        return "rose-hulman.edu";
    }

    @Override
    public String getType() {
        return "GameRecord";
    }

    public Game getGame() {
        return this.game;
    }

    @Override
    public byte[] getData() {
        byte[] data = new byte[0];

        try {
            data = Game.serialize(this.game);

        } catch (Exception e) {
            Log.d(MainActivity.TAG,"WELL FUCK, Serialize Failed");;
        }

        return data;
    }

    public static GameRecord parse(NdefRecord ndefRecord){
        ExternalTypeRecord generic = ExternalTypeRecord.parse(ndefRecord);


        Game g = null;
        try {
            g = Game.deserialize(generic.getData());
        } catch (Exception e){
            Log.d(MainActivity.TAG,"WELL SHIT, DeSerialize Failed");
        }

        return new GameRecord(g);
    }

}

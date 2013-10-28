package edu.rosehulman.tictactoextreme;

import android.content.Context;
import android.widget.Button;

/**
 * Created by coreyja on 10/28/13.
 */
public class PowerupButton extends Button {

    private Powerup powerup;

    public PowerupButton(Context c) { super (c); }

    public Powerup getPowerup() {
        return powerup;
    }

    public void setPowerup(Powerup powerup) {
        this.powerup = powerup;
    }
}

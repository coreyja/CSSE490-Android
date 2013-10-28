package edu.rosehulman.tictactoextreme;

import android.content.Context;
import android.widget.ImageButton;

/**
 * Created by coreyja on 10/28/13.
 */
public class PowerupButton extends ImageButton {

    private Powerup powerup;

    public PowerupButton(Context c) { super (c); }

    public Powerup getPowerup() {
        return powerup;
    }

    public void setPowerup(Powerup powerup) {
        this.powerup = powerup;

        String type = powerup.getStringType();

        if (type == "Bomb"){
            this.setImageResource(R.drawable.bomb_powerup);
        }
        if (type == "2X") {
            this.setImageResource(R.drawable.twox_powerup);
        }
    }
}

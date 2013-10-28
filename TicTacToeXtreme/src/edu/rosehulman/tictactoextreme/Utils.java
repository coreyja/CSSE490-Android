package edu.rosehulman.tictactoextreme;

import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * This class will contain static util methods that can be used anywhere.
 */
public class Utils {

    public static float convertDPItoPX(float dpi, DisplayMetrics dm){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, dm);
    }
}

package edu.rosehulman.tictactoextreme;

import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class will contain static util methods that can be used anywhere.
 */
public class Utils {

    public static float convertDPItoPX(float dpi, DisplayMetrics dm){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, dm);
    }


    // Given an ArrayList choose a random element and return it
    public static <T> T chooseRandomArrayListValue(ArrayList<T> list){
        // One a null list return null
        if (list.isEmpty()) return null;

        Random r = new Random();

        return list.get(r.nextInt(list.size()));
    }

    // Given an Array choose a random element and return it
    public static <T> T chooseRandomArrayValue(T[] array){
        if (array.length == 0) return null;

        Random r = new Random();
        return array[r.nextInt(array.length)];
    }

    // Given an Array choose a random element and return it
    public static int chooseRandomIntArrayValue(int[] array){
        if (array.length == 0) return -1;

        Random r = new Random();
        return array[r.nextInt(array.length)];
    }
}

package com.example.alex.helppeopletogether.SupportClasses;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by Alex on 01.06.2016.
 */
public class Dimensions {
    public int getWidth(Context context) {
        Display display =  ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }
}

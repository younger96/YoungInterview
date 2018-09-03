package com.example.chenqiuyang.younginterview.packing.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.chenqiuyang.younginterview.packing.app.App;


/**
 * Created by young on 18-3-22.
 */

public class DimenUtil {

    public static int getScreenWidth(){
        final Resources resources = App.getApplicationInstance().getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.widthPixels;
    }


    public static int getScreenHeight(){
        final Resources resources = App.getApplicationInstance().getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}

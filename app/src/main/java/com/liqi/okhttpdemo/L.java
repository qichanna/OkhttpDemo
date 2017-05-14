package com.liqi.okhttpdemo;

import android.util.Log;

/**
 * Created by liqi on 2017/5/14.
 */

public class L {

    private static final String TAG = "liqi_okhttp";
    private static boolean debug = true;

    public static void e(String msg){
        if(debug)
            Log.e(TAG,msg);
    }
}

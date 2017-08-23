package com.wuzhou.wlibrary.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wfy 2017/6/28 10:51.
 */

public class WToast {
    public static void show(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}

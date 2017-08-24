package com.wuzhou.wlibrary.utils;

import android.util.Log;

/**
 * Created by wfy 2017/6/28 10:42.
 */

public class WLog {
    public static boolean isDebug=true;
    public static void print(String tag,String log){
        if(isDebug){
            Log.e(tag,log);
        }
    }
    public static void print(String log){
        if(isDebug){
            Log.e(WLog.class.getSimpleName(),log);
        }
    }

    public static void printe(String tag,String log){
        if(isDebug){
            Log.e(tag,log);
        }
    }
    public static void printe(String log){
        if(isDebug){
            Log.e(WLog.class.getSimpleName(),log);
        }
    }

    public static void printv(String tag,String log){
        if(isDebug){
            Log.v(tag,log);
        }
    }
    public static void printv(String log){
        if(isDebug){
            Log.v(WLog.class.getSimpleName(),log);
        }
    }
    public static void printd(String tag,String log){
        if(isDebug){
            Log.d(tag,log);
        }
    }

    public static void printd(String log){
        if(isDebug){
            Log.d(WLog.class.getSimpleName(),log);
        }
    }
    public static void printi(String tag,String log){
        if(isDebug){
            Log.i(tag,log);
        }
    }
    public static void printi(String log){
        if(isDebug){
            Log.i(WLog.class.getSimpleName(),log);
        }
    }

    public static void printw(String tag,String log){
        if(isDebug){
            Log.w(tag,log);
        }
    }
    public static void printw(String log){
        if(isDebug){
            Log.w(WLog.class.getSimpleName(),log);
        }
    }
}

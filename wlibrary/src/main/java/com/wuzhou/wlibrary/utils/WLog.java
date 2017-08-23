package com.wuzhou.wlibrary.utils;

import android.util.Log;

import com.wuzhou.wlibrary.Config;

/**
 * Created by wfy 2017/6/28 10:42.
 */

public class WLog {
    public static void print(String tag,String log){
        if(Config.isDebug){
            Log.e(tag,log);
        }
    }
    public static void print(String log){
        if(Config.isDebug){
            Log.e(WLog.class.getSimpleName(),log);
        }
    }

    public static void printe(String tag,String log){
        if(Config.isDebug){
            Log.e(tag,log);
        }
    }
    public static void printe(String log){
        if(Config.isDebug){
            Log.e(WLog.class.getSimpleName(),log);
        }
    }

    public static void printv(String tag,String log){
        if(Config.isDebug){
            Log.v(tag,log);
        }
    }
    public static void printv(String log){
        if(Config.isDebug){
            Log.v(WLog.class.getSimpleName(),log);
        }
    }
    public static void printd(String tag,String log){
        if(Config.isDebug){
            Log.d(tag,log);
        }
    }

    public static void printd(String log){
        if(Config.isDebug){
            Log.d(WLog.class.getSimpleName(),log);
        }
    }
    public static void printi(String tag,String log){
        if(Config.isDebug){
            Log.i(tag,log);
        }
    }
    public static void printi(String log){
        if(Config.isDebug){
            Log.i(WLog.class.getSimpleName(),log);
        }
    }

    public static void printw(String tag,String log){
        if(Config.isDebug){
            Log.w(tag,log);
        }
    }
    public static void printw(String log){
        if(Config.isDebug){
            Log.w(WLog.class.getSimpleName(),log);
        }
    }
}

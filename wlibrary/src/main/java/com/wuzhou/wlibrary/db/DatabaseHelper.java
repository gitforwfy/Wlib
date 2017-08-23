package com.wuzhou.wlibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wfy 2017/7/5 11:25.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public interface OnCreateListener {
        void onCreate(SQLiteDatabase database, ConnectionSource connectionSource);
        void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion);
    }
    private static OnCreateListener onCreateListener;

    public static void setDbInfo(String dbName,int dbVersion,OnCreateListener listener) {
        DB_NAME = dbName;
        DB_VERSION = dbVersion;
        onCreateListener = listener;
    }

    /**
     * 数据库名字
     */
    private static String DB_NAME;

    /**
     * 数据库版本
     */
    private static int DB_VERSION;

    private Map<String, Dao> daos = new HashMap<>();

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        if(onCreateListener!=null)onCreateListener.onCreate(database,connectionSource);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if(onCreateListener!=null)onCreateListener.onUpgrade(database,connectionSource,oldVersion,newVersion);
    }

    private static DatabaseHelper instance;

    /** 单例获取该Helper */
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /** 释放资源 */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}

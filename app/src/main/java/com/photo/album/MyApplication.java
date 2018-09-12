package com.photo.album;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.photo.album.bean.Profession;
import com.photo.album.bean.Student;
import com.photo.album.bean.Worker;
import com.wuzhou.wlibrary.db.DatabaseHelper;

import java.sql.SQLException;

import cn.bmob.v3.Bmob;

/**
 * Created by wfy 2017/7/5 10:54.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        Bmob.initialize(this, "991ce92febd7a11d4382f65332e5a376");
        //初始化数据库 数据库名字，版本号，并创建需要的表
        DatabaseHelper.setDbInfo("test", 1, new DatabaseHelper.OnCreateListener() {
            @Override
            public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            try {
                TableUtils.createTable(connectionSource, Worker.class);
                TableUtils.createTable(connectionSource, Profession.class);
                TableUtils.createTable(connectionSource, Student.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            }

            @Override
            public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            try {
                TableUtils.dropTable(connectionSource, Worker.class, true);
                TableUtils.dropTable(connectionSource, Profession.class, true);
                TableUtils.dropTable(connectionSource, Student.class, true);
                onCreate(database, connectionSource);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            }
        });
//        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}

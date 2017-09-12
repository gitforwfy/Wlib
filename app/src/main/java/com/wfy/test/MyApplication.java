package com.wfy.test;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wfy.test.bean.Profession;
import com.wfy.test.bean.Student;
import com.wfy.test.bean.Worker;
import com.wuzhou.wlibrary.db.DatabaseHelper;
import com.wuzhou.wlibrary.http.RetrofitServiceManager;
import com.wuzhou.wlibrary.imageloader.Image;
import com.wuzhou.wlibrary.imageloader.PicassoImageLoader;

import java.sql.SQLException;

/**
 * Created by wfy 2017/7/5 10:54.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化图片缓存框架，传入你想要的图片缓存库
        Image.init(new PicassoImageLoader());


        //初始化网络框架
        //相当于原来config中配置的ip
        RetrofitServiceManager.init(this,"http://apicloud.mob.com/");

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

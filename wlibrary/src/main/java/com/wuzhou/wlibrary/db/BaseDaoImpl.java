package com.wuzhou.wlibrary.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by wfy 2017/7/5 10:54.
 *
 */

public class BaseDaoImpl<T, Integer> extends BaseDao<T, Integer> {

    private Class<T> clazz;
    private Dao<T, Integer> dao;
    public BaseDaoImpl(Context context, Class<T> clazz) {
        super(context);
        // TODO Auto-generated constructor stub
        this.clazz = clazz;
    }

    @Override
    public Dao<T, Integer> getDao() throws SQLException {
        // TODO Auto-generated method stub
        if (null == dao) {
            dao = mDatabaseHelper.getDao(clazz);
        }
        return dao;
    }

}

package com.wuzhou.wlibrary.http.download.download;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
     *  下载数据库的操作类
     *  by jixiang.
     *  date 2016/8/5 17:15.
     */
public class DownloadInfoDao {

    private Dao<DownloadInfo, Integer> dao;

    @SuppressWarnings("unchecked")
    public DownloadInfoDao(Context context) {
        try {
            DatabaseHelper helper = DatabaseHelper.getHelper(context);
            this.dao = helper.getDao(DownloadInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DownloadInfo> queryForAll() {
        try {
            return dao.queryForAll();
//            return dao.
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void create(DownloadInfo downloadInfo) {
        try {
            dao.create(downloadInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String url) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("url", url);
            dao.delete(dao.queryForFieldValues(map));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(DownloadInfo downloadInfo) {
        try {
            dao.update(downloadInfo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.wfy.test.db;


import android.content.Context;

import com.wfy.test.bean.Worker;
import com.wuzhou.wlibrary.db.BaseDaoImpl;

/**
 * Created by wfy 2017/7/6 09:20.
 */

public class WorkDao extends BaseDaoImpl {
    public WorkDao(Context context) {
        super(context, Worker.class);
    }
}

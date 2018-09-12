package com.photo.album.db;


import android.content.Context;

import com.photo.album.bean.Worker;
import com.wuzhou.wlibrary.db.BaseDaoImpl;

/**
 * Created by wfy 2017/7/6 09:20.
 */

public class WorkDao extends BaseDaoImpl {
    public WorkDao(Context context) {
        super(context, Worker.class);
    }

    /**
     * 自定义方法，常用的话 为了方便
     * @param worker
     * @return
     */
    public boolean isExist(Worker worker){
        boolean b;
        if(query("jobNum",worker.jobNum)==null){
            b=false;
        }else{
            b=true;
        }
        return b;
    }

}

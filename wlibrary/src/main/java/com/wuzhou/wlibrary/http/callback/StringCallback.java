package com.wuzhou.wlibrary.http.callback;

import okhttp3.Response;

/**
    *  返回字符串类型的数据
    *  by jixiang.
    *  date 2016/8/5 17:05.
    */
public abstract class StringCallback extends AbsCallback<String> {

   @Override
   public String parseNetworkResponse(Response response) throws Exception {
       return response.body().string();
   }
}

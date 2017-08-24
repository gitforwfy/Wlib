package com.wuzhou.wlibrary.http.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.Response;

/**
    *  返回图片的Bitmap，这里没有进行图片的缩放，可能会发生 OOM
    *  by jixiang.
    *  date 2016/8/5 16:41.
    */
public abstract class BitmapCallback extends AbsCallback<Bitmap> {

   @Override
   public Bitmap parseNetworkResponse(Response response) throws Exception {
       return BitmapFactory.decodeStream(response.body().byteStream());
   }
}

package com.wfy.test.net;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by wfy 2017/9/11 16:26.
 *
 * 以旺豆幼教的头像上传为例
 *
 */
public interface UpLoadService {




    //http://192.168.0.30:8088/webapi/wd/bookstore/getinfo.ashx?action=wd_edit_img&user_id=&avatar=

    @Multipart
    @POST("http://192.168.0.30:8088/webapi/wd/bookstore/getinfo.ashx?action=wd_edit_img")
    Call<ResponseBody> uploadFiles(@Query("user_id") String user_id, @PartMap() Map<String, RequestBody> maps);

}

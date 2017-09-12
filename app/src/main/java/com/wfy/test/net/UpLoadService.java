package com.wfy.test.net;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by wfy 2017/9/11 16:26.
 *
 * 以旺豆幼教的头像上传为例
 *
 */
public interface UpLoadService {
    //http://newwd.5zye.com:9700/webapi/wd/bookstore/getinfo.ashx?action=wd_edit_img&user_id=&avatar=
    @POST("http://newwd.5zye.com:9700/webapi/wd/bookstore/getinfo.ashx")

    Call<String> uploadFile(@Body RequestBody Body);




    @POST("http://newwd.5zye.com:9700/webapi/wd/bookstore/getinfo.ashx")
    //标记类 表示发送form-encoded的数据（适用于 有文件 上传的场景
    @Multipart
    Call<String> uploadFiles(@PartMap Map<String, RequestBody> params,
                             @Part List<MultipartBody.Part> parts);

}

package com.wfy.test.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by wfy 2017/7/6 14:15.
 *
 * 接口请求示例代码
 *
 * 示范链接 这是第三方一个请求天气的接口
 * http://apicloud.mob.com/v1/weather/query?key=appkey&city=%E8%A5%BF%E5%AE%89&province=%E9%99%95%E8%A5%BF
 *
 */
public interface WeatherService {
    /**
     *  参数设置方式分为以下两种
     */



    /**
     * 1、？后用&拼的参数
     *
     * @GET与@POST中填入base_url后拼接的内容，base_url相当于以前项目中config里的ip，表示所有接口都在这个链接之下
     * base_url=http://apicloud.mob.com/
     * @GET("v1/weather/query")，等于 http://apicloud.mob.com/v1/weather/query
     * @POST("v1/weather/query")
     * 方法中使用 @Query("key") String string1表示每一个参数，引号中的字段与接口一致即可
     *
     */
    @GET("v1/weather/query")
    Call<String> getWeather(@Query("key") String string1, @Query("city") String string2, @Query("province") String string3);

    @POST("v1/weather/query")
    Call<String> postWeather(@Query("key") String string1, @Query("city") String string2, @Query("province") String string3);

    /**
     ** 2、 path部分的拼接，多用于新闻资讯类接口
     *  将v1看作变量的话，v1属于path的一部分但不在？之后
     *
     */

    @GET("{vn}/weather/query")
    Call<String> getWeatherUrl(@Path("vn") String v, @Query("key") String string1, @Query("city") String string2, @Query("province") String string3);

    @POST("{vn}/weather/query")
    Call<String> postWeatherUrl(@Path("vn") String v, @Query("key") String string1, @Query("city") String string2, @Query("province") String string3);

}

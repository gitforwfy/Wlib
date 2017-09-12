package com.wuzhou.wlibrary.http;

import android.app.Application;

import com.wuzhou.wlibrary.http.cookie.store.PersistentCookieStore;
import com.wuzhou.wlibrary.http.model.HttpHeaders;
import com.wuzhou.wlibrary.http.model.HttpParams;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wfy 2017/7/6 14:13.
 */

public class RetrofitServiceManager {
    public static String BASE_URL;
    private static Application context;
    /** 必须在全局Application先调用，获取context上下文，否则缓存无法使用 */
    public static void init(Application app,String baseUrl) {
        context = app;
        BASE_URL = baseUrl;
    }

    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;
    private RetrofitServiceManager(){
        // 添加公共参数拦截器
        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("paltform","android")
                .addHeaderParams("userToken","1234343434dfdfd3434")
                .addHeaderParams("userId","123445")
                .build();

        // 创建 OKHttpClient
        OkHttpUtils.init(context);
        HttpHeaders headers = new HttpHeaders();                    //所有的 header 都 不支持 中文
        HttpParams params = new HttpParams();                       //所有的 params 都 支持 中文
        OkHttpUtils okHttpUtils=OkHttpUtils.getInstance()
                .debug("OkHttp Log")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                 //全局的写入超时时间
//                .setCookieStore(new MemoryCookieStore())                         //cookie使用内存缓存（app退出后，cookie消失）
                .setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
                .addCommonHeaders(headers)                                         //设置全局公共头
                .addCommonParams(params);

        OkHttpClient okHttpClient = okHttpUtils.getOkHttpClient();
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
//                .readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS)
//                .addInterceptor(commonInterceptor)
//                .build();
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }
    private static class SingletonHolder{
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }
    /**
     * 获取RetrofitServiceManager
     * @return
     */
    public static RetrofitServiceManager getInstance(){
        return SingletonHolder.INSTANCE;
    }
    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }
}

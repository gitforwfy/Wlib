package com.wfy.test.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wfy.test.R;
import com.wfy.test.net.WeatherService;
import com.wuzhou.wlibrary.http.RetrofitServiceManager;
import com.wuzhou.wlibrary.page.TitleActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpActivity extends TitleActivity {
    private TextView tv_http;
    private WeatherService weatherService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        setTitle("网络请求示例");
        tv_http= (TextView) findViewById(R.id.tv_http);
        weatherService =RetrofitServiceManager.getInstance().create(WeatherService.class);

    }

    public void get(View view) {
        Call<String> call= weatherService.getWeather("key","西安","陕西省");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tv_http.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                tv_http.setText(t.getMessage());
            }
        });
    }

    public void getUrl(View view) {
        Call<String> call= weatherService.getWeatherUrl("v1","key","西安","陕西省");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tv_http.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                tv_http.setText(t.getMessage());
            }
        });
    }

    public void post(View view) {
        Call<String> call= weatherService.postWeather("key","西安","陕西省");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tv_http.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                tv_http.setText(t.getMessage());
            }
        });
    }

    public void postUrl(View view) {
        Call<String> call= weatherService.postWeatherUrl("v1","key","西安","陕西省");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tv_http.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                tv_http.setText(t.getMessage());
            }
        });
    }

    public void clear(View view) {
        tv_http.setText("");
    }

    public void upLoad(View view) {
        AlertDialog dialog=new AlertDialog.Builder(mActivity)
                .setItems(new String[]{"拍照","从相册选择","取消","上传"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:

                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;

                        }
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();

    }

//    List<String> mImageList;
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == 555) {
//            if (resultCode == RESULT_OK) { // Successfully.
//                // 不要再次质疑你的眼睛，还是这么简单。
//                mImageList =  Album.parseResult(data);
//            } else if (resultCode == RESULT_CANCELED) { // User canceled.
//                // 用户取消了操作。
//            }
//        }
//    }
    private void refreshAdpater(ArrayList<String> paths){
        // 处理返回照片地址
    }
}

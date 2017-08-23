package com.wfy.test.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wfy.test.R;
import com.wfy.test.net.WeatherService;
import com.wuzhou.wlibrary.http.RetrofitServiceManager;
import com.wuzhou.wlibrary.page.TitleActivity;

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
}

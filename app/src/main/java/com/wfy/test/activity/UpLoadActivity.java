package com.wfy.test.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wfy.test.R;
import com.wfy.test.net.UpLoadService;
import com.wuzhou.wlibrary.http.RetrofitServiceManager;
import com.wuzhou.wlibrary.imageloader.Image;
import com.wuzhou.wlibrary.matisse.Matisse;
import com.wuzhou.wlibrary.matisse.MimeType;
import com.wuzhou.wlibrary.matisse.engine.impl.GlideEngine;
import com.wuzhou.wlibrary.matisse.filter.Filter;
import com.wuzhou.wlibrary.matisse.filter.GifSizeFilter;
import com.wuzhou.wlibrary.matisse.internal.entity.CaptureStrategy;
import com.wuzhou.wlibrary.page.TitleActivity;
import com.wuzhou.wlibrary.utils.WLog;
import com.wuzhou.wlibrary.widget.WToast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpLoadActivity extends TitleActivity {
    private UpLoadService weatherService;
    TextView tv_upload;
    GridView gv_upload;
    UpLoadAdapter adapter;

    class UpLoadAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return result.size();
        }

        @Override
        public Object getItem(int i) {
            return result.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view==null){
                view= LayoutInflater.from(mActivity).inflate(R.layout.item_upload,null);
                holder=new ViewHolder();
                holder.imv_item_upload=(ImageView) view.findViewById(R.id.imv_item_upload);
                view.setTag(holder);
            }else{
                holder= (ViewHolder) view.getTag();
            }
            Image.displayImage(mActivity,holder.imv_item_upload,result.get(i));
            return view;
        }
    }

    class ViewHolder{
        ImageView imv_item_upload;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load);
        setTitle("图片上传");
        tv_upload= (TextView) findViewById(R.id.tv_upload);
        gv_upload= (GridView) findViewById(R.id.gv_upload);
        adapter=new UpLoadAdapter();
        gv_upload.setAdapter(adapter);
        weatherService = RetrofitServiceManager.getInstance().create(UpLoadService.class);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    private static final int REQUEST_CODE_CHOOSE = 23;
    private static final int MaxSize = 9;
    public void add(View view) {
        int i=MaxSize-result.size();
        if(i<1){
            WToast.show(mActivity,"已添加"+MaxSize+"张图片");
        }else{
            Matisse.from(mActivity)
                    .choose(MimeType.ofImage(), false)
                    .theme(R.style.Matisse_Zhihu)
                    .countable(true)
                    .capture(true)
                    .showSingleMediaType(true)
                    .captureStrategy(
                            new CaptureStrategy(true, "com.wfy.test.MyFileProvider"))
                    .maxSelectable(MaxSize-result.size())
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(
                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_CHOOSE);
        }
        /**
        AlertDialog alertDialog=new AlertDialog.Builder(mActivity)
                .setItems(new String[]{"拍照","从相册选择","取消"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Matisse.from(mActivity)
                                        .choose(MimeType.ofAll(), false)
                                        .countable(true)
                                        .capture(true)
                                        .captureStrategy(
                                                new CaptureStrategy(true, "com.wfy.test.MyFileProvider"))
                                        .maxSelectable(9)
                                        .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                        .gridExpectedSize(
                                                getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                        .thumbnailScale(0.85f)
                                        .imageEngine(new GlideEngine())
                                        .forResult(REQUEST_CODE_CHOOSE);
                                break;
                            case 1:
                                Matisse.from(mActivity)
                                        .choose(MimeType.ofImage())
                                        .theme(R.style.Matisse_Dracula)
                                        .countable(false)
                                        .maxSelectable(9)
                                        .imageEngine(new PicassoEngine())
                                        .forResult(REQUEST_CODE_CHOOSE);
                                break;
                            case 2:
                                dialogInterface.dismiss();
                                break;

                        }
                    }
                })
                .create();
        alertDialog.show();
         */
    }

    public void upload(View view) {

        if(result.isEmpty()){
            WToast.show(mActivity,"请选择图片！");
        }else{
            File file=new File(result.get(0).toString());

            //构建body
            //
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("action", "wd_edit_img")
                    .addFormDataPart("user_id", "362")
                    .addFormDataPart("avatar", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                    .build();

            Call<String> call= weatherService.uploadFile(requestBody);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    tv_upload.setText(response.body().toString());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    tv_upload.setText(t.getMessage().toString());
                }
            });
        }
    }

    List<String> result=new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
            result.addAll(Matisse.obtainPathResult(data));
            adapter.notifyDataSetChanged();
            WLog.printe(result.toString());
        }
    }

    public void uploadII(View view) {
        if(result.isEmpty()){
            WToast.show(mActivity,"请选择图片！");
        }else{
            File file=new File(result.get(0).toString());

            RequestBody action = RequestBody.create(MediaType.parse("multipart/form-data"), "wd_edit_img");
            RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), "362");
            Map<String,RequestBody> map=new HashMap<>();
            map.put("action",action);
            map.put("user_id",user_id);



            List<MultipartBody.Part> parts=new ArrayList<>();
            for(String path:result){
                File image_file=new File(path);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
                MultipartBody.Part part = MultipartBody.Part.
                        createFormData("avatar", file.getName(), requestBody);
                parts.add(part);
            }


            Call<String> call= weatherService.uploadFiles(map,parts);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    tv_upload.setText(response.body().toString());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    tv_upload.setText(t.getMessage().toString());
                }
            });
        }
    }

    public void uploadIII(View view) {

        if(result.isEmpty()){
            WToast.show(mActivity,"请选择图片！");
        }else{
            File file=new File(result.get(0).toString());
            List<MultipartBody.Part> parts=new ArrayList<>();
            for(String path:result){
                File image_file=new File(path);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
                MultipartBody.Part part = MultipartBody.Part.
                        createFormData("avatar", file.getName(), requestBody);
                parts.add(part);
            }

            Call<String> call= weatherService.uploadFiles("action","362",parts);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    tv_upload.setText(response.body().toString());
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    tv_upload.setText(t.getMessage().toString());
                }
            });
        }
    }
}

package com.photo.album;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.photo.album.activity.DBActivity;
import com.photo.album.activity.SDBActivity;
import com.photo.album.activity.ZipActivity;
import com.wuzhou.wlibrary.page.TitleActivity;
import com.wuzhou.wlibrary.widget.WToast;
import com.wuzhou.wlibrary.zxing.CaptureActivity;

import static com.wuzhou.wlibrary.zxing.CaptureActivity.SCANNIN_REQUEST_CODE;

public class MainActivity extends TitleActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv= (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("wlib");
        setTopLeftButton(0,null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent();
        switch (i){
            case 0:
//                intent.setClass(this, ImageLoaderActivity.class);
//                startActivity(intent);
                break;
            case 1:
//                intent.setClass(this, EventActivity.class);
//                startActivity(intent);
                break;
            case 2:
                intent.setClass(this, DBActivity.class);
                startActivity(intent);
                break;
            case 3:
//                intent.setClass(this, HttpActivity.class);
//                startActivity(intent);
                break;
            case 4:
                intent.setClass(this, ZipActivity.class);
                startActivity(intent);
                break;
            case 5:
                requestPermission(new String[]{Manifest.permission.CAMERA}, 0x0001);
                break;
            case 6:
                intent.setClass(this, SDBActivity.class);
                startActivity(intent);
                break;
//            case 6:
//                intent.setClass(this, UpLoadActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 0x0001:
                Intent intent = new Intent(this,CaptureActivity.class);
                startActivityForResult(intent,SCANNIN_REQUEST_CODE);
                break;
        }

    }

    @Override
    public void permissionFail(int requestCode) {
        super.permissionFail(requestCode);
        WToast.show(this,"未授权不可使用");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SCANNIN_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                String code=data.getStringExtra(CaptureActivity.SCAN_RESULT);
                new AlertDialog.Builder(this)
                        .setMessage("result:"+code)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        }
    }
}

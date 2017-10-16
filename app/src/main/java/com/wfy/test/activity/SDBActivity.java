package com.wfy.test.activity;

import android.Manifest;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wfy.test.R;
import com.wuzhou.wlibrary.page.BaseActivity;
import com.wuzhou.wlibrary.widget.WToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SDBActivity extends BaseActivity {

    private TextView tv_log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdb);
        tv_log= (TextView) findViewById(R.id.tv_log);
    }

    SQLiteDatabase db;
    private final String DATABASE_PATH ="/mnt/external_sd/Android/data/com.wfy.test";
    //    private final String DATABASE_PATH = android.os.Environment
//            .getExternalStorageDirectory().getAbsolutePath() + "/test";
    private String DATABASE_FILENAME = "test.db3";

    // 初始化数据库
    private SQLiteDatabase openDatabase() {
        try {
            String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);

            if (!dir.exists())
                dir.mkdir();

            if (!(new File(databaseFilename)).exists()) {
                InputStream is = getResources().openRawResource(R.raw.test);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
            return db;
        } catch (Exception e) {
            tv_log.setText(e.getMessage().toString()+"");
            e.printStackTrace();
        }
        return null;
    }

    public void onTest(View view) {
        SQLiteDatabase database= openDatabase();
        if(database==null){
//            tv_log.setText("null");
        }else{
            tv_log.setText(database.getPath());
        }
    }

    public void onRequest(View view) {
        requestPermission(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS
        }, 0x0001);
    }

    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 0x0001:
//                Intent intent = new Intent(this,CaptureActivity.class);
//                startActivityForResult(intent,SCANNIN_REQUEST_CODE);
                tv_log.setText("权限申请成功");
                break;
        }

    }

    @Override
    public void permissionFail(int requestCode) {
        super.permissionFail(requestCode);
        WToast.show(this,"未授权不可使用");
        tv_log.setText("未授权不可使用");
    }

}

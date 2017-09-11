package com.wfy.test.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.wfy.test.R;
import com.wuzhou.wlibrary.matisse.Matisse;
import com.wuzhou.wlibrary.matisse.MimeType;
import com.wuzhou.wlibrary.matisse.engine.impl.GlideEngine;
import com.wuzhou.wlibrary.matisse.engine.impl.PicassoEngine;
import com.wuzhou.wlibrary.matisse.filter.Filter;
import com.wuzhou.wlibrary.matisse.filter.GifSizeFilter;
import com.wuzhou.wlibrary.matisse.internal.entity.CaptureStrategy;
import com.wuzhou.wlibrary.page.TitleActivity;
import com.wuzhou.wlibrary.utils.WLog;

import java.util.List;
public class UpLoadActivity extends TitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load);
        setTitle("图片上传");
    }
    private static final int REQUEST_CODE_CHOOSE = 23;
    public void add(View view) {
        AlertDialog alertDialog=new AlertDialog.Builder(mActivity)
                .setItems(new String[]{"拍照","从相册选择","取消"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Matisse.from(mActivity)
                                        .choose(MimeType.ofAll(), false)
                                        .countable(true)
//                                        .capture(true)
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
    }

    public void upload(View view) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mAdapter.setData(Matisse.obtainResult(data), Matisse.obtainPathResult(data));
            List<String> result=Matisse.obtainPathResult(data);
            WLog.printe(result.toString());
        }
    }
}

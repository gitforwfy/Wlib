package com.wuzhou.wlibrary.zip;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

/**
 *
 * Created by wfy on 2017/8/23.
 *
 */

public class UnZipTask extends AsyncTask<String,Integer,String> {
    Context context;
    ProgressDialog progressDialog_unzip;
    public UnZipTask(Context context) {
        this.context=context;
        progressDialog_unzip=new ProgressDialog(context);
        progressDialog_unzip.setMessage("正在解压...");
        progressDialog_unzip.setCanceledOnTouchOutside(false);
        progressDialog_unzip.setCancelable(false);
        progressDialog_unzip.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog_unzip.setProgressNumberFormat("%1d/%2d");
        progressDialog_unzip.setProgress(0);
    }

    String zipFile;
    String folder_path;
    @Override
    protected String doInBackground(String... strings) {
        File file=new File(zipFile);
        try {
            ZipUtils.unZip(file, folder_path, new UnZipCallBack() {
                @Override
                public void run(int max, int progress, String dirName) {
                    publishProgress(new Integer[]{max,progress});
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog_unzip.setMessage("正在解压");
        progressDialog_unzip.setMax(values[0]);
        progressDialog_unzip.setProgress(values[1]);
        if(!progressDialog_unzip.isShowing()){
            progressDialog_unzip.show();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(progressDialog_unzip.isShowing()){
            progressDialog_unzip.dismiss();
        }
    }
}

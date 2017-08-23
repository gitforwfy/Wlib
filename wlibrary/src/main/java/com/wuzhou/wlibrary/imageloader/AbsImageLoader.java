package com.wuzhou.wlibrary.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by wfy 2017/6/28 09:06.
 */

public abstract class AbsImageLoader {

    protected String getPath(String path) {
        if (path == null) {
            path = "";
        }

        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }
        return path;
    }

    public abstract void displayImage(Context context, ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, WDisplayImageListener listener);
    public abstract void displayImage(Context context, ImageView imageView, String path);
    public abstract void displayImage(Context context, ImageView imageView, String path,@DrawableRes int failResId);

    public abstract void downloadImage(Context context, String path, WDownloadImageListener listener);

    public interface WDisplayImageListener {
        void onSuccess(View view, String path);
    }

    public interface WDownloadImageListener {
        void onSuccess(String path, Bitmap bitmap);

        void onFailed(String path);
    }

}

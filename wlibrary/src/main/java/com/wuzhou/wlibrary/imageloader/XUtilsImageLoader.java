package com.wuzhou.wlibrary.imageloader;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by wfy 2017/6/28 09:49.
 */

public class XUtilsImageLoader extends AbsImageLoader{
    @Override
    public void displayImage(Context context, final ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final WDisplayImageListener listener) {
        x.Ext.init((Application) context.getApplicationContext());

        ImageOptions options = new ImageOptions.Builder()
                .setLoadingDrawableId(loadingResId)
                .setFailureDrawableId(failResId)
                .setSize(width, height)
                .build();

        final String finalPath = getPath(path);
        x.image().bind(imageView, finalPath, options, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                if (listener != null) {
                    listener.onSuccess(imageView, finalPath);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    public void displayImage(Context activity, ImageView imageView, String path) {
        x.Ext.init((Application) activity.getApplicationContext());

        ImageOptions options = new ImageOptions.Builder()
                .build();

        final String finalPath = getPath(path);
        x.image().bind(imageView, finalPath, options);
    }

    @Override
    public void displayImage(Context activity, ImageView imageView, String path, @DrawableRes int failResId) {
        x.Ext.init((Application) activity.getApplicationContext());
        ImageOptions options = new ImageOptions.Builder()
                .setFailureDrawableId(failResId)
                .build();

        final String finalPath = getPath(path);
        x.image().bind(imageView, finalPath, options);
    }

    @Override
    public void downloadImage(Context context, String path, final WDownloadImageListener listener) {
        x.Ext.init((Application) context.getApplicationContext());

        final String finalPath = getPath(path);
        x.image().loadDrawable(finalPath, new ImageOptions.Builder().build(), new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                if (listener != null) {
                    listener.onSuccess(finalPath, ((BitmapDrawable) result).getBitmap());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (listener != null) {
                    listener.onFailed(finalPath);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}

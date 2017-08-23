package com.wuzhou.wlibrary.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by wfy 2017/6/28 10:19.
 */

public class PicassoImageLoader extends AbsImageLoader{
    @Override
    public void displayImage(Context context, final ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final WDisplayImageListener listener) {
        final String finalPath = getPath(path);
        Picasso.with(context).load(finalPath).placeholder(loadingResId).error(failResId).resize(width, height).centerInside().into(imageView, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                if (listener != null) {
                    listener.onSuccess(imageView, finalPath);
                }
            }
        });
    }

    @Override
    public void downloadImage(Context context, String path, final WDownloadImageListener listener) {
        final String finalPath = getPath(path);
        Picasso.with(context.getApplicationContext()).load(finalPath).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (listener != null) {
                    listener.onSuccess(finalPath, bitmap);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (listener != null) {
                    listener.onFailed(finalPath);
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String path) {
        final String finalPath = getPath(path);
        Picasso.with(context).load(finalPath).into(imageView);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String path, @DrawableRes int failResId) {
        final String finalPath = getPath(path);
        Picasso.with(context).load(finalPath).error(failResId).into(imageView);
    }
}

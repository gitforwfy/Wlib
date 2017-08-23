package com.wuzhou.wlibrary.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by wfy 2017/6/28 10:28.
 */

public class UILImageLoader extends AbsImageLoader{

    private void initImageLoader(Context context) {
        if (!ImageLoader.getInstance().isInited()) {
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext()).threadPoolSize(3).defaultDisplayImageOptions(options).build();
            ImageLoader.getInstance().init(config);
        }
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final WDisplayImageListener listener) {
        initImageLoader(context);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadingResId)
                .showImageOnFail(failResId)
                .cacheInMemory(true)
                .build();
        ImageSize imageSize = new ImageSize(width, height);

        final String finalPath = getPath(path);
        ImageLoader.getInstance().displayImage(finalPath, new ImageViewAware(imageView), options, imageSize, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (listener != null) {
                    listener.onSuccess(view, imageUri);
                }
            }
        }, null);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String path) {
        initImageLoader(context);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();
        final String finalPath = getPath(path);
        ImageLoader.getInstance().displayImage(finalPath, new ImageViewAware(imageView), options);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, String path, @DrawableRes int failResId) {
        initImageLoader(context);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnFail(failResId)
                .cacheInMemory(true)
                .build();
        final String finalPath = getPath(path);
        ImageLoader.getInstance().displayImage(finalPath,imageView,options);
    }

    @Override
    public void downloadImage(Context context, String path, final WDownloadImageListener listener) {
        initImageLoader(context);

        final String finalPath = getPath(path);
        ImageLoader.getInstance().loadImage(finalPath, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                if (listener != null) {
                    listener.onSuccess(imageUri, loadedImage);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (listener != null) {
                    listener.onFailed(imageUri);
                }
            }
        });
    }
}

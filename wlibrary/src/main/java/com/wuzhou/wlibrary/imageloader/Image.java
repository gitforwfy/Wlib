package com.wuzhou.wlibrary.imageloader;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by wfy 2017/6/28 09:39.
 */

public class Image {

    private static AbsImageLoader absImageloader;

    /**
     * 设置使用哪个图片缓存框架
     *
     * 调用第一次getImageLoader前设置有效
     * @param imageLoader
     */
    public static void init(AbsImageLoader imageLoader){
        if(absImageloader==null) {
            synchronized (Image.class) {
                absImageloader = imageLoader;
            }
        }
    }

    /**
     * 框架默认使用glide
     * @return
     */
    public static AbsImageLoader getImageLoader(){
        if(absImageloader==null){
            synchronized (Image.class) {
                absImageloader=new GlideImageLoader();
//                if (absImageloader == null) {
//                    if (isClassExists("com.bumptech.glide.Glide")) {
//                        absImageloader = new GlideImageLoader();
//                    } else if (isClassExists("com.squareup.picasso.Picasso")) {
//                        absImageloader = new PicassoImageLoader();
//                    } else if (isClassExists("com.nostra13.universalimageloader.core.ImageLoader")) {
//                        absImageloader = new UILImageLoader();
//                    } else if (isClassExists("org.xutils.x")) {
//                        absImageloader = new XUtilsImageLoader();
//                    } else {
//                        throw new RuntimeException("必须在你的 build.gradle 文件中配置「Glide、Picasso、universal-image-loader、XUtils3」中的某一个图片加载库的依赖,或者检查是否添加了图库的混淆配置");
//                    }
//                }
            }
        }
        return absImageloader;
    }

    private static final boolean isClassExists(String classFullName) {
        try {
            Class.forName(classFullName);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void displayImage(Activity activity, ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final AbsImageLoader.WDisplayImageListener delegate) {
        getImageLoader().displayImage(activity, imageView, path, loadingResId, failResId, width, height, delegate);
    }

    public static void displayImage(Context context, ImageView imageView, String path) {
        getImageLoader().displayImage(context, imageView, path);
    }

    public static void displayImage(Context context, ImageView imageView, String path,@DrawableRes int failResId) {
        getImageLoader().displayImage(context, imageView, path,failResId);
    }

    public static void downloadImage(Context context, String path, final AbsImageLoader.WDownloadImageListener delegate) {
        getImageLoader().downloadImage(context, path, delegate);
    }

}

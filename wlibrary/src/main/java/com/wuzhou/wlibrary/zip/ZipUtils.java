package com.wuzhou.wlibrary.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by wfy on 2017/8/23.
 */

public class ZipUtils {
    private static final String byteformat="UTF-8";
    private static final String resultformat="UTF-8";
    public static void unZip(File file, String folderPath, UnZipCallBack callback)throws IOException {
        String dirName = null;
        ZipFile zipFile = new ZipFile(file);
        // 返回 ZIP 文件条目的枚举。
        Enumeration zList = zipFile.entries();
        //ZIP 文件中的条目数。
        int file_max = zipFile.size();
        //解压进度
        int progress = 0;
        int block=1024;

        byte[] buf = new byte[block];
        while (zList.hasMoreElements()) {
            // 当且仅当此枚举对象至少还包含一个可提供的元素时，才返回 true；否则返回 false。
            ZipEntry zipEntry = (ZipEntry) zList.nextElement();//获得压缩文件里 所有的文件 和文件目录
            progress++;
            // 当且仅当此抽象路径名表示的文件存在且是一个目录时，返回 true；否则返回false
            if (zipEntry.isDirectory()) {
                //是目录则返回
                continue;
            }
            File subfile = getRealFileName(folderPath, zipEntry.getName());

            if (subfile != null) {
                //ze.getName()是获得压缩文件中的文件路径 不包括文件夹
                OutputStream os = new BufferedOutputStream(new FileOutputStream(subfile));
                InputStream is = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                int readLen = 0;
                while ((readLen = is.read(buf, 0, block)) != -1) {
                    os.write(buf, 0, readLen);
                }
                is.close();
                os.close();
            } else {
                //文件损坏
                //回调接口
                callback.run(0, -3, "null");
            }
            //String Str1 = dirName != null ? dirName.substring(0, dirName.indexOf("/")) : "null";
            if (progress <= file_max) {
                dirName = zipEntry.getName();
                String Str1 = dirName != null ? dirName.substring(0, dirName.indexOf("/")+1) : "null";
                //回调接口
                callback.run(file_max, progress, Str1);
            }

        }
        zipFile.close();
    }


    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param folderPath 指定根目录
     * @param absFileName     相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
    private static File getRealFileName(String folderPath, String absFileName) {
        String[] dirs = absFileName.split("/");
//		System.out.println("dirs-->"+dirs.length);
//		System.out.println("dirs-->"+dirs[0]);
        File ret = new File(folderPath);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {//循环拼文件路径
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes(byteformat), resultformat);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);
            }
            if (!ret.exists())
                ret.mkdirs();//如果目录不存在则创建目录 ，包括他的父目录
            substr = dirs[dirs.length - 1];//为文件名， 不是目录

            try {
                substr = new String(substr.getBytes(byteformat), resultformat);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            return ret;
        } else if (dirs.length == 1) {//当解压的文件没有目录时
            substr = dirs[0];
            try {
                substr = new String(substr.getBytes(byteformat), resultformat);

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            return ret;
        }
        return ret;
    }
}

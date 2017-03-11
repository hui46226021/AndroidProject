package com.jereibaselibrary.cache;

import android.content.Context;
import android.content.pm.PackageInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
/**
 * Created by zhush on 2016/11/28
 * E-mail 405086805@qq.com
 * PS  硬盘缓存  工具
 *  将文件存储为2禁止文件
 */

public class DiskLruCacheUtil  {
    private static DiskLruCache mDiskLruCache;
    public static String CACHE_DATA="data";
    /**
     * 得到DiskLruCache
     * @param context
     * @return
     */

    public static DiskLruCache open(Context context) {
        try {//  data/data/com.android.httpclient10_21/cache/bitmap
            File cacheDir = getDiskCacheDir(context, CACHE_DATA);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            /**
             * 第一个参数：数据的缓存地址
             * 第二个参数：指定当前程序的版本号
             * 第三个参数：指定同一个key可以对应多少个缓存文件，基本都是传1
             * 第四个参数：指定最多可以缓存多少字节的数据
             * 有了DiskLruCache的实例之后，就可以执行数据操作：写入、访问、移除等
             */
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 500 * 1024 * 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDiskLruCache;
    }

    /**
     * 写入到缓存**************************************************外部调用
     *
     * @param url
     */
    public static void writeToDiskCache(final String url, final byte[] data, Context context) {
        mDiskLruCache = open(context);

        new Thread() {
            public void run() {
                String key = hashKeyForDisk(url);
                try {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        outputStream.write(data, 0, data.length);
                        editor.commit();
                        outputStream.close();
                    }
                    //
                    mDiskLruCache.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();
    }

    /**
     * 从缓存中读取**********************************************外部调用
     */
    public static byte[] readFromDiskCache(String url, Context context) {
        try {
            open(context);
            //得到key
            String key = hashKeyForDisk(url);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream in = snapShot.getInputStream(0);

                ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
                byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
                int rc = 0;
                while ((rc = in.read(buff, 0, 100)) > 0) {
                    swapStream.write(buff, 0, rc);
                }
                byte[] in_b = swapStream.toByteArray(); //in_b为转换之后的结果
                return in_b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取缓存地址
     */
    private static File getDiskCacheDir(Context context, String name) {
        String cachePath = null;
            /*/data/data/<application package>/cache  */
        cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + name);
    }

    /**
     * 获取当前应用程序的版本号:
     * 缓存路径下存储的所有数据都会被清除掉，因为DiskLruCache认为当应用程序有版本更新的时候，所有的数据都应该从网上重新获取。
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1)
                str.append('0');
            str.append(hex);
        }
        return str.toString();
    }


    /**
     * 将image的url地址进行Md5加密
     */
    private static String hashKeyForDisk(String key) {
        String cacheKey = null;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (Exception e) {
            cacheKey = String.valueOf(key.hashCode());
            e.printStackTrace();
        }
        return cacheKey;
    }




    public static long size(Context context) {

        open(context);
        return mDiskLruCache.size();
    }


    public static void  delete() {
        try {
            mDiskLruCache.delete();
        }catch (Exception e){}
    }
}











//package com.jereibaselibrary.cache;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.support.v4.util.LruCache;
//
//
//import com.android.volley.toolbox.ImageLoader;
//import com.jereibaselibrary.tools.JRBaseLibrayUtisl;
//
//import java.io.ByteArrayOutputStream;
//
///**
// * Created by zhush on 2016/11/28
// * E-mail zhush@jerei.com
// * PS  volley 网络图片 框架 缓存
// */
//
//public class BitmapCache implements ImageLoader.ImageCache {
//
//    static BitmapCache bitmapCache;
//
//    private LruCache<String, Bitmap> mCache;
//
//    public static synchronized BitmapCache getInstance() {
//        if (bitmapCache == null) {
//            bitmapCache = new BitmapCache();
//        }
//        return bitmapCache;
//    }
//
//    public BitmapCache() {
//        int maxSize = 10 * 1024 * 1024;
//        mCache = new LruCache<String, Bitmap>(maxSize) {
//            @Override
//            protected int sizeOf(String key, Bitmap bitmap) {
//                return bitmap.getRowBytes() * bitmap.getHeight();
//            }
//        };
//    }
//
//    @Override
//    public Bitmap getBitmap(String url) {
//
//        if (mCache.get(url) == null) {
//            byte[] bytes = DiskLruCacheUtil.readFromDiskCache(url, JRBaseLibrayUtisl.getContext());
//            Bitmap bitmap= Bytes2Bimap(bytes);
//            if(bitmap!=null){
//                mCache.put(url, bitmap);
//            }
//            return bitmap;
//        } else {
//            return mCache.get(url);
//        }
//
//    }
//
//    @Override
//    public void putBitmap(String url, Bitmap bitmap) {
//        mCache.put(url, bitmap);
//        DiskLruCacheUtil.writeToDiskCache(url, Bitmap2Bytes(bitmap), JRBaseLibrayUtisl.getContext());
//    }
//
//    public byte[] Bitmap2Bytes(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        return baos.toByteArray();
//    }
//
//    public Bitmap Bytes2Bimap(byte[] b) {
//
//        if (b!=null&&b.length != 0) {
//            return BitmapFactory.decodeByteArray(b, 0, b.length);
//
//        } else {
//            return null;
//        }
//    }
//
//}
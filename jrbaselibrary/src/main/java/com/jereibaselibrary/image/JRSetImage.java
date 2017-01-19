package com.jereibaselibrary.image;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jereibaselibrary.cache.GlideCacheUtil;



import java.io.File;



/**
 * 获取网络图片
 * Created by zhush on 2016/7/14.
 *   setNetWorkImage          设置网络图片
 *   setNetWorkGifImage       设置GIF 动图
 *   setLocalBitmap           加载本地 大图 防止 内存溢出
 *
 */
public class JRSetImage {

//    private static RequestQueue mQueue;//请求网络图片

    /**
     * 设置网络图片
     * @param context
     * @param url
     * @param view
     */
   public static  void  setNetWorkImage(Context context, String url, ImageView view){
       Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);

      
    }

    /**
     * 设置GIF 动图
     * @param context
     * @param url
     * @param view
     * @param loop  循环次数
     */
    public static  void  setNetWorkGifImage(Context context, String url, ImageView view,int loop){
        if(loop>0){
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new GlideDrawableImageViewTarget(view, loop));
        }else{
            Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(view);
        }


    }


    /**
     * 利用 Picasso 加载本地 大图 防止 内存溢出
     * @param context
     * @param path
     * @param view
     */
    public static  void  setLocalBitmap(Context context, String path, ImageView view){
        Glide.with(context).load(new File(path)).into(view);
    }

    public static  void  setLocalBitmap(Context context, int resourcesId, ImageView view){
        Glide.with(context).load(resourcesId).into(view);
    }



    /**
     * 设置网络图片 防止混乱
     * @param context
     * @param url
     * @param view
     * @param defaultBitmap
     * @param errorBitmap
     */
//    public static  void  setNetWorkBitmapNoChaos(Context context, String url, NetworkImageView view, int defaultBitmap, int errorBitmap){
////        if(mQueue == null){
////            mQueue = Volley.newRequestQueue(context);
////        }
////        ImageLoader imageLoader = new ImageLoader(mQueue, BitmapCache.getInstance());
////        view.setDefaultImageResId(defaultBitmap);
////        view.setDefaultImageResId(errorBitmap);
////
////        view.setImageUrl(url,imageLoader);
//    }






}

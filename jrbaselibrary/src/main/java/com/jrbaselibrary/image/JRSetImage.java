package com.jrbaselibrary.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jrbaselibrary.cache.DiskLruCacheUtil;
import com.jrbaselibrary.constant.SystemConfig;


import java.io.File;



/**
 * 获取网络图片
 * Created by zhush on 2016/7/14.
 *   setNetWorkImage          设置网络图片
 *   setNetWorkGifImage       设置GIF 动图
 *   setLocalBitmap           加载本地 大图 防止 内存溢出
 *  setNetWorkListViewImage   加载listview图片 （防止错乱）
 */
public class JRSetImage {

//    private static RequestQueue mQueue;//请求网络图片

    /**
     * 设置网络图片
     * @param context
     * @param url
     * @param view
     */
   public static  void  setNetWorkImage(Context context, String url, ImageView view,int Image){
       if(context!=null && !context.equals("") && !TextUtils.isEmpty(url)&&view!=null){
           if(!url.startsWith("http")){
               url = SystemConfig.getFullUrl()+"/upload/"+url;
           }
           Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);

       }
       else{
           Glide.with(context).load(Image).diskCacheStrategy(DiskCacheStrategy.ALL).into(view);
       }
    }


    static  RequestQueue mQueue ;
    static  ImageLoader imageLoader;
    public static  void  setNetWorkListViewImage(final Context context, String url, NetworkImageView view, int Image){
        if(mQueue==null){
            mQueue =  Volley.newRequestQueue(context);
          imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    DiskLruCacheUtil.open(context);
                    DiskLruCacheUtil.writeToDiskCache(url,JRBitmapUtils.bimap2Bytes(bitmap),context);
                }

                @Override
                public Bitmap getBitmap(String url) {
                    byte[] bytes = DiskLruCacheUtil.readFromDiskCache(url,context);
                    if(bytes!=null){
                        return   JRBitmapUtils.bytes2Bimap(bytes);
                    }
                    return null;
                }
            });
        }
        if(context!=null && !context.equals("") && !TextUtils.isEmpty(url)&&view!=null){
            if(!url.startsWith("http")){
                url = SystemConfig.getFullUrl()+"/upload/"+url;
            }
            view.setDefaultImageResId(Image);
            view.setErrorImageResId(Image);
            view.setImageUrl(url,
                    imageLoader);

        }
        else{
            view.setDefaultImageResId(Image);
            view.setErrorImageResId(Image);
            view.setImageUrl(url,
                    imageLoader);
        }
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
     * 利用 Glide 加载本地 大图 防止 内存溢出
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

}

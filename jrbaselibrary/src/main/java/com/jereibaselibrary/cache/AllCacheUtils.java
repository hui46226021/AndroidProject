package com.jereibaselibrary.cache;

import android.content.Context;

import java.math.BigDecimal;

/**
 * Created by zhush on 2017/1/12.
 * E-mail zhush@jerei.com
 * PS
 */
public class AllCacheUtils {


    /**
     * 清空图片缓存
     * @param context
     */
    public static void clearBitmapCache(final Context context){
        GlideCacheUtil.getInstance().clearImageAllCache(context);
    }

    /**
     * 获取图片缓存得大小
     * @param context
     * @return
     */
    public static String getBitmapCacheSize(Context context){
        return getFormatSize(GlideCacheUtil.getInstance().getCacheSize(context));
    }

    /**
     * 获取数据缓存大小
     * @param context
     * @return
     */
    public static String getDataCacheSize(Context context){
       return getFormatSize(DiskLruCacheUtil.size(context)) ;
    }

    /**
     * 清空数据缓存
     * @param context
     */
    public static void clearDataCache(final Context context){
        DiskLruCacheUtil.delete();
    }

    /**
     * 获取全部缓存大小
     * @param context
     * @return
     */
    public static String getAllCacheSize(Context context){
        return getFormatSize(DiskLruCacheUtil.size(context)+GlideCacheUtil.getInstance().getCacheSize(context)) ;
    }

    /**
     * 清空全部缓存
     * @param context
     * @return
     */
    public static void clearAllCache(Context context){
        clearBitmapCache(context);
        clearDataCache(context);
    }


    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}

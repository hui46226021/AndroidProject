package com.sh.zsh.code.umeng_sdk;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by zhush on 2017/2/6.
 * E-mail 405086805@qq.com
 * PS
 */
public class UMShareHelper {

    public static void init(Application application){

       Resources resources= application.getResources();
        //注册第三方 id
        PlatformConfig.setWeixin(resources.getString(R.string.wexin_app_id), resources.getString(R.string.wexin_app_secret));
        PlatformConfig.setSinaWeibo(resources.getString(R.string.weibo_app_key), resources.getString(R.string.weibo_app_secret));
        PlatformConfig.setQQZone(resources.getString(R.string.qq_appid), resources.getString(R.string.qq_appkey));
        UMShareAPI.get(application);

    }

    /**
     * 调用微信支付
     * @param context
     * @param partnerId 微信支付分配的商户号（后台获取）
     * @param prepayId 微信返回的支付交易会话ID（后台获取）
     * @param sign  签名 （后台获取）
     * @param nonceStr  随机数
     *
     * 支付结果 会在 WXEntryActivity 类的 onResp 方法回调
     */
    public static void weChatPay(Context context,String partnerId,String prepayId,String sign,String nonceStr){
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);

        // 将该app注册到微信

        msgApi.registerApp(context.getString(R.string.wexin_app_id));

        PayReq request = new PayReq();

        request.appId = context.getString(R.string.wexin_app_id);

        request.partnerId =partnerId;

        request.prepayId= prepayId;

        request.packageValue = "Sign=WXPay";// 暂填写固定值Sign=WXPay

        request.nonceStr= nonceStr;

        request.timeStamp= System.currentTimeMillis()+"";

        request.sign= sign;

        msgApi.sendReq(request);
    }
}

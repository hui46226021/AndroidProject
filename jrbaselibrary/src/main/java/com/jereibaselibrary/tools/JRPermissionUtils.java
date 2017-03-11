package com.jereibaselibrary.tools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.compat.BuildConfig;

/**
 * Created by zhush on 2017/3/8.
 * E-mail 405086805@qq.com
 * PS  几大流氓厂商 的权限管理页面
 *
 * gotoMiuiPermission  开启小米权限界面
 * gotoMeizuPermission  开启魅族权限界面
 * gotoHuaweiPermission  开启华为权限界面
 * gotoAppDetailSettingIntent  开启原生android权限界面
 * gotoMIUI神隐          去小米5的神隐模式
 */
public class JRPermissionUtils {

    /**
     * 跳转到miui的权限管理页面
     */
    private void gotoMiuiPermission(Context context) {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", context.getPackageName());
        try {
            context. startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * 跳转到魅族的权限管理系统
     */
    private void gotoMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * 华为的权限管理页面
     */
    private void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 打开应用详情页面intent
     */
    public static void gotoAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName",context. getPackageName());
        }
        context.startActivity(localIntent);
    }

    /**
     * 去设置小米手机的神隐模式
     * @param context
     * @param requestCode
     */
    public static void gotoMIUI神隐(Context context,int requestCode) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName("com.miui.powerkeeper","com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"));
        intent.putExtra("extra_pkgname", context.getPackageName());
        ((Activity)context).startActivityForResult(intent,requestCode);
    }



}

package com.mny.share.shareutil.shareutils;

import android.app.Activity;
import android.app.WallpaperInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.mny.share.shareutil.R;

public class Const {

    /**
     * 分享平台的appkey，开发者需要分别在qq，微信，微博平台根据包名和正式签名文件的md5值（申请时是MD5值转小写并去掉冒号）申请对应的key
     * 切记包名和签名
     */
    public static String WX_APP_ID = "wxa97f2cfdfd4c5fc5";

    public static String SINA_APP_ID = "968652578";

    public static String QQ_APP_ID = "1107419685";
    private static Const instance;
    public static Const getInstance(){
        if (instance==null) {
            instance=new Const();
        }
        return instance;
    }
    public void initAppID(Context context) {
        SINA_APP_ID = getMetaData(context, context.getResources().getString(R.string.sina_app_id));
        WX_APP_ID = getMetaData(context, context.getResources().getString(R.string.sina_app_id));
        QQ_APP_ID = getMetaData(context, context.getResources().getString(R.string.qq_app_id));
    }

    // 在application应用<meta-data>元素。
    public String getMetaData(Context context, String key) {

        ApplicationInfo appInfo = null;
        String value = "";
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = String.valueOf(appInfo.metaData.get(key));
            Log.d("KEY", "meta-data :key: " + key);
            Log.d("KEY", "  meta-data :value: " + value);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("KEY", e.getMessage());
        }

        return value;
    }

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
//    public static final String REDIRECT_URL = "http://www.sina.com";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * <p>
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * <p>
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * <p>
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}

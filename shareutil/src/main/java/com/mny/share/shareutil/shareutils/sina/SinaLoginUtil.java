package com.mny.share.shareutil.shareutils.sina;

import android.content.Intent;

import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;


/**
 * Crate by E470PD on 2018/8/29
 */
public class SinaLoginUtil {
    public SinaLoginUtil(){}
    private static SinaLoginUtil instance;
    public static SinaLoginUtil getInstance(){
        if (instance==null){
            instance=new SinaLoginUtil();
        }
        return instance;
    }

    /**
     *
     * @param ssoHandler 授权
     * @param authListener 监听
     */
    public void sinaLogin(SsoHandler ssoHandler, WbAuthListener authListener){
            ssoHandler.authorize(authListener);
    }

    /**
     * 当当 SSO 授权 Activity 退出时，该函数被调用。
     * @param mSsoHandler
     */
    public void setSinaStartActivityResult(SsoHandler mSsoHandler,int requestCode, int resultCode, Intent data){
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}

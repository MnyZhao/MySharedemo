package com.mny.share.shareutil.shareutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import com.mny.share.shareutil.shareutils.qq.QQLoginUtil;
import com.mny.share.shareutil.shareutils.sina.SinaLoginUtil;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Crate by E470PD on 2018/8/29
 */
public class LoginHelper {
    private Activity mContext;
    LoginCallBackListenr loginCallBackListenr;

    public LoginHelper(Activity mContext) {
        this.mContext = mContext;
        /*初始化appID*/
        Const.getInstance().initAppID(mContext);
    }

    public LoginHelper(Activity context, LoginCallBackListenr loginCallBackListenr) {
        this.mContext = context;
        this.loginCallBackListenr = loginCallBackListenr;
        Const.getInstance().initAppID(context);
    }

    /********************QQ******************************/
    private Tencent tencent;

    private Tencent getTencent(Activity context) {
        if (tencent == null) {
            tencent = Tencent.createInstance(Const.QQ_APP_ID, context.getApplicationContext());
        }
        return tencent;
    }

    /**
     * loginQQ
     */
    public void qqLogin() {
        QQLoginUtil.getInstance(mContext).qqLogin(getTencent(mContext), qqLoginListener);
    }

    /**
     * 设置接收监听事件QQ
     * 必须调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void setQqStartActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, qqLoginListener);
        }
    }

    IUiListener qqLoginListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            loginCallBackListenr.onSuccess(o);
        }

        @Override
        public void onError(UiError uiError) {
            loginCallBackListenr.onError(uiError);
        }

        @Override
        public void onCancel() {
            loginCallBackListenr.onCancel();
        }
    };
    /********************QQ END******************************/
    /********************SINA******************************/

    /**
     * 调用Sina 分享要先调用 initSina 初始化新浪(初始化sdk 注册应用到sina)
     * 如果要监听回调事件的话要在要在onNewIntent中添加监听调用addSinaCallBackListener
     */
    private WbShareHandler shareHandler;

    /**
     * 获取WbShareHandler
     *
     * @param context
     * @return
     */
    private WbShareHandler getShareHandler(Activity context) {
        if (shareHandler == null) {
            shareHandler = new WbShareHandler(context);
        }
        return shareHandler;
    }

    /**
     * 初始化SinaSdk  context 尽量用ApplicationContext
     *
     * @param context
     */
    private void initSinaSDK(Context context) {
        WbSdk.install(context, new AuthInfo(context
                , Const.SINA_APP_ID, Const.REDIRECT_URL, Const.SCOPE));
    }

    /**
     * 注册应用到sina
     *
     * @param context
     */
    private void registerAppToSina(Activity context) {
        getShareHandler(context).registerApp();//注册应用
        getShareHandler(context).setProgressColor(0xff33b5e5);//设置等待提示进度颜色
    }

    /**
     * 初始化Sina  要进行登陆必须先进行初始化操作 在获取
     *
     * @param context
     */
    public void initSina(Activity context) {
        //初始化sdk
        initSinaSDK(context.getApplicationContext());
        //注册应用到sina
        registerAppToSina(context);
    }

    private SsoHandler ssoHandler;

    /**
     * 获取SsoHandler 在此之前一定要初始化sinaSdk 并将应用注册
     *
     * @param context
     * @return
     */
    private SsoHandler getSsoHandler(Activity context) {
        if (ssoHandler == null) {
            initSina(context);
            ssoHandler = new SsoHandler(context);

        }
        return ssoHandler;
    }

    /**
     * sina 登陆
     */
    public void sinaLogin() {
        SinaLoginUtil.getInstance().sinaLogin(getSsoHandler(mContext), wbAuthListener);
    }

    /**
     * 当调用sina登陆activity 销毁时调用 要在onactiviytresult中设置
     * 必须调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void setSinaStartActivityResult(int requestCode, int resultCode, Intent data) {
        SinaLoginUtil.getInstance().setSinaStartActivityResult(ssoHandler, requestCode, resultCode, data);
    }

    WbAuthListener wbAuthListener = new WbAuthListener() {
        @Override
        public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
            loginCallBackListenr.onSuccess(oauth2AccessToken);
        }

        @Override
        public void cancel() {
            loginCallBackListenr.onCancel();
        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            loginCallBackListenr.onError(wbConnectErrorMessage);
        }
    };

    /********************SINA END******************************/
    public interface LoginCallBackListenr<T> {
        /*成功返回登陆信息 可以通过instanceof 判断类型*/
        void onSuccess(T userInfo);

        /*失败返回登陆信息 可以通过instanceof 判断类型*/
        void onError(T errorInfo);

        /*用户取消*/
        void onCancel();
    }
}

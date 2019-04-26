package com.mny.share.shareutil.shareutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.mny.share.shareutil.R;
import com.mny.share.shareutil.shareutils.qq.QQShareUtil;
import com.mny.share.shareutil.shareutils.sina.SinaShareUtil;
import com.mny.share.shareutil.shareutils.wx.WXShareUtil;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * Crate by E470PD on 2018/8/8
 */
public class ShareHelperUtils {
    private static final String TAG = "ShareHelperUtils";
    private shareCallBackListener callBackListener;
    private Activity context;

    public ShareHelperUtils(Activity context) {
        this.context = context;
        /*初始化appID*/
        Const.getInstance().initAppID(context);
    }

    public ShareHelperUtils(Activity context, shareCallBackListener callBackListener) {
        this.context = context;
        this.callBackListener = callBackListener;
        /*初始化appID*/
        Const.getInstance().initAppID(context);
    }

    /**
     * 设置统一回调
     *
     * @param callBackListener
     */
    public void setCallBackListener(shareCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    /******************************QQ分享***************************************/
    /**
     * QQ分享不需要注册 直接调用发送方法即可
     */
    private Tencent tencent;

    private Tencent getTencent(Activity context) {
        if (tencent == null) {
            tencent = Tencent.createInstance(Const.QQ_APP_ID, context.getApplicationContext());
        }
        return tencent;
    }

    /**
     * 分享到QQ
     *
     * @param context
     * @param shareContent 内容
     */
    public void shareQQ(Activity context, ShareContent shareContent) {
        QQShareUtil.ShareToQQ(context, getTencent(context), shareContent, qqShareListener);
    }

    /**
     * 分享到QQ空间
     *
     * @param context
     * @param shareContent 内容
     */
    public void shareQZone(Activity context, ShareContent shareContent) {
        QQShareUtil.ShareToQzone(context, getTencent(context), shareContent, qqShareListener);
    }

    /**
     * QQ 分享的监听事件 在onActivityResult里注册
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void addQQCallBackListener(int requestCode, int resultCode, Intent data) {
        /*添加监听事件*/
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            Log.i(TAG, "ShareHelperUtils.onCancel" + "QQ取消分享");
            if (null != callBackListener) {
                callBackListener.onCancel();
            }

        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Log.i(TAG, "ShareHelperUtils.onComplete" + "QQ分享成功");
            if (null != callBackListener) {
                callBackListener.onSuccess(response.toString());
            }
        }

        @Override
        public void onError(UiError e) {
            Log.i(TAG, "ShareHelperUtils.onError" + "QQ分享失败" + e.errorMessage);
            if (null != callBackListener) {
                callBackListener.onError(e.errorMessage);
            }
        }
    };
    /******************************QQ分享结束***************************************/

    /******************************Sina分享***************************************/
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

    //初始化Sina
    public void initSina(Activity context) {
        //初始化sdk
        initSinaSDK(context.getApplicationContext());
        //注册应用到sina
        registerAppToSina(context);
    }

    /**
     * 分享到新浪
     * 分享之前要先调用initSina();
     *
     * @param shareContent
     * @param imgId        显示图片id
     */
    public void shareSina(Activity context, ShareContent shareContent, int imgId) {
        SinaShareUtil.getInsance().shareToSinaMedia(context, shareHandler, shareContent, imgId);
    }

    /**
     * sina 分享监听回调 要在onNewIntent中注册
     *
     * @param context
     * @param intent
     */
    public void addSinaCallBackListener(Activity context, Intent intent) {
        getShareHandler(context).doResultIntent(intent, callback);

    }

    WbShareCallback callback = new WbShareCallback() {
        @Override
        public void onWbShareSuccess() {
            Log.i(TAG, "onWbShareSuccess: ");
            String response = "分享成功";
            if (null != callBackListener) {
                callBackListener.onSuccess(response);
            }
        }

        @Override
        public void onWbShareCancel() {
            Log.i(TAG, "onWbShareCancel");
            if (null != callBackListener) {
                callBackListener.onCancel();
            }
        }

        @Override
        public void onWbShareFail() {
            Log.i(TAG, "onWbShareFail");
            String e = "分享失败";
            if (null != callBackListener) {
                callBackListener.onError(e);
            }
        }
    };

    /******************************Sina分享结束***************************************/

    /******************************WeChat分享***************************************/
    /***
     * 微信分享 要先调用initWx 将app 注册到微信里 在调用shareWxSession shareWxTimeLine
     * 如果要监听分享结果 要在onnewIntent里面添加回调监听事件 addWxCallBackListener
     */
    public IWXAPI iwxapi;

    /**
     * 获取 IWXAPI
     *
     * @param context
     * @return
     */
    private IWXAPI getIwxapi(Context context) {
        if (iwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context, Const.WX_APP_ID, false);
        }
        return iwxapi;
    }

    /**
     * 注册应用到微信 必须要做 最好在oncreate里
     *
     * @param context
     */
    private void registerAppToWx(Context context) {
        // 将该app注册到微信
        getIwxapi(context).registerApp(Const.WX_APP_ID);
    }

    //初始化微信
    public void initWx(Context context) {
        //注册应用到微信
        registerAppToWx(context);
    }

    /**
     * 发送WebPage到微信聊天界面
     *
     * @param context
     * @param shareContent
     */
    public void shareWebPageWxSession(Context context, ShareContent shareContent) {
        WXShareUtil.shareToWXSessixon(context, getIwxapi(context), shareContent);
    }

    /**
     * 发送WebPage到微信聊天界面
     *
     * @param context
     * @param shareContent
     * @param drawableId
     */
    public void shareWebPageWxSession(Context context, ShareContent shareContent, int drawableId) {
        WXShareUtil.shareToWXSessixon(context, getIwxapi(context), shareContent, drawableId);
    }

    /**
     * 发送WebPage到微信朋友圈
     *
     * @param context
     * @param shareContent
     */
    public void shareWebPageWxTimeLine(Context context, ShareContent shareContent) {
        WXShareUtil.shareToWXTimeLine(context, getIwxapi(context), shareContent);
    }

    /**
     * 发送WebPage到微信朋友圈
     *
     * @param context
     * @param shareContent
     * @param drawableId
     */
    public void shareWebPageWxTimeLine(Context context, ShareContent shareContent, int drawableId) {
        WXShareUtil.shareToWXTimeLine(context, getIwxapi(context), shareContent, drawableId);
    }

    /**
     * 分享图片到微信朋友圈
     *
     * @param context
     * @param imagePath
     */
    public void shareImageToWxTimeLine(Context context, String imagePath) {
        WXShareUtil.ShareImageToWXTimeLine(getIwxapi(context), imagePath);
    }
    /**
     * 分享图片到微信朋友圈
     *
     * @param context
     * @param imagePath
     */
    public void shareImageToWxSession(Context context, String imagePath) {
        WXShareUtil.ShareImageToWXSessixon(getIwxapi(context), imagePath);
    }

    /**
     * 添加微信分享后的回调
     * 在onNewIntent中
     *
     * @param intent
     */
    public void addWxCallBackListener(Context context, Intent intent) {
        getIwxapi(context).handleIntent(intent, iwxapiEventHandler);
    }

    IWXAPIEventHandler iwxapiEventHandler = new IWXAPIEventHandler() {
        @Override
        public void onReq(BaseReq baseReq) {

        }

        @Override
        public void onResp(BaseResp baseResp) {
            int result;
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    if (null != callBackListener) {
                        callBackListener.onSuccess("分享成功");
                    }
                    result = R.string.errcode_success;//发送成功
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    if (null != callBackListener) {
                        callBackListener.onCancel();
                    }
                    result = R.string.errcode_cancel;//发送取消
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    if (null != callBackListener) {
                        callBackListener.onError("分享失败" + "errorCode:" + baseResp.errCode + "errMsg" + baseResp.errStr);
                    }
                    result = R.string.errcode_deny;//发送被拒绝
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    result = R.string.errcode_unsupported;//不支持错误
                    break;
                default:
                    result = R.string.errcode_unknown;//发送返回
                    break;
            }
        }
    };
    /******************************WeChat分享结束***************************************/
    /**
     * 设置统一回调
     */
    public interface shareCallBackListener {
        //成功
        void onSuccess(String response);

        //取消
        void onCancel();

        //失败出现异常
        void onError(String e);
    }
}


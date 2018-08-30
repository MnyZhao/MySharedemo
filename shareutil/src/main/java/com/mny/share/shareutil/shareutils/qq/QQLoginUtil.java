package com.mny.share.shareutil.shareutils.qq;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

/**
 * Crate by E470PD on 2018/8/28
 */
public class QQLoginUtil {
    public Activity mContext;
    public QQLoginUtil(){}
    public QQLoginUtil(Activity mContext){
        this.mContext=mContext;
    }
    private static QQLoginUtil instance;
    public static QQLoginUtil getInstance(Activity mContext){
        if (instance==null){
            instance=new QQLoginUtil(mContext);
        }
        return instance;
    }

    /**
     *
     * @param mTencent
     * @param loginListener 监听结果
     */
    public void qqLogin(Tencent mTencent, IUiListener loginListener){

        if (!mTencent.isSessionValid()) {
                mTencent.login(mContext, "all", loginListener);
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        }
    }
}

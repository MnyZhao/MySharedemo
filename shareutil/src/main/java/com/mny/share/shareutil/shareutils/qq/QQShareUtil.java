package com.mny.share.shareutil.shareutils.qq;


import android.app.Activity;
import android.os.Bundle;


import com.mny.share.shareutil.R;
import com.mny.share.shareutil.shareutils.ShareContent;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

public class QQShareUtil {
    /**
     * 分享给好友
     *
     * @param context      上下文
     * @param tencent      tencent 对象
     * @param shareContent 分享内容对象 包含 标题 内容(摘要) 用户点击跳转链接
     * @param iUiListener  状态监听
     */
    public static void ShareToQQ(Activity context, Tencent tencent, ShareContent shareContent, IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareContent.title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent.content);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareContent.url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareContent.imageUrl);//图片URL
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, context.getResources().getString(R.string.app_name));//应用名字
        tencent.shareToQQ(context, params, iUiListener);
    }

    /**
     * 分享到Qzone
     *
     * @param context      上下文
     * @param tencent      tencent 对象
     * @param shareContent 分享内容对象 包含 标题 内容(摘要) 用户点击跳转链接
     * @param iUiListener  状态监听
     */
    public static void ShareToQzone(Activity context, Tencent tencent, ShareContent shareContent, IUiListener iUiListener) {
        //分享类型
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);//图文类型
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, shareContent.title);//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, shareContent.content);//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareContent.url);//必填
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(shareContent.imageUrl);//图片URL
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, arrayList);
        tencent.shareToQzone(context, params, iUiListener);
    }
}

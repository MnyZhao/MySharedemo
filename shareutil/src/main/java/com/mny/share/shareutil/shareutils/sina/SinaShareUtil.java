package com.mny.share.shareutil.shareutils.sina;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.mny.share.shareutil.shareutils.ShareContent;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;

public class SinaShareUtil {
    public SinaShareUtil() {
    }

    ;
    public static SinaShareUtil insance;

    public static SinaShareUtil getInsance() {
        if (insance == null) {
            insance = new SinaShareUtil();
        }
        return insance;
    }

    /**
     * @param context
     * @param wbShareHandler 分享api
     * @param shareContent   消息体
     * @param imgId          显示缩略图id
     */
    public void shareToSinaMedia(Activity context, WbShareHandler wbShareHandler, ShareContent shareContent, int imgId) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.mediaObject = getWebpageObj(shareContent, context, imgId);
        wbShareHandler.shareMessage(weiboMessage, false);
    }

    /**
     * 分享文本
     * @param wbShareHandler
     * @param shareContent
     */
    public void shareToSinaText(WbShareHandler wbShareHandler, ShareContent shareContent){
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        weiboMessage.textObject=getTextObj(shareContent);
        wbShareHandler.shareMessage(weiboMessage,false);
    }

    /**
     * 创建文本消息对象。
     *
     * @param content 消息体
     * @return 文本消息对象。
     */
    private TextObject getTextObj(ShareContent content) {
        TextObject textObject = new TextObject();
        textObject.text = content.content;
        textObject.title = content.title;
        textObject.actionUrl = content.url;
        return textObject;
    }

    /**
     * 创建多媒体（网页）消息对象。
     *
     * @param content 分享体描述
     * @param context 上下文链接用来获取指定的资源id
     * @param imgId   显示的缩略图
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj(ShareContent content, Activity context, int imgId) {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = content.title;
        mediaObject.description = content.content;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgId);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = content.url;
        mediaObject.defaultText = content.title;
        return mediaObject;
    }
}

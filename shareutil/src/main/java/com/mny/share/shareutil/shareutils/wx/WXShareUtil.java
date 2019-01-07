package com.mny.share.shareutil.shareutils.wx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mny.share.shareutil.R;
import com.mny.share.shareutil.shareutils.ShareContent;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.io.ByteArrayOutputStream;


public class WXShareUtil {
    /**
     * @param context
     * @param api
     * @param shareContent
     * @param targetType   SendMessageToWX.Req.WXSceneSession(发送到聊天界面)
     *                     SendMessageToWX.Req.WXSceneTimeline(发送到朋友圈)
     */
    public static void sharedToWx(Context context, IWXAPI api, ShareContent shareContent, int targetType) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = shareContent.content;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // msg.title = "Will be ignored";
        msg.description = shareContent.title;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    /**
     * 发送到聊天界面
     *
     * @param api
     * @param shareContent
     */
    public static void shareToWXSessixon(Context context, IWXAPI api, ShareContent shareContent) {
        WXWebpageObject imgObj = new WXWebpageObject();
        imgObj.webpageUrl = shareContent.url;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = shareContent.title;
        msg.description = shareContent.content;
        Bitmap thumbBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);

    }

    /**
     * 发送到聊天界面
     *
     * @param api
     * @param shareContent
     * @param drawableId
     */
    public static void shareToWXSessixon(Context context, IWXAPI api, ShareContent shareContent, int drawableId) {
        if (0 == drawableId) {
            drawableId = R.drawable.ic_launcher;
        }
        WXWebpageObject imgObj = new WXWebpageObject();
        imgObj.webpageUrl = shareContent.url;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = shareContent.title;
        msg.description = shareContent.content;
        Bitmap thumbBmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);

    }

    /**
     * 分享到朋友圈
     *
     * @param api
     * @param shareContent
     */
    public static void shareToWXTimeLine(Context context, IWXAPI api, ShareContent shareContent) {
        WXWebpageObject imgObj = new WXWebpageObject();
        imgObj.webpageUrl = shareContent.url;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = shareContent.title;
        msg.description = shareContent.content;
        Bitmap thumbBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);

    }

    /**
     * 分享到朋友圈
     *
     * @param api
     * @param shareContent
     * @param drawableId
     */
    public static void shareToWXTimeLine(Context context, IWXAPI api, ShareContent shareContent, int drawableId) {
        if (0 == drawableId) {
            drawableId = R.drawable.ic_launcher;
        }
        WXWebpageObject imgObj = new WXWebpageObject();
        imgObj.webpageUrl = shareContent.url;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.title = shareContent.title;
        msg.description = shareContent.content;
        Bitmap thumbBmp = BitmapFactory.decodeResource(context.getResources(), drawableId);
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);

    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}

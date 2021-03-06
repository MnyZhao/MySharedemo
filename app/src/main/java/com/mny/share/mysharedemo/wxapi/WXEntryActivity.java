package com.mny.share.mysharedemo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mny.share.mysharedemo.R;
import com.mny.share.shareutil.shareutils.Const;
import com.mny.share.shareutil.shareutils.ShareContent;
import com.mny.share.shareutil.shareutils.ShareHelperUtils;
import com.mny.share.shareutil.shareutils.Util;
import com.mny.share.shareutil.shareutils.wx.WXShareUtil;


/**
 * Crate by E470PD on 2018/8/8
 */
public class WXEntryActivity extends Activity {
    private Button BtnWx, BtnWXLine, BtnImageWXLine, BtnImageWXSession;
    static ShareContent shareContent;
    ShareHelperUtils shareHelperUtils;

    static {
        shareContent = new ShareContent("测试标题"
                , "测试内容"
                , "http://f.amap.com/4cpb_07AMnu"
                , "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_entry_activity);

        /*封装类后*/
        shareHelperUtils = new ShareHelperUtils(this, callBackListener);
        shareHelperUtils.initWx(this);

        initView();
    }

    private void initView() {
        BtnWx = findViewById(R.id.btn_share_wxsession);
        BtnWx.setOnClickListener(listener);
        BtnWXLine = findViewById(R.id.btn_share_wxtimeline);
        BtnWXLine.setOnClickListener(listener);
        BtnImageWXLine = findViewById(R.id.btn_share_image_line);
        BtnImageWXLine.setOnClickListener(listener);
        BtnImageWXSession = findViewById(R.id.btn_share_image_seession);
        BtnImageWXSession.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_share_wxsession:
                    /*封装类后*/
                    shareHelperUtils.shareWebPageWxSession(WXEntryActivity.this, shareContent);

                    break;
                case R.id.btn_share_wxtimeline:
                    /*封装类后*/
                    shareHelperUtils.shareWebPageWxTimeLine(WXEntryActivity.this, shareContent);
                    break;
                case R.id.btn_share_image_line:
                    shareHelperUtils.shareImageToWxTimeLine(WXEntryActivity.this, Environment.getExternalStorageDirectory().getPath() + "/Pictures/Screenshots/S90426-012113.jpg");
                    break;
                case R.id.btn_share_image_seession:
                    shareHelperUtils.shareImageToWxSession(WXEntryActivity.this, Environment.getExternalStorageDirectory().getPath() + "/Pictures/Screenshots/S90426-012113.jpg");
                    break;
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /*封装类后*/
        setIntent(intent);
        shareHelperUtils.addWxCallBackListener(this, intent);

    }

    ShareHelperUtils.shareCallBackListener callBackListener = new ShareHelperUtils.shareCallBackListener() {
        @Override
        public void onSuccess(String response) {
            Util.toastMessage(WXEntryActivity.this, "分享成功");
        }

        @Override
        public void onCancel() {
            Util.toastMessage(WXEntryActivity.this, "取消分享");
        }

        @Override
        public void onError(String e) {
            Util.toastMessage(WXEntryActivity.this, "分享失败");
        }
    };
}

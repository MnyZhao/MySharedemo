package com.mny.share.mysharedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mny.share.mysharedemo.wxapi.WXEntryActivity;
import com.mny.share.shareutil.shareutils.ShareContent;
import com.mny.share.shareutil.shareutils.ShareHelperUtils;
import com.mny.share.shareutil.shareutils.Util;


public class SecendActivity extends AppCompatActivity {
    private Button BtnGoWx ,BtnShareQQ, BtnShareQzone, BtnShareSina, BtnShareWxSession, BtnShareWxTimeLine;
    public static ShareContent shareContent;
    public ShareHelperUtils shareHelperUtils;

    static {
        shareContent = new ShareContent("测试标题"
                , "测试内容"
                , "https://www.baidu.com"
                , "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        /*添加新浪回调事件*/
        shareHelperUtils.addSinaCallBackListener(this, intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shareHelperUtils = new ShareHelperUtils(this, callBackListener);
        shareHelperUtils.initSina(this);
        initViews();
    }
    private void initViews() {
        BtnGoWx=findViewById(R.id.go_wx);
        BtnGoWx.setOnClickListener(listener);
        BtnShareQQ = (Button) findViewById(R.id.btn_share_qq);
        BtnShareQQ.setOnClickListener(listener);
        BtnShareQzone = (Button) findViewById(R.id.btn_share_qzone);
        BtnShareQzone.setOnClickListener(listener);
        BtnShareSina = (Button) findViewById(R.id.btn_share_sina);
        BtnShareSina.setOnClickListener(listener);
        BtnShareWxSession = (Button) findViewById(R.id.btn_share_wx);
        BtnShareWxSession.setOnClickListener(listener);
        BtnShareWxTimeLine = (Button) findViewById(R.id.btn_share_wxLine);
        BtnShareWxSession.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.go_wx:
                    Intent intentWx = new Intent(SecendActivity.this, WXEntryActivity.class);
                    startActivity(intentWx);
                    finish();
                    break;
                case R.id.btn_share_qq:
                    shareHelperUtils.shareQQ(SecendActivity.this, shareContent);
                    break;
                case R.id.btn_share_qzone:
                    shareHelperUtils.shareQZone(SecendActivity.this, shareContent);
                    break;
                case R.id.btn_share_sina:
                    shareHelperUtils.shareSina(SecendActivity.this, shareContent, R.mipmap.ic_launcher);
                    break;
                case R.id.btn_share_wx:
                    Log.i("TAG", "TESTCLICK");
                    Log.d("TAG", "TESTCLICK");
                    Log.e("TAG", "TESTCLICK");
                    Log.w("TAG", "TESTCLICK");


                    System.out.println("SecendActivity.onClick" + "WX");
                    Toast.makeText(SecendActivity.this, "click", Toast.LENGTH_SHORT).show();
                    Toast.makeText(SecendActivity.this, "click", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_share_wxLine:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*添加QQ监听事件*/
        shareHelperUtils.addQQCallBackListener(requestCode, resultCode, data);

    }

    ShareHelperUtils.shareCallBackListener callBackListener = new ShareHelperUtils.shareCallBackListener() {
        @Override
        public void onSuccess(String response) {
            Util.toastMessage(SecendActivity.this, response + "分享成功");
        }

        @Override
        public void onCancel() {
            Util.toastMessage(SecendActivity.this, "取消分享");
        }

        @Override
        public void onError(String e) {
            Util.toastMessage(SecendActivity.this, e + "分享失败");
        }
    };
}

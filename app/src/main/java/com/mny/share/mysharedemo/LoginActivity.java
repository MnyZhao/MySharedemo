package com.mny.share.mysharedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.mny.share.shareutil.shareutils.Const;
import com.mny.share.shareutil.shareutils.LoginHelper;
import com.mny.share.shareutil.shareutils.Util;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends AppCompatActivity {
    Tencent tencent;
    private Button mBtnQQ, mBtnSina;
    private LoginHelper loginHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tencent = Tencent.createInstance(Const.QQ_APP_ID, this.getApplicationContext());
        loginHelper = new LoginHelper(this, loginCallBackListenr);
        initVIew();
    }

    private void initVIew() {
        mBtnQQ = findViewById(R.id.btn_qq_login);
        mBtnQQ.setOnClickListener(listener);

        mBtnSina = findViewById(R.id.btn_sina_login);
        mBtnSina.setOnClickListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*必须调用*/
        loginHelper.setSinaStartActivityResult(requestCode, resultCode, data);
        loginHelper.setQqStartActivityResult(requestCode, resultCode, data);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_qq_login:
                    loginHelper.qqLogin();
                    break;
                case R.id.btn_sina_login:
                    loginHelper.sinaLogin();
                    break;
            }
        }
    };
    public String TAG = "LOGINHELPER";
    LoginHelper.LoginCallBackListenr loginCallBackListenr = new LoginHelper.LoginCallBackListenr() {
        @Override
        public void onSuccess(Object userInfo) {
            String str = "";
            /*微信非开发者不能测试所以代码还没加 gg*/
            if (userInfo instanceof Oauth2AccessToken) {
                /*sina*/
                Log.e(TAG, "onSuccess: " + ((Oauth2AccessToken) userInfo).getToken());
                str = ((Oauth2AccessToken) userInfo).getToken();
            } else {
                /*得到一个json串可以自己解析*/
                Log.e(TAG, "onSuccess: " + userInfo.toString());
                str = userInfo.toString();
            }
            Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Object errorInfo) {
            String str = "";
            if (errorInfo instanceof UiError) {
                Log.e(TAG, "onError:UIError " + ((UiError) errorInfo).errorMessage);
                str = "onError:UIError " + ((UiError) errorInfo).errorMessage;
            } else if (errorInfo instanceof WbConnectErrorMessage) {
                Log.e(TAG, "onError: WbError" + ((WbConnectErrorMessage) errorInfo).getErrorMessage());
                str = "onError: WbError" + ((WbConnectErrorMessage) errorInfo).getErrorMessage();
            }
            Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "onCancel: ");
            Toast.makeText(LoginActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
        }
    };
}

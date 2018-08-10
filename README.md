#
关于分享的使用方式

#
 首先配置在目标项目的AndroidManifest.xml中配置相应的appid
         <meta-data
                android:name="WX_APP_ID"
                android:value="你的WxAppId" />
         <meta-data
                android:name="QQ_APP_ID"
                android:value="你的QqAppId" />
         <meta-data
                android:name="SINA_APP_ID"
                android:value="你的SinaAppId" />

         <!-- QQ分享必须添加 -->
                  <activity
                      android:name="com.tencent.connect.common.AssistActivity"
                      android:configChanges="orientation|keyboardHidden|screenSize"
                      android:theme="@android:style/Theme.Translucent.NoTitleBar" />
                  <activity
                      android:name="com.tencent.tauth.AuthActivity"
                      android:launchMode="singleTask"
                      android:noHistory="true">
                      <intent-filter>
                          <action android:name="android.intent.action.VIEW" />

                          <category android:name="android.intent.category.DEFAULT" />
                          <category android:name="android.intent.category.BROWSABLE" />
                           <!--tencent你的appID-->
                          <data android:scheme="tencent1107419685" />
                      </intent-filter>
                  </activity>
                  <!-- sina分享不需要配置 -->
                  <!-- WeChat 分享回调必须在 WXEntryActivity中-->
                  <activity
                      android:name=".wxapi.WXEntryActivity"
                      android:exported="true"
                      android:label="@string/app_name"
                      android:launchMode="singleTop"></activity>
         权限不要忘记
           <uses-permission android:name="android.permission.INTERNET" />
           <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
  配置完成后阅读以下步骤即可根据提示使用
         首先创建ShareHelperUtils实例
 #
 QQ
        #调用
            通过ShareHelperUtils的实例调用 shareQQ() shareQZone()\<br>
        添加分享监听事件\<br>
            要重写Activity中的onActivityResult方法并\<br>
            在其中通过ShareHelperUtils的实例调用addQQCallBackListener()\<br>
 #
 Sina
        #调用
            1.通过ShareHelperUtils的实例对sina 进行初始化  调用initSina()方法在onCreate中最好
            2.完成对sina的初始化后
            3.通过ShareHelperUtils的实例调用shareSina即可
        #添加分享监听事件
              1.要重写Activity中的onNewIntenet方法
              2.并在其中通过ShareHelperUtils的实例调用addSinaCallBackListener()
 #
 WeChat
         #调用
            1.通过ShareHelperUtils的实例 对WeChat进行初始化 调用initWx()方法在onCreate()中最好
            2.完成对WeChat初始化后
            3.通过ShareHelperUtils的实例调用shareWxSession(),shareWxTimeLine()分享到聊天界面和朋友圈
         #添加分享监听事件
            1.要重写Activity中的onNewIntent()方法
            2.并在其中通过ShareHelperUtils的实例调用addWxCallBackListener()
 ##
 注意
          *因为微信的对回调结果的监听要求必须在WXEntryActivity中所以我们要在项目目录下新建一个wxapi
          *并在其中实现对wechat sina qq 的分享以及回调事件的监听与其他Activity实现方式没什么不同
          *onNewIntent()方法要重新设置setIntent() 因为分享页面的启动模式设置成singleTop比较好
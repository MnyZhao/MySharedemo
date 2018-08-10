package com.mny.share.shareutil.shareutils;


public class ShareContent {
    public String title; //分享标题
    public String content; //内容
    public String url; //链接
    public String imageUrl;//显示的小图链接

    public ShareContent(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }
    public ShareContent(String title, String content, String url,String imageUrl) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.imageUrl=imageUrl;
    }
}

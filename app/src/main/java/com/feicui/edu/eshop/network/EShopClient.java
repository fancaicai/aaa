package com.feicui.edu.eshop.network;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2017/2/23 0023.
 */
// 网络请求的客户端
public class EShopClient {
    public static final String BASE_URL= "http://106.14.32.204/eshop/emobile/?url=";
    private static EShopClient shopClient;
    private final OkHttpClient mOkHttpClient;
    private EShopClient(){
// 日志拦截器的创建
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // OkHttpClient 的初始化
        mOkHttpClient = new OkHttpClient.Builder()
.addInterceptor(loggingInterceptor)
                .build();
    }
public static EShopClient getInstance(){
    if (shopClient==null){
        shopClient = new EShopClient();
    }
    return shopClient;
}
    // 构建一下商品分类的接口请求
    public Call getCategory(){
//        什么情况下使用Requestbody什么情况下使用Request
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL+"/category")
                .build();

        return mOkHttpClient.newCall(request);
    }
    // 首页：banner请求接口
    public  Call getHomeBanner(){
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL+"/home/data")
                .build();
        return mOkHttpClient.newCall(request);
    }
    // 首页：分类和推荐的商品
    public Call getHomeCategory(){
        Request request= new Request.Builder()
                .get().url(BASE_URL+"/home/category")
                .build();
        return  mOkHttpClient.newCall(request);
    }
}

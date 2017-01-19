package com.sh.shprojectdemo;

import com.jereibaselibrary.netowrk.retrofit2.BaseCallModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zhush on 2017/1/19.
 * E-mail zhush@jerei.com
 * PS
 */
public interface GitHubAPI {
    /*
   请求该接口：https://api.github.com/users/baiiu
 */
    @FormUrlEncoded
    @POST("/login/email")
    Call<BaseCallModel<News>> userInfo(@Field("news") String news);
}

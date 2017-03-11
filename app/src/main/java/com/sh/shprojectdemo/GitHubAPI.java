package com.sh.shprojectdemo;

import com.jereibaselibrary.netowrk.retrofit2.BaseCallModel;
import com.sh.shprojectdemo.model.News;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhush on 2017/1/19.
 * E-mail 405086805@qq.com
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

package com.jereibaselibrary.netowrk.retrofit2;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhush on 2017/1/19.
 * E-mail 405086805@qq.com
 * PS
 */
public abstract class MyCallback<T extends BaseCallModel> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.raw().code() == 200) {//200是服务器有合理响应
            if(response.body().error == 0){
                onSuc(response);
            }
            else if (response.body().error == 1){

            }
            else if (response.body().error == 2){

            }
            else {
                onFail(response.body().msg);
            }

        } else {//失败响应
            onFailure(call, new RuntimeException("response error,detail = " + response.raw().toString()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {//网络问题会走该回调
        if(t instanceof SocketTimeoutException){
            //
        }else if(t instanceof ConnectException){
            //
        }else if(t instanceof RuntimeException){
            //
        }
        onFail(t.getMessage());
    }

    public abstract void onSuc(Response<T> response);

    public abstract void onFail(String message);



}


package com.jrfunclibrary.base.comm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jereibaselibrary.netowrk.HttpAsynTask;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.listen.HandleResponse;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jereibaselibrary.tools.JRDataResult;
import com.jereibaselibrary.tools.JRDateUtils;
import com.jrfunclibrary.model.CommCode;
import com.jruilibrary.form.layout.model.ViewData;
import com.jruilibrary.form.layout.view.FormSpinner;
import com.jruilibrary.widget.MyProgressDialog;
import com.sh.zsh.jrfunclibrary.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhush on 2017/3/14.
 * E-mail 405086805@qq.com
 * PS 通用代码
 */
public class FormCommSpinner extends FormSpinner  {

    private SharedPreferences sharedPreferences;//数据
    private String commCode;//通用代码
    private boolean isCache;//是否缓存
    private final String COMM_CODE_CACHE="commCodeCache";

    public FormCommSpinner(Context context) {
        super(context);
    }

    public FormCommSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = getContext().obtainStyledAttributes(attrs,  R.styleable.FormCommSpinner, 0, 0);
        commCode = ta.getString( R.styleable.FormCommSpinner_comm_code);
        isCache =  ta.getBoolean( R.styleable.FormCommSpinner_is_cache,false);
        sharedPreferences = getContext().getSharedPreferences(COMM_CODE_CACHE,  getContext().MODE_PRIVATE);
        initCommCodeList();
    }


    public String getCommCode() {
        return commCode;
    }

    public void setCommCode(String commCode) {
        this.commCode = commCode;
        initCommCodeList();
    }

    /**
     * 加载通用代码
     */
    public void initCommCodeList(){
        if(TextUtils.isEmpty(commCode)){
            return;
        }
        MyProgressDialog.show(getContext(), "正在加载数据",false);
        //查询路径
       final HttpAsynTask httpAsynTask = new HttpAsynTask("/basedata/baseCodeMap4P.action",HttpAsynTask.GET);
        httpAsynTask.putParam("codeType",commCode);
        //查询回掉
        httpAsynTask.setHttpRequestCall(new RequestCall<Map<String ,List<CommCode>>>() {

            @Override
            public void success(Map<String ,List<CommCode>> dataResult) {
                MyProgressDialog.dismiss();
                Set<String> setKey = dataResult.keySet();
                for(String key:setKey){
                    List<CommCode> commCodes= dataResult.get(key);
                    ArrayList<ViewData> arrayList = new ArrayList<ViewData>();
                    for(CommCode commCode:commCodes){
                        arrayList.add(new ViewData(commCode.getText(),commCode.getValue()));
                    }
                    setpvOptionsList(arrayList);
                }
            }

            @Override
            public void failed(String message, int errorCode) {
                Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
                MyProgressDialog.dismiss();
            }
        });
        //查询成功处理
        httpAsynTask.setHandleResponse(new HandleResponse() {
            @Override
            public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {

                String respons=  client.getResponseStr();
                if(isCache){
                    setCache(commCode,respons);
                }
                Map<String,List<CommCode>> comMap = new HashMap<String, List<CommCode>>();
                try {

                    JSONObject jsonObject = new JSONObject(respons);
                    JSONObject map = (JSONObject) jsonObject.get("map");
                    Iterator<String> keyIter= map.keys();

                    while (keyIter.hasNext()) {
                        String key = keyIter.next();
                        comMap.put(key,client.getList(CommCode.class,"map."+key));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dataControlResult.setResultObject(comMap);
                return dataControlResult;
            }
        });
        //查询缓存策略
        httpAsynTask.setHttpCacheInterface( new HttpAsynTask.HttpCacheInterface() {
            @Override
            public void getHttpCache(JRDataResult result) {
                if(isCache){
                    String commCodeJson= getCache(commCode);
                    Map<String,List<CommCode>> comMap = new HashMap<String, List<CommCode>>();
                    try {
                        JSONObject jsonObject = new JSONObject(commCodeJson);
                        JSONObject map = (JSONObject) jsonObject.get("map");
                        Iterator<String> keyIter= map.keys();

                        while (keyIter.hasNext()) {
                            String key = keyIter.next();
                            comMap.put(key,httpAsynTask.getClient().getList(CommCode.class,"map."+key));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    result.setResultObject(comMap);
                }



            }
        },HttpAsynTask.FOREVER_CACHE);
        httpAsynTask.execute();

    }

    /**
     * 查询缓存
     * @param commCode
     * @return
     */
    public String getCache(String commCode){
        String cacheDate=sharedPreferences.getString(commCode+"cacheDate","");
        if(cacheDate.equals(JRDateUtils.formatDate(System.currentTimeMillis()))){
            return sharedPreferences.getString(commCode,"");
        }else {
            return "";
        }
    }

    /**
     * 出入缓存
     * @param commCode
     * @param commCodeJson
     */
    public void setCache(String commCode,String commCodeJson){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(commCode+"cacheDate", JRDateUtils.formatDate(System.currentTimeMillis()));
        editor.putString(commCode, commCodeJson);
        editor.commit();
    }


}

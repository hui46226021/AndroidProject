package com.jruilibarary;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by zhush on 2017/1/19.
 * E-mail zhush@jerei.com
 * PS
 */
public class ProvinceBean implements IPickerViewData {
    private int id;
    private String s1;
    private String s2;
    private String s3;


    public ProvinceBean(int id, String s1, String s2, String s3) {
        this.id = id;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return s1;
    }
}

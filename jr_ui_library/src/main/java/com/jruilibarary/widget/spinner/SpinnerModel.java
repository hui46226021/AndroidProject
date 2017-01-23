package com.jruilibarary.widget.spinner;

/**
 * Created by zhush on 2016/9/29.
 * E-mail zhush@jerei.com
 */
public class SpinnerModel {
    private String key;
    private Object value;

    public SpinnerModel(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}

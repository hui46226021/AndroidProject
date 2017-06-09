package com.jruilibrary.widget.spinner;

/**
 * Created by zhush on 2016/9/29.
 * E-mail 405086805@qq.com
 */
public class OptionModel {
    private String key;
    private Object value;

    public OptionModel(String key, Object value) {
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

package com.jruilibarary.widget.lineFromView;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by zhush on 2017/1/19.
 * E-mail zhush@jerei.com
 * PS
 */
public class ViewData implements IPickerViewData {
    String key;
    Object value;

    public ViewData(String key, Object value) {
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

    @Override
    public String getPickerViewText() {
        return key;
    }
}

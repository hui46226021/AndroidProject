package com.jruilibarary;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by zhush on 2017/1/19.
 * E-mail zhush@jerei.com
 * PS
 */
public class PickerViewData implements IPickerViewData {
    private String content;

    public PickerViewData(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getPickerViewText() {
        return content;
    }
}

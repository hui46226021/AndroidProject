package com.jruilibrary.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jruilibrary.form.layout.view.FormTimeView;
import com.sh.zsh.jr_ui_library.R;

import java.util.Date;

/**
 * Created by zhush on 2017/4/6.
 * E-mail 405086805@qq.com
 * PS 时间段选择控件
 */
public class DateSlotView extends LinearLayout {
    public DateSlotView(Context context) {
        super(context);
    }
    FormTimeView startTimeText;
    FormTimeView endTimeText;
    public DateSlotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.widget_date_slot, this);
        startTimeText= (FormTimeView) findViewById(R.id.startTime_text);
        endTimeText= (FormTimeView) findViewById(R.id.endTime_text);
        endTimeText.setDate(new Date());
        startTimeText.setDate(new Date(System.currentTimeMillis()-(60*1000*60*24*7))); //开始时间前7天

    }

    /**
     * 获取开始时间
     * @return
     */
    public String getStartTime(){
        return startTimeText.getText()+"";
    }

    /**
     * 获取结束时间
     * @return
     */
    public String getEndTime(){
        return endTimeText.getText()+"";
    }
}

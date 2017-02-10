package com.jruilibarary.widget.lineformview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.jruilibarary.widget.IOSSwitchButton;
import com.sh.zsh.jr_ui_library.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by zhush on 2017/1/19
 * E-mail zhush@jerei.com
 * PS
 *  设置等页面条状控制或显示信息的控件
 */

public class LineFromView extends LinearLayout {

    private Context myContext;
    private String tital;


    private String edit_hint;

    private WidgetType widgetType;//控件类型
    private boolean edit_enabled;//输入框是否可编辑
    private boolean canClick;//可点击
    private boolean must;//必须的
    TextView tvName; //标题
    TextView tvContent; //内容
    TextView mustTextView; //显示必须的红点
    EditText editText; //输入框
    TextView tvOptions;//下来选择器
    LinearLayout timeLinearLayout;
    TextView dataText; //日期
    ImageView rightArrow; //右侧箭头
    String text;

    IOSSwitchButton switch_button;

    View bottomLine;//底部分割线
    TimePickerView pvTime;
    boolean isBotomLine;//是否有底部分割线

    OptionsPickerView pvOptions;//选项选择器
    Object pvOptionsSelectValue;//选中的value

    /**
     * 装有所有LineFromView全局map
     * 第一层 key是当前页面
     * 第二层 key是当前字段
     */


    public LineFromView(Context context, AttributeSet attrs) {
        super(context, attrs);

        myContext = context;
        try {
            LayoutInflater.from(context).inflate(R.layout.widget_line_from, this);
        }catch (Exception e){
            e.printStackTrace();
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineFromView, 0, 0);
        try {
            tital = ta.getString(R.styleable.LineFromView_linefromview_tital);

            edit_enabled = ta.getBoolean(R.styleable.LineFromView_linefromview_edit_enabled, true);
            edit_hint = ta.getString(R.styleable.LineFromView_linefromview_edit_hint);
            isBotomLine = ta.getBoolean(R.styleable.LineFromView_linefromview_bottomLine, true);
            canClick = ta.getBoolean(R.styleable.LineFromView_linefromview_can_click, false);
            must = ta.getBoolean(R.styleable.LineFromView_linefromview_must, false);
            text= ta.getString(R.styleable.LineFromView_linefromview_must);


            int type = ta.getInt(R.styleable.LineFromView_linefromview_type, 1);

            switch (type) {
                case 1:
                    widgetType = WidgetType.textView;
                    break;
                case 2:
                    widgetType = WidgetType.editText;
                    break;
                case 3:
                    widgetType = WidgetType.spinner;
                    break;
                case 4:
                    widgetType = WidgetType.time;
                    break;
                case 5:
                    widgetType = WidgetType.switchButton;
                    break;

            }

            setUpView();
        } finally {
            ta.recycle();
        }


    }


    private void setUpView() {
        if (!isBotomLine) {
            findViewById(R.id.bottomLine).setVisibility(GONE);
        }
        tvOptions = (TextView) findViewById(R.id.linefromview_spinner);
        timeLinearLayout = (LinearLayout) findViewById(R.id.line_time_widget);
        dataText = (TextView) findViewById(R.id.line_date);
//        timeText = (TextView) findViewById(R.id.line_date_time);
        timeLinearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showPvTime();
            }
        });
        rightArrow = (ImageView) findViewById(R.id.rightArrow);
        switch_button = (IOSSwitchButton) findViewById(R.id.line_switch_button);
        mustTextView = (TextView) findViewById(R.id.line_must);
        tvName = (TextView) findViewById(R.id.line_tital);
        tvName.setText(tital);
        tvContent = (TextView) findViewById(R.id.line_content);
        editText = (EditText) findViewById(R.id.line_edittext);
        editText.setHint(edit_hint);
        if (must) {
            mustTextView.setVisibility(VISIBLE);
        } else {
            mustTextView.setVisibility(INVISIBLE);
        }

        if (edit_enabled == false) {
            setEnabled(edit_enabled);
        }

        tvOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pvOptions.show();
            }
        });


        if (canClick) {
            tvContent.setTextColor(Color.rgb(64, 130, 184));
        }


        switch (widgetType) {
            case textView:
                tvContent.setVisibility(VISIBLE);
                editText.setVisibility(GONE);
                tvOptions.setVisibility(GONE);
                timeLinearLayout.setVisibility(GONE);
                switch_button.setVisibility(GONE);
                tvContent.setText(text);
                break;
            case editText:
                tvContent.setVisibility(GONE);
                editText.setVisibility(VISIBLE);
                tvOptions.setVisibility(GONE);
                timeLinearLayout.setVisibility(GONE);
                switch_button.setVisibility(GONE);

                break;
            case spinner:
                tvContent.setVisibility(GONE);
                editText.setVisibility(GONE);
                tvOptions.setVisibility(VISIBLE);
                timeLinearLayout.setVisibility(GONE);
                switch_button.setVisibility(GONE);
                canClick = true;
                pvOptions = new OptionsPickerView(myContext);
                break;
            case time:
                tvContent.setVisibility(GONE);
                editText.setVisibility(GONE);
                tvOptions.setVisibility(GONE);
                timeLinearLayout.setVisibility(VISIBLE);
                switch_button.setVisibility(GONE);
                canClick = true;
                break;
            case switchButton:
                tvContent.setVisibility(GONE);
                editText.setVisibility(GONE);
                tvOptions.setVisibility(INVISIBLE);
                timeLinearLayout.setVisibility(INVISIBLE);
                switch_button.setVisibility(VISIBLE);
                break;
        }

        if (canClick) {
            rightArrow.setVisibility(VISIBLE);
        } else {
            rightArrow.setVisibility(GONE);
        }

    }





    ArrayList<ViewData> options1Items ;
    /**
     * 设置选择器list
     */
    public void setpvOptionsList(ArrayList<ViewData> options){
        this.options1Items =options;

        //三级联动效果
        pvOptions.setPicker(options1Items);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle(tital);
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
//        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText();
                tvOptions.setText(tx);
                pvOptionsSelectValue = options1Items.get(options1).getValue();
                if(selectedListener!=null){
                    selectedListener.pvOptions(tx,pvOptionsSelectValue);
                }
            }
        });
        //默认选中第一个
        pvOptions.setSelecedItem(0);

    }

    /**
     * 显示时间控件
     */
    public void showPvTime(){
        //时间选择器
        pvTime = new TimePickerView(myContext, TimePickerView.Type.ALL);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                dataText.setText(getTime(date));
            }
        });

        pvTime.show();
    }

    /**
     * 设置文字内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        switch (widgetType) {
            case textView:

                tvContent.setText(content);
                break;
            case editText:
                editText.setText(content);
                break;
            case spinner:
                int i = 0;
                try {
                    i = Integer.parseInt(content);
                } catch (Exception e) {
                    throw new RuntimeException("选择器 value必须是数字");
                }
                pvOptions.setSelectOptions(i);
                break;
            case time:
                String strTime = timeTOtimeStr(content);
                dataText.setText(strTime);
                break;

            case switchButton:
                try {
                    if (Boolean.parseBoolean(content)) {
                        switch_button.openSwitch();
                    } else {
                        switch_button.closeSwitch();
                    }

                } catch (Exception e) {
                    throw new RuntimeException("开关值为boolean");
                }


                break;
        }
    }


    /**
     * 获取内容
     */
    public String getContent() {

        switch (widgetType) {
            case textView:
                return tvContent.getText().toString();
            case editText:
                return editText.getText().toString();
            case spinner:

                return pvOptionsSelectValue.toString();
            case time:

                return timeStrTOtime(dataText.getText() + " " ) + ":0";
            case switchButton:
                return switch_button.isSwitchOpen() + "";
        }
        return "";
    }

    /**
     * 获取控件
     *
     * @return
     */
    public View getWidget() {
        switch (widgetType) {
            case textView:
                return tvContent;
            case editText:
                return editText;

            case switchButton:
                return switch_button;
        }
        return tvContent;
    }


    enum WidgetType {
        textView,
        editText,
        time,
        switchButton,
        spinner;
    }

    /**
     * @param switchListener
     */
    public void setOnSwitchListener(IOSSwitchButton.SwitchListener switchListener) {
        switch_button.setSwitchListener(switchListener);
    }

    public void setTital(String tital) {
        this.tital = tital;
        tvName.setText(tital);
    }

    /**
     * 控件禁用
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        getWidget().setEnabled(enabled);
        if (getWidget() instanceof TextView) {
            ((TextView) getWidget()).setTextColor(Color.rgb(0, 0, 0));
        }
        rightArrow.setVisibility(GONE);
        dataText.setEnabled(enabled);

        timeLinearLayout.setEnabled(enabled);
        tvOptions.setEnabled(enabled);
        if(enabled==false){
            dataText.setTextColor(Color.rgb(0, 0, 0));
            tvOptions.setTextColor(Color.rgb(0, 0, 0));
        }

    }



    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return format.format(date);
    }

    /**
     * 时间转换
     * @param timeStr
     * @return
     */
    public  String timeTOtimeStr(String timeStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String resulTtimeStr="";
        try {
            format.setLenient(false);
            Date date= format.parse(timeStr);
            resulTtimeStr = format2.format(date);
            return resulTtimeStr;
        } catch (ParseException e) {
            throw new RuntimeException("时间格式不合法");

        }
    }

    public  String timeStrTOtime(String timeStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String resulTtimeStr="";
        try {

            format.setLenient(false);
            Date date= format2.parse(timeStr);
            resulTtimeStr = format.format(date);
            return resulTtimeStr;
        } catch (ParseException e) {
            throw new RuntimeException("时间格式不合法");

        }
    }



    public void setSelectedListener(SelectedListener selectedListener) {
        if(pvOptions!=null){
            this.selectedListener = selectedListener;
        }
    }
    private SelectedListener selectedListener; //选中监听
    /**
     * 选中监听
     */
    public static interface SelectedListener{
        public void pvOptions( String key, Object value);
    }
}

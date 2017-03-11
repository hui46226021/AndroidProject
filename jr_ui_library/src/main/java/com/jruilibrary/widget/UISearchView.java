package com.jruilibrary.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.sh.zsh.jr_ui_library.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhush on 2016/10/19.
 * E-mail 405086805@qq.com
 * 搜索框控件
 */

public class UISearchView extends LinearLayout implements View.OnKeyListener {
    EditText editText;
    SearchViewListener searchViewListener;
    ImageView searchDelete;

    boolean isCan =true;//两秒内有效

    public EditText getEditText() {
        return editText;
    }



    public UISearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_search, this);

        searchDelete= (ImageView) findViewById(R.id.search_delete);
        searchDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });

        editText = (EditText) findViewById(R.id.inputSearch);
        editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        editText.setOnKeyListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(editText.getText()+"")){
                    searchDelete.setVisibility(GONE);
                }else {
                    searchDelete.setVisibility(VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        //        if (event.getAction() != KeyEvent.ACTION_UP){   // 忽略其它事件
//            return false;
//        }
        if(!isCan){
            return super.onKeyUp(keyCode, keyEvent);
        }
        isCan =false;
        new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦除第一次按键记录
            @Override
            public void run() {
                isCan =true;
            }
        }, 2000);
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if(searchViewListener!=null){
                    searchViewListener.searchViewCallText(editText.getText()+"");
                }

                return true;
            default:
                return super.onKeyUp(keyCode, keyEvent);
        }
    }

    /**
     * 搜索框 点击搜索键监听
     */
    public static interface SearchViewListener{
        public void searchViewCallText(String text);
    }

    public void setSearchViewListener(SearchViewListener searchViewListener) {
        this.searchViewListener = searchViewListener;
    }

    public String getContent(){
        return editText.getText()+"";
    }


}

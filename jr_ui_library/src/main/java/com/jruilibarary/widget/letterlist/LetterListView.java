package com.jruilibarary.widget.letterlist;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.sh.zsh.jr_ui_library.R;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * 带有字母列表的listView
 *
 *@Title:
 *@Description:
 *@Author:harlan
 *@Since:2014-5-7
 *@Version:
 */
public class LetterListView extends FrameLayout
{
    /** 隐藏字母消息 **/
    private final int MSG_HIDE_LETTER = 0x0;

    /** 字母列表的宽度 **/
    private final int LETTER_LIST_VIEW_WIDTH = 50;//这里宽度写死了,按着一定的比例比较好

    /** 内容列表 **/
    private ListView mListView;
    /** 内容列表适配器 **/
    private LetterBaseAdapter<LetterModle> mAdapter;

    /** 字母列表 **/
    private ListView mLetterListView;
    private LetterAdapter mLetterAdapter;

    private TextView mLetterTextView;

    /** 字母消息Handler **/
    private Handler mLetterhandler;

    /**
     * 构造方法
     * @param context
     */
    public LetterListView(Context context)
    {
        super(context);
        initListView(context);
    }

    /**
     * 构造方法
     * @param context
     * @param attrs
     */
    public LetterListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initListView(context);
    }

    /**
     * 初始化 内容列表 字母列表
     * @Description:
     * @Author harlan
     * @Date 2014-5-7
     */
    private void initListView(Context context)
    {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        //TODO 这里添加内容列表,可以在这里对ListView进行一些你想要的设置
        mListView = (ListView) inflater.inflate(R.layout.letter_list_container, null, false);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mListView, lp);

        //TODO 这里添加字母列表,可以在这里对ListView进行一些你想要的设置
        mLetterListView = (ListView) inflater.inflate(R.layout.letter_list_letter, null, false);
        mLetterListView.setOnTouchListener(mLetterOnTouchListener);
        LayoutParams letterListLp = new LayoutParams(LETTER_LIST_VIEW_WIDTH, LayoutParams.MATCH_PARENT, Gravity.RIGHT);
        addView(mLetterListView, letterListLp);

        //TODO 这里对显示的字母进行设置
        mLetterTextView = (TextView) inflater.inflate(R.layout.letter_list_position, null, false);
        LayoutParams letterLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        addView(mLetterTextView, letterLp);
        mLetterTextView.setVisibility(View.INVISIBLE);

        //初始化letter消息发送者
        mLetterhandler = new LetterHandler(this);
    }

    /**
     * 设置内容列表适配器
     *
     * @param adapter {@link LetterBaseAdapter}
     * @Description:
     * @Author harlan
     * @Date 2014-5-7
     */
    public void setAdapter(LetterBaseAdapter<LetterModle> adapter)
    {
        if(adapter != null)
        {
            mAdapter = adapter;
            if(adapter.getList().size()<10){
                mLetterListView.setVisibility(GONE);
            }else {
                mLetterListView.setVisibility(VISIBLE);
            }

            mListView.setAdapter(mAdapter);
        }
    }

    /**
     * {@link AbsListView#setOnItemClickListener(OnItemClickListener)}
     *
     * @Description:
     * @Author harlan
     * @Date 2014-5-14
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        mListView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        mLetterAdapter = new LetterAdapter(h-getPaddingTop()-getPaddingBottom());
        mLetterListView.setAdapter(mLetterAdapter);
    }

    /**
     * 显示字母
     * @Description:
     * @Author harlan
     * @Date 2014-5-8
     */
    private void showLetter(String letter)
    {
        if(mLetterTextView.getVisibility() != View.VISIBLE)
        {
            mLetterTextView.setVisibility(View.VISIBLE);
            mLetterListView.setBackgroundResource(android.R.color.darker_gray);
        }
        mLetterTextView.setText(letter);

        mLetterhandler.removeMessages(MSG_HIDE_LETTER);
        mLetterhandler.sendEmptyMessageDelayed(MSG_HIDE_LETTER, 500);
    }

    /**
     * 处理消息 {@link LetterHandler#handleMessage(Message)}
     *
     * @param msg 消息
     * @Description:
     * @Author harlan
     * @Date 2014-5-8
     */
    private void handleLetterMessage(Message msg)
    {
        mLetterTextView.setVisibility(View.INVISIBLE);
        mLetterListView.setBackgroundResource(android.R.color.white);
    }

    /** 字母栏touch事件 **/
    private OnTouchListener mLetterOnTouchListener = new OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            int height = (int)event.getY() - v.getTop();

            int position = mLetterAdapter.getTouchPoistion(height);
            if(position >= 0)
            {
                char letter = (Character) mLetterAdapter.getItem(position);
                //显示字母
                showLetter(String.valueOf(letter));

                //显示到字母对应的位置
                int select = mAdapter.getIndex(letter);
                if(select >= 0)
                {
                    mListView.setSelection(select);
                }
                return true;
            }
            return false;
        }
    };

    /**
     * 字母列表设配器
     *@Title:
     *@Description:
     *@Author:harlan
     *@Since:2014-5-7
     *@Version:
     */
    private class LetterAdapter extends BaseAdapter
    {
        /** 字母表 **/
        private static final String LETTER_STR = "+ABCDEFGHIJKLMNOPQRSTUVWXYZ#";
        /** 最终显示的字母array **/
        private char[] letterArray;
        /** 每个字母的高度 **/
        private int itemHeight;

        /**
         * 构造方法
         *
         * @param height view height
         */
        public LetterAdapter(int height)
        {
            if(mAdapter.hideLetterNotMatch())
            {
                List<Character> list = new ArrayList<Character>();
                char[] allArray = LETTER_STR.toCharArray();
                for(int i=0; i<allArray.length; i++)
                {
                    char letter = allArray[i];
                    int position = mAdapter.getIndex(letter);
                    if(position >= 0)
                    {
                        list.add(letter);
                    }
                }
                letterArray = new char[list.size()];
                for(int i=0; i<list.size(); i++)
                {
                    letterArray[i] = list.get(i);
                }
                list.clear();
                list = null;
            }
            else
            {
                letterArray = LETTER_STR.toCharArray();
            }
            itemHeight = height/letterArray.length;
        }

        @Override
        public int getCount()
        {
            return letterArray.length;
        }

        @Override
        public Object getItem(int position)
        {
            return letterArray[position];
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if(convertView == null)
            {
                convertView = new TextView(getContext());
                ((TextView) convertView).setTextColor(getResources().getColor(android.R.color.black));
                ((TextView) convertView).setGravity(Gravity.CENTER);
                AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, itemHeight);
                convertView.setLayoutParams(lp);
            }
            ((TextView) convertView).setText(String.valueOf(letterArray[position]));

            return convertView;
        }

        /**
         * 获取touch的位置
         *
         * @return position
         * @Description:
         * @Author harlan
         * @Date 2014-5-8
         */
        public int getTouchPoistion(int touchHeight)
        {
            int position = touchHeight / itemHeight;
            if(position >= 0 && position < getCount())
            {
                return position;
            }
            return -1;
        }
    }

    /**
     * 处理字母显示的handler.
     *@Title:
     *@Description:
     *@Author:harlan
     *@Since:2014-5-8
     *@Version:
     */
    private static class LetterHandler extends Handler
    {
        /** 弱引用 {@link LetterListView} **/
        private SoftReference<LetterListView> srLetterListView;

        /**
         * 构造方法
         * @param letterListView {@link LetterListView}
         */
        public LetterHandler(LetterListView letterListView)
        {
            srLetterListView = new SoftReference<LetterListView>(letterListView);
        }

        @Override
        public void handleMessage(Message msg)
        {
            LetterListView letterListView = srLetterListView.get();
            //如果view没有被销毁掉,交给view处理这个消息
            if(letterListView != null)
            {
                letterListView.handleLetterMessage(msg);
            }
        }
    }

    public ListView getmListView(){
        return mListView;
    }
}

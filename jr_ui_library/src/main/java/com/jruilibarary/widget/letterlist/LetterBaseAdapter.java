package com.jruilibarary.widget.letterlist;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * 带有侧边字母列表的listView适配器
 *
 *@Title:
 *@Description:
 *@Author:harlan
 *@Since:2014-5-8
 *@Version:
 */
public abstract class LetterBaseAdapter<L> extends BaseAdapter
{
    /** 字母表头部 **/
    protected static final char HEADER = '+';
    /** 字母表尾部 **/
    protected static final char FOOTER = '#';

    /**
     * 是否需要隐藏没有匹配到的字母
     *
     * @return true 隐藏, false 不隐藏
     * @Description:
     * @Author harlan
     * @Date 2014-5-8
     */
    public abstract boolean hideLetterNotMatch();

    /**
     * 获取字母对应的位置
     *
     * @return position
     * @Description:
     * @Author harlan
     * @Date 2014-5-8
     */
    public abstract int getIndex(char letter);

    public abstract List getList();
}

package com.jruilibrary.widget.letterlist;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用带有字母列表的泛型对象adapter
 *@Title:
 *@Description:
 *@Author:harlan
 *@Since:2014-5-9
 *@Version:
 */
public abstract class LetterBaseListAdapter extends LetterBaseAdapter<LetterModle>
{
    /** log tag. **/
    private static final String TAG = "LetterBaseListAdapter";

    /** 默认错误头部字母. **/
    private static final char ERROR_LETTER = ' ';

    /** view type的类型总数  **/
    private static final int TYPE_COUNT = 2;
    /** 字母类型 **/
    private static final int TYPE_LETTER = 0;
    /** 实体类型 **/
    private static final int TYPE_CONTAINER = 1;

    /** 添加字母之后的list **/
    protected final List<LetterModle> list;
    /** 字母头位置标示map **/
    private final Map<Character, Integer> letterMap;

    /**
     * 构造方法
     */
    public LetterBaseListAdapter()
    {
        list = new ArrayList<LetterModle>();
        letterMap = new HashMap<Character, Integer>();
    }

    /**
     * 构造方法
     * @param dataArray 内容数组
     */
    public LetterBaseListAdapter(LetterModle[] dataArray)
    {
        this();
        setContainerList(dataArray);
    }

    /**
     * 构造方法
     * @param dataList 内容列表
     */
    public LetterBaseListAdapter(List<LetterModle> dataList)
    {
        this();
        setContainerList(dataList);
    }

    /**
     * 设置主体内容
     *
     * @param dataArray 实体数组
     * @Description:
     * @Author harlan
     * @Date 2014-5-9
     */
    protected final void setContainerList(LetterModle[] dataArray)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        if(!letterMap.isEmpty())
        {
            letterMap.clear();
        }

        char letter = ERROR_LETTER;
        int index = 0;
        for(int i=0; i<dataArray.length; i++)
        {
            LetterModle t = dataArray[i];

            char l = getHeaderLetter(t);

            if(letter != l && l != ERROR_LETTER)
            {
                //如果发现这个字母没有添加过,更新一下标示
                letter = l;
                //创建一个T类型的字母头放进去
                LetterModle tl = create(letter);
                if(tl != null)
                {
                    //如果创建成功,则插入到列表中
                    list.add(tl);
                }
                //存放最新字母对应的位置
                letterMap.put(letter, index);
                index++;
            }
            //添加原本的填充实体项
            list.add(t);
            index++;
        }
    }

    /**
     * 设置主体内容.
     *
     * @param dataList 实体列表
     * @Description:
     * @Author harlan
     * @Date 2014-5-9
     */
    protected final void setContainerList(List<LetterModle> dataList)
    {
        if(!list.isEmpty())
        {
            list.clear();
        }
        if(!letterMap.isEmpty())
        {
            letterMap.clear();
        }

        char letter = ' ';
        int index = 0;
        for(int i=0; i<dataList.size(); i++)
        {
            LetterModle t = dataList.get(i);

            char l = getHeaderLetter(t);

            if(letter != l && l != ERROR_LETTER)
            {
                //如果发现这个字母没有添加过,更新一下标示
                letter = l;
                //创建一个T类型的字母头放进去
                LetterModle tl = create(letter);

                if(tl != null)
                {
                    //如果创建成功,则插入到列表中
                    list.add(tl);
                }
                //存放最新字母对应的位置
                letterMap.put(letter, index);
                index++;
            }
            //添加原本的填充实体项
            list.add(t);
            index++;
        }
    }

    /**
     * @param t <实体item对象>
     *
     * @return <实体item对象> 首字母, 获取失败返回 {@link #ERROR_LETTER}
     * @Description:
     * @Author harlan
     * @Date 2014-5-12
     */
    private char getHeaderLetter(LetterModle t)
    {
        //获取item对应的字符串
        String str = getItemString(t);
        str.trim();

        //如果为空,跳出继续
        if(TextUtils.isEmpty(str))
        {
            Log.e(TAG, "item string empty in " + t.toString());
            return ERROR_LETTER;
        }
        char l;
        //获取第一个字母
        char firstChar = str.charAt(0);
        if(firstChar == HEADER || firstChar == FOOTER || LetterUtil.isLetter(firstChar))
        {
            l = firstChar;//如果是头,尾,字母,直接赋值
        }
        else
        {
            String[] letterArray = LetterUtil.getFirstPinyin(firstChar);
            //如果是汉字,取拼音首字母
            if(letterArray != null && letterArray.length > 0)
            {
                l = letterArray[0].charAt(0);
            }
            else
            {
                //如果汉字转拼音失败了,跳过
                Log.e(TAG, firstChar + " turn to letter fail, " + t.toString());
                return ERROR_LETTER;
            }
        }

        //如果是小写字母,转换为大写字母
//        if(l >= 'a')
//        {
//            l = (char) (l - 32);
//        }


        return  (l+"").toUpperCase().charAt(0);
    }

    @Override
    public final int getCount()
    {
        return list.size();
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent)
    {
        if(getItemViewType(position) == TYPE_LETTER)
        {
            return getLetterView(position, convertView, parent);
        }
        return getContainerView(position, convertView, parent);
    }

    @Override
    public final int getItemViewType(int position)
    {
        if(isLetter(list.get(position)))
        {
            return TYPE_LETTER;
        }
        return TYPE_CONTAINER;
    }

    @Override
    public final int getViewTypeCount()
    {
        return TYPE_COUNT;
    }

    @Override
    public boolean hideLetterNotMatch()
    {
        return false;
    }

    @Override
    public final int getIndex(char letter)
    {
        Integer index = letterMap.get(letter);
        if(index == null)
        {
            return -1;
        }
        return index;
    }

    /**
     * @param T <实体item对象>
     *
     * @return <实体item对象>对应的String,用来获取<拼音首字母>
     * @Description:
     * @Author harlan
     * @Date 2014-5-9
     */
    public abstract String getItemString(LetterModle t);

    /**
     * @param letter <字母>
     *
     * @return 根据<字母>创建一个<实体item对象>,用来显示<字母item>
     * @Description:
     * @Author harlan
     * @Date 2014-5-9
     */

    public LetterModle create(char letter) {
        LetterModle letterModle =new  LetterModle();
        letterModle.setFirstLetter(String.valueOf(letter));
        letterModle.setLetter(true);
        return letterModle;
    }

    /**
     * @param t <实体item对象>
     *
     * @return 根据<实体item对象>,判断是否是<字母item>
     * @Description:
     * @Author harlan
     * @Date 2014-5-9
     */
    public boolean isLetter(LetterModle t) {
        return t.isLetter();
    }

    /**
     * 返回 <字母item>界面,其他的同<P>{@link #getView(int, View, ViewGroup)}
     *
     * @Description:
     * @Author harlan
     * @Date 2014-5-9
     */
    public abstract View getLetterView(int position, View convertView, ViewGroup parent);

    /**
     * 返回<实体item>界面,其他的同<P>{@link #getView(int, View, ViewGroup)}
     *
     * @Description:
     * @Author harlan
     * @Date 2014-5-9
     */
    public abstract View getContainerView(int position, View convertView, ViewGroup parent);

    public List getList(){
     return  list;
    }
}

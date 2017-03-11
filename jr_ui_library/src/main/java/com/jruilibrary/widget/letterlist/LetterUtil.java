package com.jruilibrary.widget.letterlist;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.Collections;
import java.util.List;


/**
 * 字母工具类
 *@Title:
 *@Description:
 *@Author:harlan
 *@Since:2014-5-8
 *@Version:
 */
public class LetterUtil
{
    /**
     * @param chinese 一个汉字
     * @return 拼音首字母  依赖pinyin4j-2.5.0.jar
     * @Description:
     * @Author harlan
     * @Date 2014-5-8
     */
    public static String[] getFirstPinyin(char chinese)
    {
        return PinyinHelper.toHanyuPinyinStringArray(chinese);
    }

    /**
     * 是否是字母
     *
     * @return true 字母,false 非字母
     * @Description:
     * @Author harlan
     * @Date 2014-5-8
     */
    public static boolean isLetter(char c)
    {


        String str = "ABCDEFGHIZKLMNOPQRSTUVWXYZabcdefghizklmnopqrstuvwxyz@!$%^&*_-";

        return str.contains(c+"");
    }

    /**
     * 设置 改对象的首字母
     * @param letterModle
     * @param Str
     */
    public static void setFirstLetter(LetterModle letterModle,String Str){

        letterModle.setFirstLetter(getHeaderLetter(Str)+"");

    }
    public static void onSort(List<LetterModle> letterModles){

        Collections.sort(letterModles, new SortByName());

    }

    private static String getHeaderLetter(String str)
    {


        char l;
        //获取第一个字母
        char firstChar = str.charAt(0);
        if(LetterUtil.isLetter(firstChar))
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

                return "A";
            }
        }

        return (l+"").toUpperCase();
    }
}

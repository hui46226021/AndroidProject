package com.jereibaselibrary.tools;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhush on 2016/11/28.
 * E-mail 405086805@qq.com
 * PS
 * replaceLowerCase    大写变小写，并且字母前面加下划线
 * insertXHX            大写字母前面加下划线
 * format              格式胡字符串
 * isEmpty            判断字符串是否为空
 * containStrNumbers  判断包含子字符串的数量
 */
public class JRStringUtils {
    /**
     * 大写变小写，并且字母前面加下划线
     *
     * @param str
     * @return
     */
    public static String replaceLowerCase(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; c != null && i < c.length; i++) {
            if (c[i] < 97 && c[i] > 57) {
                sb.append(("_" + c[i]).toLowerCase());
            } else {
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 大写字母前面加下划线
     *
     * @param str
     * @return
     */
    public static String insertXHX(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; c != null && i < c.length; i++) {
            if (c[i] < 97 && i != 0) {
                sb.append(("_" + c[i]));
            } else {
                sb.append(c[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 格式化字符串
     *
     * @param obj
     * @return
     */
    public static String format(Object obj) {
        return obj == null ? "" : QBchange(obj.toString().trim());
    }

    /**
     * 全角转半角
     *
     * @param str
     * @return
     */
    public static final String QBchange(String str) {
        if (str == null)
            return "";
        String outStr = "";
        String tStr = "";
        byte[] b = null;
        for (int i = 0; str != null && i < str.length(); i++) {
            try {
                tStr = str.substring(i, i + 1);
                b = tStr.getBytes("unicode");
            } catch (Exception ex) {
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (Exception ex) {
                }
            } else {
                outStr = outStr + tStr;
            }
        }
        return outStr;
    }

    /**
     * 判断字符串是否匹配选择项
     *
     * @param arg 需要匹配的字符串
     * @param ignore 是否忽略大小写
     * @param match 匹配项
     * @return
     */
    public static boolean equals(String arg, boolean ignore, String... matchs) {
        boolean bol = false;
        for (String match : matchs) {
            if (ignore) {
                if (match.equalsIgnoreCase(arg)) {
                    bol = true;
                    break;
                }
            } else {
                if (match.equals(arg)) {
                    bol = true;
                    break;
                }
            }
        }
        return bol;
    }

    /**
     * 获取属性名
     * @param field
     * @return
     */
    public static String getGenericClassName(Field field) {
        try {
            String type = field.getGenericType().toString();
            return type.substring(type.lastIndexOf("<") + 1, type
                    .lastIndexOf(">"));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 判断字符串 是不是空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return TextUtils.isEmpty(str);
    }
    /**根据传入对象返回格式化后的字符串值**/
    public static String getFormatStr(Object o)
    {
        String str="";
        try
        {
            if(o==null)
            {
                return "";
            }
            str=QBchange(o.toString().trim());
        }catch(Exception ex){}
        return str;

    }

    /**
     *
     * @param str  判断包含子字符串数量
     * @param containStr
     * @return
     */
    public static int containStrNumbers(String str, String containStr) {
        Pattern p = Pattern.compile(containStr,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        int count = 0;
        while(m.find()){
            count ++;
        }
        return count;
    }
}

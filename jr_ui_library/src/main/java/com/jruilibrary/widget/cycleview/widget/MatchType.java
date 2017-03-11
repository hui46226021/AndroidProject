package com.jruilibrary.widget.cycleview.widget;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/1/4.
 */
public class MatchType {
    public final static int TYPE_WEB = 0xff01;
    public final static int TYPE_FILE = 0xff02;
    public final static int TYPE_SRC = 0xff03;

    public static int getType(String img){
        if (isWeb(img)){
            return TYPE_WEB;
        }else if (isNumeric(img)){
            return TYPE_SRC;
        }else if (isFile(img)){
            return TYPE_FILE;
        }else {
            return -1;
        }
    }

    private static boolean isWeb(String img){
        return img.startsWith("http");
    }

    private static boolean isNumeric(String img){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(img);
        return isNum.matches();
    }

    private static boolean isFile(String img){
        try {
            File file = new File(img);
            if (!file.exists()) {
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

}

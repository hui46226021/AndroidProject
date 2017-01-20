package com.jruilibarary.widget.letterlist;

/**
 * Created by zhush on 2017/1/20.
 * E-mail zhush@jerei.com
 * PS
 */
public class LetterModle {
    public String firstLetter;//名字首字母

    public boolean isLetter;//是否是字母

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public void setLetter(boolean letter) {
        isLetter = letter;
    }

    public boolean isLetter() {
        return isLetter;
    }
}

package com.sh.shprojectdemo.model;

import com.jruilibarary.widget.letterlist.LetterModle;

/**
 * Created by zhush on 2017/1/20.
 * E-mail zhush@jerei.com
 * PS
 */
public class City extends LetterModle{
    private String name;

    public City( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

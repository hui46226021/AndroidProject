package com.sh.shprojectdemo.model;

import com.jereibaselibrary.db.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by zhush on 2017/1/24.
 * E-mail zhush@jerei.com
 * PS
 */
public class User extends DataSupport implements Serializable{
    private String name;
    private int userId;
    private int company;
    private int department;
    private String birthday;
    private boolean sex;// true 男 false 女
    private String headImage;
    private String sign;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", company=" + company +
                ", department=" + department +
                ", birthday='" + birthday + '\'' +
                ", sex=" + sex +
                ", headImage='" + headImage + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}

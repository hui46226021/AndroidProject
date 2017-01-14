package com.sh.shprojectdemo;


import android.support.v4.media.RatingCompat;

import com.jereibaselibrary.db.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by zhush on 2017/1/14.
 * E-mail zhush@jerei.com
 * PS
 */
public class News extends DataSupport {

    private int id;

    private String title;

    private String content;

    private Date publishDate;

    private int commentCount;

    private int hahahaha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishDate=" + publishDate +
                ", commentCount=" + commentCount +
                ", hahahaha=" + hahahaha +
                '}';
    }

    public int getHahahaha() {
        return hahahaha;
    }

    public void setHahahaha(int hahahaha) {
        this.hahahaha = hahahaha;
    }
}

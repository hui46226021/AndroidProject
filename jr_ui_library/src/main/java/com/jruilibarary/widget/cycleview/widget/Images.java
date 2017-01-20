package com.jruilibarary.widget.cycleview.widget;

import java.io.Serializable;

/**
 * Created by wpy on 15/9/2.
 */
public class Images implements Serializable {
//    public String mTitle;
//    public int mImageResourceId;
//
//    public Images(String mTitle, int mImageResourceId) {
//        this.mTitle = mTitle;
//        this.mImageResourceId = mImageResourceId;
//    }
//
//    public static List<Images> createSampleImages() {
//        final List<Images> images = new ArrayList<Images>();
//        images.add(new Images("banner1", R.drawable.banner1));
//        images.add(new Images("banner2", R.drawable.banner2));
//        images.add(new Images("banner3", R.drawable.banner3));
//        images.add(new Images("banner4", R.drawable.banner4));
//        return images;
//    }

    private int id;
    private String title;
    private String img;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Images(int id,String img) {
        this.id = id;
        this.img = img;
    }

    public Images(int id,String title, String img) {
        this.id = id;
        this.title = title;
        this.img = img;
    }
}

package com.jrfunclibrary.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.jereibaselibrary.image.JRSetImage;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.jruilibrary.widget.touchphoto.TouchPhotoView;
import com.sh.zsh.jrfunclibrary.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 文章内顯示大圖
 * Created by zhush on 2016/7/13.
 */
public class ImageViewPageActivity extends BaseActivity implements View.OnClickListener{


    Button goback;
    TextView index;
    Button save;
    ViewPager viewPager;

    String[] imageList;
    int imageIndex;

    private List<ImageView> imageViewsList = new ArrayList<ImageView>();

    public static String IMAGE_LIST="imageList";
    public static String IMAGE_INDEX="image_Index";

     public static Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_page);

        initView();

        imageList= getIntent().getStringArrayExtra(IMAGE_LIST);
        imageIndex = getIntent().getIntExtra(IMAGE_INDEX,0);

        if(imageList!=null){//通过网络 地址 获取图片
            for (int i = 0; i < imageList.length; i++) {
                TouchPhotoView view = new TouchPhotoView(this);
                JRSetImage.setNetWorkImage(ImageViewPageActivity.this,imageList[i],view);
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewsList.add(view);
            }
            if(imageList.length>1){
                index.setText("("+(imageIndex+1)+"/"+imageViewsList.size()+")");
            }else {
                index.setVisibility(View.VISIBLE);
            }
        }else { //通过上一个页面 传递过来的图片

            index.setVisibility(View.VISIBLE);
            TouchPhotoView view = new TouchPhotoView(this);
            view.setImageBitmap(bitmap);
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageViewsList.add(view);
        }

        initViewPage();
    }

    void initView(){

         goback = (Button) findViewById(R.id.goback);
        index= (TextView) findViewById(R.id.index);
       save= (Button) findViewById(R.id.save);
         viewPager= (ViewPager) findViewById(R.id.view_pager);
        goback.setOnClickListener(this);
        index.setOnClickListener(this);
        save.setOnClickListener(this);
        viewPager.setOnClickListener(this);

    }

    void initViewPage(){
        viewPager.setFocusable(true);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setCurrentItem(imageIndex);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(R.id.goback==id){
            finish();
        }
        if(R.id.view_pager==id){
            finish();
        }
        if(R.id.save==id){
            ImageView imageView= imageViewsList.get(viewPager.getCurrentItem());
            saveMyBitmap(System.currentTimeMillis()+"",((BitmapDrawable)imageView.getDrawable()).getBitmap());
        }
    }




    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {



        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        @Override
        public void onPageSelected(int pos) {
            index.setText("("+(pos+1)+"/"+imageViewsList.size()+")");

        }
    }

    public void saveMyBitmap(String bitName, Bitmap mBitmap){


        MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, bitName, "description");
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File("/sdcard/Boohee/image.jpg"))));

        showMessage("保存成功");
    }
}


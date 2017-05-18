package com.sh.shprojectdemo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jereibaselibrary.image.JRBitmapUtils;
import com.jereibaselibrary.image.JRSetImage;
import com.jrfunclibrary.base.activity.BaseFormActivity;
import com.jrfunclibrary.fileupload.presenter.UploadImagePresenter;
import com.jrfunclibrary.fileupload.view.ImageUpLoadView;
import com.jrfunclibrary.model.AttachmentModel;
import com.jruilibrary.form.annotation.FormInjection;
import com.jruilibrary.form.layout.model.ViewData;
import com.jruilibrary.form.layout.view.FormSpinner;
import com.jruilibrary.widget.TemplateTitleBar;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.common.cache.TemporaryCache;
import com.sh.shprojectdemo.model.User;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhush on 2017/2/3
 * E-mail 405086805@qq.com
 * PS  设置页面
 */

public class SettingActivity extends BaseFormActivity implements ImageUpLoadView {

    @InjectView(R.id.template)
    TemplateTitleBar template;
    @InjectView(R.id.headImage)
    ImageView headImage;


    @FormInjection(name = "sign", isNull = true)
    @InjectView(R.id.sign)
    EditText sign;

    UploadImagePresenter uploadImagePresenter;

    public String imageUrl;
    @InjectView(R.id.company)
    FormSpinner company;
    @InjectView(R.id.department)
    FormSpinner department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        initFormInjection();
        uploadImagePresenter = new UploadImagePresenter(this);
        intView();
        initData();
    }

    void intView() {

        template.setMoreTextContextAction("提交", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        String url = "http://ww2.sinaimg.cn/bmiddle/43a39d58gw1ebqjvjr5onj20ea0e1ach";
        JRSetImage.setNetWorkImage(this, url, headImage,R.drawable.nopicture);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addPictrues(true, getImageBitmap(headImage));
            }
        });
        ArrayList<ViewData> companys = new ArrayList<>();
        companys.add(new ViewData("百度", 1));
        companys.add(new ViewData("阿里", 2));
        company.setSelectedListener(new FormSpinner.SelectedListener() {
            @Override
            public void pvOptions(String key, Object value, Object pvOptionsSelectValueObject) {
                ArrayList<ViewData> departments = new ArrayList<>();
                if (((int) value) == 1) {
                    departments.add(new ViewData("地图", 11));
                    departments.add(new ViewData("买假药", 12));
                }
                if (((int) value) == 2) {
                    departments.add(new ViewData("淘宝", 21));
                    departments.add(new ViewData("天猫", 22));
                }
                department.setpvOptionsList(departments);
            }

        });
        company.setpvOptionsList(companys);
    }

    /**
     * 获取ImageView上的图片
     *
     * @param view
     * @return
     */
    public Bitmap getImageBitmap(ImageView view) {
        Bitmap bitmap = null;
        view.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(headImage.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    void initData() {
        User user = TemporaryCache.getUserSession();
        objectToForm(user);
    }

    void submit() {
        User user = (User) formToObjectAndCheck(User.class);
        if (user != null) {
            user.setHeadImage(imageUrl);
        }
        showMessage("提交成功  " + user.toString());
    }

    @Override
    public void getPhonePhoto(Bitmap bitmap) {

        bitmap = JRBitmapUtils.watermarkBitmap(bitmap, null, "水印");
        uploadImagePresenter.uploadImage(bitmap, headImage);
    }

    @Override
    public void uploadImageSuccess(Bitmap bitmap, View view, AttachmentModel attachmentModel) {
        headImage.setImageBitmap(bitmap);
        imageUrl = attachmentModel.getUrl();
    }

    @Override
    public void uploadImageFail(View v, String message) {
        showMessage("上传失败");
    }
}
